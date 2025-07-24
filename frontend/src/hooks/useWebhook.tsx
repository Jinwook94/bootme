import { fetcher } from '../api/fetcher';
import * as jose from 'jose';
import {
  COURSE_BOOKMARKED,
  COURSE_CLICKED,
  COURSE_ID,
  POST_BOOKMARKED,
  POST_CLICKED,
  POST_ID,
} from '../constants/webhook';
import { useSecret } from './useSecret';

const useWebhook = (): useWebhookProps => {
  const { secrets } = useSecret();
  /**
   * 지정된 이벤트 발생시 서버로 웹훅 요청을 보내는 함수
   * 웹훅 요청 검증 목적으로 헤더에 JWT 첨부
   * @param {string} event - 발생한 이벤트명
   * @param {number} id - 해당 이벤트와 관련된 아이템의 id (ex: 코스 클릭 이벤트 발생 -> id = courseId)
   */
  const sendWebhookNoti = async (event: string, id: number) => {
    const jwt = await generateJwt();
    const webhookObject = createWebhookObject(event, id);

    fetcher
      .post('/webhook', webhookObject, {
        headers: {
          bootmesecrets: 'Bearer ' + jwt,
        },
      })
      .catch(error => {
        console.log(error);
      });
  };

  function generateJwt() {
    const ISSUER = 'bootme.co.kr';
    const AUDIENCE = secrets?.['bootmeAudience'];
    const SIGNING_KEY = secrets?.['bootmeSigningKey'];
    const alg = 'HS256';
    const typ = 'JWT';
    const signingKey = new TextEncoder().encode(SIGNING_KEY);

    return new jose.SignJWT({})
      .setProtectedHeader({ alg, typ })
      .setIssuedAt()
      .setExpirationTime('5m')
      .setIssuer(ISSUER as string)
      .setAudience(AUDIENCE as string)
      .sign(signingKey);
  }

  // todo: WebhookObject 필드 추가 - sessionId, memberId, 기타 Demographics, 기기정보, 브라우저 정보, IP
  function createWebhookObject(event: string, id: number): WebhookObject {
    let data = {};
    switch (event) {
      case COURSE_CLICKED:
      case COURSE_BOOKMARKED:
        data = { [COURSE_ID]: id.toString() };
        break;
      case POST_CLICKED:
      case POST_BOOKMARKED:
        data = { [POST_ID]: id.toString() };
        break;
    }
    return {
      event,
      data,
      currentUrl: window.location.href,
      createdAt: new Date().getTime(),
    };
  }

  return {
    sendWebhookNoti,
  };
};

export default useWebhook;

interface useWebhookProps {
  sendWebhookNoti: (event: string, id: number) => void;
}

type WebhookObject = {
  event: string;
  data: {
    [key: string]: string;
  };
  currentUrl: string;
  createdAt: number;
};
