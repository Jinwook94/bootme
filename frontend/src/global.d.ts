declare module '*.jpg';
declare module '*.png';
declare module '*.jpeg';
declare module '*.gif';
declare module '*.svg' {
  const content: any;
  export default content;
}

declare interface Window {
  Kakao: any;
  naver: any;
}
