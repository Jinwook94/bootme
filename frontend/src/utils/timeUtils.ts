export const getTimeSince = (createdAt: number) => {
  const SECOND = 1000;
  const MINUTE = 60 * SECOND;
  const HOUR = 60 * MINUTE;
  const DAY = 24 * HOUR;
  const MONTH = 30 * DAY;
  const now = Date.now();

  const timeDiff = now - createdAt;

  if (timeDiff < HOUR) {
    const minutes = Math.floor(timeDiff / MINUTE);
    return `${minutes}분 전`;
  } else if (timeDiff < DAY) {
    const hours = Math.floor(timeDiff / HOUR);
    return `${hours}시간 전`;
  } else if (timeDiff < 60 * DAY) {
    const days = Math.floor(timeDiff / DAY);
    return `${days}일 전`;
  } else {
    const months = Math.floor(timeDiff / MONTH);
    return `${months}개월 전`;
  }
};
