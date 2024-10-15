# 👥 GetTogether

**다양한 활동에서 함께할 사람을 찾고 소통할 수 있는 웹 커뮤니티 플랫폼**

사용자가 함께 활동할 사람을 찾거나 소통할 수 있는 서비스입니다.

## 📌 **프로젝트 소개**

### 🌟 **프로젝트 주제**

### GetTogether

함께할 사람을 찾고, 소통할 수 있는 웹 커뮤니티 플랫폼

### 💡 **개발 동기**

사람들이 공부, 취미, 운동 등 다양한 활동에서 함께할 사람을 쉽게 찾을 수 있도록 지원하는 플랫폼을 만들고자 기획했습니다.

### ⚙️ **핵심 기능**

1. **회원 시스템**: 사용자는 회원가입 후 로그인하여 게시글 작성, 댓글 작성, 좋아요 등의 서비스를 이용할 수 있습니다.
2. **게시글 작성**: 사용자는 활동에 대한 게시글을 작성하여, 함께할 사람을 찾고 자신의 활동을 공유할 수 있습니다.
3. **댓글 작성**: 게시글에 댓글을 달아 다른 사용자와 의견을 나누고 소통할 수 있습니다.
4. **좋아요**: 게시글에 좋아요를 눌러, 사용자들이 관심 있는 주제에 대해 피드백을 주고받을 수 있습니다.
5. **검색**: 사용자는 원하는 검색어를 입력하여 게시글을 쉽게 찾을 수 있습니다.

### 🔧 **개발 환경**

- Kotiln
- Spring Boot
- JPA
- Thymeleaf
- Gradle
- MySQL
- IntelliJ

### 🗂️ **ERD**
![{95D697DC-1501-4415-A464-2D8702F93C0B}](https://github.com/user-attachments/assets/c803a23d-67d8-4057-a758-dd995d44fba0)

### 🔗 [ERD 링크](https://www.erdcloud.com/d/oCyiHz4DwMWK3Pybe)

## 📋 **API**

### 👤 **사용자 API**

| 기능            | 메서드   | 엔드포인트                       |
|-----------------|----------|----------------------------------|
| 사용자 회원가입  | `POST`   | `/api/users`                     |
| 사용자 로그인    | `POST`   | `/api/users/login`               |
| 사용자 로그아웃  | `POST`   | `/api/users/logout`              |
| 사용자 정보 조회 | `GET`    | `/api/users/{userId}`            |
| 사용자 정보 수정 | `PUT`    | `/api/users/{userId}`            |
| 사용자 비활성화  | `PATCH`  | `/api/users/{userId}/deactivate` |

### 📝 **게시글 API**

| 기능              | 메서드   | 엔드포인트                       |
|-------------------|----------|----------------------------------|
| 게시글 작성        | `POST`   | `/api/posts`                     |
| 게시글 목록 조회   | `GET`    | `/api/posts`                     |
| 특정 게시글 조회   | `GET`    | `/api/posts/{postId}`            |
| 게시글 수정        | `PUT`    | `/api/posts/{postId}`            |
| 게시글 삭제        | `DELETE` | `/api/posts/{postId}`            |
| 게시글 좋아요      | `POST`   | `/api/posts/{postId}/like`       |
| 게시글 좋아요 취소 | `DELETE` | `/api/posts/{postId}/like`       |

### 💬 **댓글 및 답글 API**

| 기능                | 메서드   | 엔드포인트                           |
|---------------------|----------|--------------------------------------|
| 댓글/답글 작성       | `POST`   | `/api/posts/{postId}/comments`       |
| 댓글/답글 목록 조회  | `GET`    | `/api/posts/{postId}/comments`       |
| 댓글/답글 수정       | `PUT`    | `/api/comments/{commentId}`          |
| 댓글/답글 삭제       | `DELETE` | `/api/comments/{commentId}`          |
| 댓글/답글 좋아요     | `POST`   | `/api/comments/{commentId}/like`     |
| 댓글/답글 좋아요 취소 | `DELETE` | `/api/comments/{commentId}/like`     |
