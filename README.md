# 🎮 Playgrown`d

> Spring Boot 기반으로 만드는 개인 토이 프로젝트 저장소  
> 현재는 **사자성어 퀴즈 + 랭킹 시스템**을 중심으로 개발 중입니다.

---

## ✨ 소개

**Playgrown`d**는 직접 기능을 설계하고 구현하면서  
백엔드, 데이터 처리, 화면 구성, 보안, 배포까지 한 번에 연습하기 위한 개인 프로젝트입니다.

현재 `javaQuiz` 브랜치에서는 다음과 같은 기능을 개발하고 있습니다.

- 사자성어 퀴즈 플레이
- 난이도 기반 점수 반영
- 사용자 랭킹 조회 및 갱신
- Thymeleaf 기반 서버 사이드 렌더링
- PostgreSQL 연동
- JPA + MyBatis 혼합 사용

---

## 🛠 Tech Stack

### Backend
- Java 17
- Spring Boot 3
- Spring Web
- Spring Validation
- Spring Security
- Spring Data JPA
- Spring Data JDBC
- MyBatis

### Frontend
- Thymeleaf
- Thymeleaf Layout Dialect
- jQuery

### Database
- PostgreSQL

### Build
- Gradle

---

## 📦 주요 기능

### 1. Quiz System
- 사자성어 문제를 풀며 점수를 획득합니다.
- 난이도에 따라 점수를 다르게 반영합니다.
- 라운드 진행형 구조로 퀴즈를 플레이합니다.

### 2. Ranking System
- 사용자 최고 점수를 저장합니다.
- 랭킹 목록을 조회할 수 있습니다.
- 새 점수가 기존 최고 점수보다 높을 때 기록을 갱신합니다.

### 3. Server Side Rendering
- Thymeleaf 템플릿 기반으로 화면을 구성합니다.
- jQuery를 이용해 화면 상호작용을 처리합니다.

---

## 📁 Project Structure

```bash
src
├─ main
│  ├─ java
│  │  └─ ... controller / service / entity / dto / config
│  └─ resources
│     ├─ templates
│     ├─ static
│     ├─ sql
│     └─ application.yml
└─ test
```

프로젝트 구조는 개발 진행에 따라 조금씩 변경될 수 있습니다.

---

## ⚙️ Configuration

기본적으로 PostgreSQL 연결과 Spring Boot 실행 환경이 필요합니다.

예시:

```yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/your_db
    username: your_username
    password: your_password

server:
  port: ${PORT:8080}
```

> 배포 환경에서는 `PORT` 환경변수를 우선 사용합니다.

---

## 🚀 Getting Started

### 1. Clone
```bash
git clone https://github.com/Dr0nix/playgrownd.git
cd playgrownd
```

### 2. Checkout branch
```bash
git checkout javaQuiz
```

### 3. Run
```bash
./gradlew bootRun
```

---

## 🔐 Notes

- Spring Security가 포함되어 있어 요청 처리 시 보안 설정의 영향을 받을 수 있습니다.
- JSON 요청 처리 시 CSRF 설정 여부를 함께 확인해야 합니다.
- JPA 설정이 `ddl-auto: validate` 기준이라면 실행 전 DB 스키마가 준비되어 있어야 합니다.

---

## 📈 TODO

- [x]  사자성어 퀴즈 > 난이도 분리 및 초성 힌트 기능 개발
- [x]  페이지 접속 로깅 개발
- [x]  사용자 닉네임 필드 추가
- [x]  사자성어 퀴즈 > 랭킹 기능 개발
- [x]  사자성어 퀴즈 > 난이도별 가점 추가
- [x]  RENDER 서버 spin-down 방지 크론탭 추가
- [ ]  공지사항 게시판
- [ ]  사용자 개인 포인트 제도 개발
- [ ]  이력서 페이지 개발
- [ ]  권한 엔티티 개발 및 사용자-권한-메뉴 구조 개발
- [ ]  로그인 이력 로깅 개발

---

## 📝 Why this project?

이 프로젝트는 단순한 CRUD 연습을 넘어서,  
직접 기능을 설계하고 문제를 해결하면서  
실제 서비스 흐름에 가까운 구조를 경험하기 위해 만들고 있습니다.

사실 그냥 하고싶은 것 떠오르는 것 다 만들고싶어서 하고 있어요 :-)

---

## 👤 Author

**Minju Jeon**  
Backend Developer in progress  
Java / Spring Boot / SQL / PostgreSQL / BigQuery
