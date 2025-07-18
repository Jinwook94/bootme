#!/usr/bin/env bash
#───────────────────────────────────────────────────────────────────────────────
# BootMe 애플리케이션 이미지용 S3 버킷(dev|prod) 생성 & 필수 정책 일괄 적용
#   • Public Access Block (단, 이미지 읽기는 허용)
#   • ACL 완전 무효화 (BucketOwnerEnforced)
#   • 버전 관리 / SSE-S3 암호화
#   • Lifecycle: 비활성 멀티파트 3일 후 삭제
#   • 버킷 정책: 공개 읽기 허용, 동일 계정만 쓰기 가능
#───────────────────────────────────────────────────────────────────────────────
set -euo pipefail

THIS_SCRIPT_NAME="$(basename "$0")"
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
source "${SCRIPT_DIR}/0-config.sh"

# 0) 환경 선택 ───────────────────────────────────────────────────────────────
if ! prompt_environment "create"; then exit 1; fi

readonly BUCKET_NAME="$APPLICATION_IMAGES_BUCKET_NAME"
AWS_REGION="$(aws configure get region --profile "$AWS_PROFILE")"
[[ -n "$AWS_REGION" ]] || { log "$THIS_SCRIPT_NAME" "❌ AWS 리전을 찾지 못했습니다"; exit 1; }

LOG_FILE="$(mktemp)"; trap 'rm -f "$LOG_FILE"' EXIT

log "$THIS_SCRIPT_NAME" "================ 이미지 버킷 생성 & 설정 ================="
log "$THIS_SCRIPT_NAME" "  • ENV            : $ENVIRONMENT"
log "$THIS_SCRIPT_NAME" "  • Profile        : $AWS_PROFILE"
log "$THIS_SCRIPT_NAME" "  • Region         : $AWS_REGION"
log "$THIS_SCRIPT_NAME" "  • Bucket         : $BUCKET_NAME"
log "$THIS_SCRIPT_NAME" "========================================================"

# 1) 버킷 생성 ────────────────────────────────────────────────────────────────
log "$THIS_SCRIPT_NAME" "버킷 생성 시도 …"
if ! aws s3api create-bucket                               \
         --bucket "$BUCKET_NAME"                           \
         --region "$AWS_REGION"                            \
         --create-bucket-configuration "LocationConstraint=$AWS_REGION" \
         --profile "$AWS_PROFILE" >"$LOG_FILE" 2>&1; then

    if grep -q "BucketAlreadyOwnedByYou" "$LOG_FILE"; then
        log "$THIS_SCRIPT_NAME" "⚠️  이미 존재하는 버킷 → 설정 업데이트 진행"
    elif grep -q "InvalidLocationConstraint" "$LOG_FILE" && [ "$AWS_REGION" = "us-east-1" ]; then
        aws s3api create-bucket --bucket "$BUCKET_NAME" --region "$AWS_REGION" --profile "$AWS_PROFILE" >/dev/null
    else
        log "$THIS_SCRIPT_NAME" "❌ 버킷 생성 실패"; cat "$LOG_FILE"; exit 1
    fi
fi
log "$THIS_SCRIPT_NAME" "✅ 버킷 생성 완료"

# 2) Public Access Block (단, 버킷 정책은 허용) ─────────────────────────────────
log "$THIS_SCRIPT_NAME" "Public Access Block 설정 중 …"
aws s3api put-public-access-block \
    --bucket "$BUCKET_NAME" \
    --public-access-block-configuration \
        'BlockPublicAcls=true,IgnorePublicAcls=true,BlockPublicPolicy=false,RestrictPublicBuckets=false' \
    --profile "$AWS_PROFILE" --region "$AWS_REGION"
log "$THIS_SCRIPT_NAME" "✅ Public Access Block 설정 완료 (버킷 정책 허용)"

# 3) ACL 무효화 (BucketOwnerEnforced) ────────────────────────────────────────
log "$THIS_SCRIPT_NAME" "ACL 무효화 설정 중 …"
aws s3api put-bucket-ownership-controls \
    --bucket "$BUCKET_NAME" \
    --ownership-controls 'Rules=[{ObjectOwnership=BucketOwnerEnforced}]' \
    --profile "$AWS_PROFILE" --region "$AWS_REGION"
log "$THIS_SCRIPT_NAME" "✅ ACL 무효화 완료"

# 4) 버전 관리 & 암호화 ──────────────────────────────────────────────────────
log "$THIS_SCRIPT_NAME" "버전 관리 활성화 중 …"
aws s3api put-bucket-versioning \
    --bucket "$BUCKET_NAME" \
    --versioning-configuration Status=Enabled \
    --profile "$AWS_PROFILE" --region "$AWS_REGION"
log "$THIS_SCRIPT_NAME" "✅ 버전 관리 활성화 완료"

