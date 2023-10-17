[![frontend deployment](https://github.com/Jinwook94/bootme/actions/workflows/frontend-deploy.yml/badge.svg)](https://github.com/Jinwook94/bootme/actions/workflows/frontend-deploy.yml)
[![backend deployment](https://github.com/Jinwook94/bootme/actions/workflows/backend-deploy.yml/badge.svg)](https://github.com/Jinwook94/bootme/actions/workflows/backend-deploy.yml)

## 프로젝트 소개
- 소프트웨어 커리큘럼 등 개발자 취업 관련 정보 제공 서비스

<br>

## 기술 스택
- **TypeScript, React**, Webpack, Styled-components, React Query 
- **Java11, Spring Boot, Spring Data JPA**, Spring REST Docs, Logback, Mockito, Mockmvc, JUnit5
- AWS CodeDeploy, CloudWatch, EC2 (ALB, Auto Scaling), S3, RDS(Aurora), Secrets Manager

<br>

## 인프라
### HTTP 요청 처리 과정
<img src="https://github.com/Jinwook94/bootme/assets/44575214/3a080bea-3e11-4b79-b883-bef65aa48c31">

### Frontend CI/CD
<img src="https://github.com/Jinwook94/bootme/assets/44575214/6351b7f7-6ff3-49de-b006-aaf07224932e">

### Backend CI/CD
<img src="https://github.com/Jinwook94/bootme/assets/44575214/b5bea313-1cf5-4bb8-b059-0a0cfd4cb28a">

### CloudWatch 모니터링

```mermaid
flowchart LR

subgraph EC2
    subgraph Springboot
        1[Tomcat]
				2[ApplicationLogic]
    end
		subgraph Logs
			3
			4
			5
			6
		end
end

1 --logback--> 3[accessLog-text.log]
1 --logback--> 4[accessLog-json.log]
2 --logback--> 5[generalLog-text.log]
2 --logback--> 6[generalLog-json.log]

subgraph CloudWatch
	7[ Log Groups<br>ㅡㅡㅡㅡㅡㅡㅡㅡㅡ<br> springboot/access/text <br> springboot/access/json <br> springboot/general/text <br> springboot/general/json <br>]
end

Logs --CloudWatch Agent--> CloudWatch

Springboot:::springboot
CloudWatch:::cloudwatch
classDef springboot fill:#a7f49a,stroke:#FFF
classDef cloudwatch fill:#f2d5e0, stroke:#FFF
```

<br>

## 주요 요청 처리 흐름

<details>
  <summary>로그인 요청</summary>
  <img width="800" alt="image" src="https://github.com/Jinwook94/bootme/assets/44575214/23e2cf53-9460-4b2c-8010-705c602b3205">
</details>
<details>
    <summary>엑세스 토큰 검증 및 재발급</summary>
    <img alt="image" src="https://github.com/Jinwook94/bootme/assets/44575214/72d225b2-b1b9-4ecc-a69f-085dee4237a8">
    <img width="350" alt="image" src="https://github.com/Jinwook94/bootme/assets/44575214/a847dfd5-1afe-4b13-b66f-502b9b251be2">
</details>

<br>

## API 문서

- https://api.bootme.co.kr/docs/swagger/index.html
- https://api.bootme.co.kr/docs/rest/index.html

<br>
