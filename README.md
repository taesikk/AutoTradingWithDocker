# 📈 Auto Trading Bot with 한국투자증권

> 한국투자증권 Open API를 활용한 **주식 자동 매매 웹 서비스**  
> 토큰 발급부터 매수/매도까지 한 번에 처리할 수 있는 트레이딩 시스템입니다.

---

## 🚀 프로젝트 소개

이 프로젝트는 한국투자증권 Open API를 기반으로  
주식 거래 기능을 웹에서 간편하게 사용할 수 있도록 구현한 서비스입니다.

- REST API 기반 백엔드 (Spring Boot)
- jQuery & Vanilla JS 기반 프론트엔드
- Docker를 활용한 리눅스 서버 배포

---

## 🛠 기술 스택

### Backend
- Java 17
- Spring Boot
- REST API
- Maven

### Frontend
- HTML / CSS
- jQuery
- Vanilla JavaScript

### Infra
- Docker
- Linux (Ubuntu)
- Nginx
- Git Action

---

## 📌 주요 기능

### 🔐 1. Access Token 발급
- 한국투자증권 API 사용을 위한 토큰 발급
- 만료 시간 관리 및 상태 표시

### 💰 2. 매수 가능 수량 조회
- 계좌 및 종목 기준 매수 가능 수량 확인

### 📉 3. 매도 가능 수량 조회
- 보유 주식 기준 매도 가능 수량 확인

### 🟢 4. 주식 매수 및 매도
- 종목 코드 및 수량 입력 후 매수, 매도 주문 실행

### 🔴 5. 기간별 주식 자동 매입(예정)
- 적금식으로 주식을 자동으로 매입할 수 있도록 스케줄 등록

---

## 🖥 화면 구성

- 토큰 발급 및 상태 확인 UI
- 매수/매도 가능 조회 탭
- 매수/매도 주문 탭

---

## 📂 프로젝트 구조
```
AutoTradingWithDocker
├── nodejs-frontend/ # 프론트엔드 (Node.js + jQuery)
│ ├── config/ # 설정 파일
│ ├── static/ # JS, CSS 등 정적 리소스
│ ├── templates/ # HTML 템플릿
│ ├── Dockerfile # 프론트 Docker 설정
│ ├── package.json
│ └── server.js # 프론트 서버 실행 파일
│
├── springboot-backend/ # 백엔드 (Spring Boot)
│ ├── src/
│ │ ├── main/
│ │ │ ├── java/ # Controller, Service 등
│ │ │ └── resources/ # 설정 파일 (application.yml 등)
│ │ └── test/
│ ├── Dockerfile # 백엔드 Docker 설정
│ ├── pom.xml
│ ├── mvnw
│ └── mvnw.cmd
│
├── .gitignore
└── README.md
```

---

## ⚙️ 실행 방법

### 1️⃣ 웹 페이지 접속

```
http://taesikk.duckdns.org