log "$THIS_SCRIPT_NAME" "SSE-S3 암호화 설정 중 …"
aws s3api put-bucket-encryption \
    --bucket "$BUCKET_NAME" \
    --server-side-encryption-configuration \
        '{"Rules":[{"ApplyServerSideEncryptionByDefault":{"SSEAlgorithm":"AES256"}}]}' \
    --profile "$AWS_PROFILE" --region "$AWS_REGION"
log "$THIS_SCRIPT_NAME" "✅ SSE-S3 암호화 설정 완료"

# 5) Lifecycle 설정 ───────────────────────────────────────────────────────────
log "$THIS_SCRIPT_NAME" "Lifecycle 정책 설정 중 …"
aws s3api put-bucket-lifecycle-configuration \
    --bucket "$BUCKET_NAME" \
    --lifecycle-configuration '{
        "Rules": [{
            "ID": "CleanupIncompleteMultipartUploads",
            "Status": "Enabled",
            "Filter": {"Prefix": ""},
            "AbortIncompleteMultipartUpload": {
                "DaysAfterInitiation": 3
            }
        }]
    }' \
    --profile "$AWS_PROFILE" --region "$AWS_REGION"
log "$THIS_SCRIPT_NAME" "✅ Lifecycle 정책 설정 완료"

# 6) 버킷 정책 (공개 읽기 + 동일 계정 쓰기) ──────────────────────────────────
log "$THIS_SCRIPT_NAME" "버킷 정책 설정 중 …"
ACCOUNT_ID="$(aws sts get-caller-identity --profile "$AWS_PROFILE" --query Account --output text)"
POLICY_DOC=$(cat <<EOF
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "PublicReadGetObject",
            "Effect": "Allow",
            "Principal": "*",
            "Action": "s3:GetObject",
            "Resource": "arn:aws:s3:::${BUCKET_NAME}/*"
        },
        {
            "Sid": "AllowAccountWrite",
            "Effect": "Allow",
            "Principal": {
                "AWS": "arn:aws:iam::${ACCOUNT_ID}:root"
            },
            "Action": [
                "s3:PutObject",
                "s3:PutObjectAcl",
                "s3:DeleteObject",
                "s3:ListBucket"
            ],
            "Resource": [
                "arn:aws:s3:::${BUCKET_NAME}",
                "arn:aws:s3:::${BUCKET_NAME}/*"
            ]
        }
    ]
}
EOF
)

aws s3api put-bucket-policy \
    --bucket "$BUCKET_NAME" \
    --policy "$POLICY_DOC" \
    --profile "$AWS_PROFILE" --region "$AWS_REGION"
log "$THIS_SCRIPT_NAME" "✅ 버킷 정책 적용 완료"

# 7) 버킷 태그 설정 ───────────────────────────────────────────────────────────
log "$THIS_SCRIPT_NAME" "버킷 태그 설정 중 …"
aws s3api put-bucket-tagging \
    --bucket "$BUCKET_NAME" \
    --tagging "TagSet=[
        {Key=Project,Value=${PROJECT_NAME}},
        {Key=Environment,Value=${ENVIRONMENT}},
        {Key=Purpose,Value=application-images},
        {Key=CreatedBy,Value=script}
    ]" \
    --profile "$AWS_PROFILE" --region "$AWS_REGION"
log "$THIS_SCRIPT_NAME" "✅ 버킷 태그 설정 완료"

# 8) 요약 ────────────────────────────────────────────────────────────────────
log "$THIS_SCRIPT_NAME" "====================== 최종 요약 ======================"
log "$THIS_SCRIPT_NAME" " 버킷 이름       : $BUCKET_NAME"
log "$THIS_SCRIPT_NAME" " Region          : $AWS_REGION"
log "$THIS_SCRIPT_NAME" " Account ID      : $ACCOUNT_ID"
log "$THIS_SCRIPT_NAME" " Versioning      : Enabled"
log "$THIS_SCRIPT_NAME" " Encryption      : SSE-S3 (AES-256)"
log "$THIS_SCRIPT_NAME" " Public Access   : 읽기 허용 (이미지 서빙)"
log "$THIS_SCRIPT_NAME" " Lifecycle       : 미완료 멀티파트 3일 후 삭제"
log "$THIS_SCRIPT_NAME" "======================================================"
log "$THIS_SCRIPT_NAME" ""
log "$THIS_SCRIPT_NAME" "📌 이미지 업로드 경로 예시:"
log "$THIS_SCRIPT_NAME" "   • profile/{memberId}/yyyyMMdd_HHmm_filename.jpg"
log "$THIS_SCRIPT_NAME" "   • post/{memberId}/yyyyMMdd_HHmm_filename.jpg"
log "$THIS_SCRIPT_NAME" ""
log "$THIS_SCRIPT_NAME" "🔗 이미지 접근 URL:"
log "$THIS_SCRIPT_NAME" "   https://${BUCKET_NAME}.s3.${AWS_REGION}.amazonaws.com/{key}"
log "$THIS_SCRIPT_NAME" ""
