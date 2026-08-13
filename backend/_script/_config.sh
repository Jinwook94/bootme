#!/usr/bin/env bash

# 프로젝트 설정
PROJECT_NAME="Java & Spring Boot"
TARGET_DIR="backend"

# 제외할 폴더 목록
EXCLUDE_DIRS=(
    # JavaScript/Node.js 관련
    "node_modules"
    "dist"
    ".next"

    # IDE 관련
    ".idea"
    ".vscode"

    # Go 관련
    "vendor"

    # Java/Kotlin 관련
    "build"
    "builds"
    "out"
    "target"
    ".gradle"
    "gradle"
    "docs"

    # 공통
    "_script"
    "script"
    "fonts"
    ".git"
    "tmp"
    "temp"
#    "test"
)

# 제외할 파일 목록
EXCLUDE_FILES=(
    # JavaScript/Node.js 관련
    "package-lock.json"

    # Go 관련
    "go.sum"

    # Java/Kotlin 관련
    "gradlew"
    "gradlew.bat"
    "*.jar"
    "*.class"
    "*.war"
    "*.ear"

    # IDE 관련
    "*.iml"

    # 기타
    ".DS_Store"
    "*.log"
)

# 포함할 파일 확장자 목록
INCLUDE_EXTENSIONS=(
    # Go 관련
    "*.go"
    "*.mod"

    # Java/Kotlin 관련
    "*.kt"
    "*.kts"
    "*.java"
    "*.properties"
    "*.xml"

    # JavaScript/TypeScript 관련
    "*.js"
    "*.jsx"
    "*.ts"
    "*.tsx"
    "*.mjs"

    # 설정 파일
    "*.yml"
    "*.yaml"
    "*.json"
    "*.toml"
    "*.ini"
    "*.conf"
    "*.config"

    # 웹 관련
    "*.html"
    "*.css"
    "*.scss"
    "*.sass"

    # 데이터베이스 관련
    "*.sql"

    # 스크립트
    "*.sh"
    "*.bash"
    "*.zsh"

    # 환경 설정
    ".env*"

    # 빌드/배포 관련 (확장자가 없는 파일들)
    "Dockerfile"
    "Makefile"
    "*.gradle"

    # 문서
    "*.md"
    "README*"
    "HELP*"
)

# find 명령을 위한 제외 경로 옵션 생성
build_exclude_paths() {
    local exclude_opts=""
    for dir in "${EXCLUDE_DIRS[@]}"; do
        exclude_opts="$exclude_opts ! -path '*/$dir/*'"
    done
    for file in "${EXCLUDE_FILES[@]}"; do
        exclude_opts="$exclude_opts ! -name '$file'"
    done
    echo "$exclude_opts"
}

# find 명령을 위한 포함 확장자 옵션 생성
build_include_extensions() {
    local include_opts=""
    local first=true
    for ext in "${INCLUDE_EXTENSIONS[@]}"; do
        if $first; then
            include_opts="-name \"$ext\""
            first=false
        else
            include_opts="$include_opts -o -name \"$ext\""
        fi
    done
    echo "\\( $include_opts \\)"
}

# Git에서 특정 파일이 제외되어야 하는지 확인
should_exclude_file() {
    local file="$1"

    # 제외 폴더 체크
    for dir in "${EXCLUDE_DIRS[@]}"; do
        if [[ "$file" == *"/$dir/"* ]]; then
            return 0
        fi
    done

    # 제외 파일 체크
    local basename=$(basename "$file")
    for exclude in "${EXCLUDE_FILES[@]}"; do
        # 와일드카드 패턴 지원
        if [[ "$basename" == $exclude ]]; then
            return 0
        fi
    done

    return 1
}

# 파일이 포함되어야 하는지 확인
should_include_file() {
    local file="$1"
    local basename=$(basename "$file")

    for pattern in "${INCLUDE_EXTENSIONS[@]}"; do
        # 와일드카드 패턴 처리
        case "$pattern" in
            \*.*)
                # 확장자 패턴 (예: *.go)
                local ext="${pattern#\*.}"
                if [[ "$basename" == *".$ext" ]]; then
                    return 0
                fi
                ;;
            .*\*)
                # 접두사 패턴 (예: .env*)
                local prefix="${pattern%\*}"
                if [[ "$basename" == "$prefix"* ]]; then
                    return 0
                fi
                ;;
            *\*)
                # 접미사 패턴 (예: README*)
                local prefix="${pattern%\*}"
                if [[ "$basename" == "$prefix"* ]]; then
                    return 0
                fi
                ;;
            *)
                # 정확한 파일명 매칭 (예: Dockerfile)
                if [[ "$basename" == "$pattern" ]]; then
                    return 0
                fi
                ;;
        esac
    done

    return 1
}
