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

## 🎯 **미션**
<details>

<summary> [미션4] 조회 REST API 만들기 </summary>

## 조회 REST API 및 테스트 코드
이번 미션에서는 게시글 조회 REST API를 개발하고, 이를 검증하기 위한 테스트 코드를 작성했습니다.  
API는 전체 게시글 조회와 특정 게시글 조회 두 가지 기능을 제공합니다.

## API 엔드포인트 설명

### 1. 전체 게시글 조회
- **경로:** `GET /api/posts`
- **설명:** 전체 게시글을 조회하여 반환합니다.
- **응답 예시**
  ```json
    {
      "postId": 1,
      "title": "제목 1",
      "content": "내용 1",
      "userId": 1,
      "categoryId": 1,
      "viewCount": 100,
      "createdAt": "2024-10-10T10:00:00"
    }
  ```

### 2. 특정 게시글 조회
- **경로:** `GET /api/posts/{postId}`
- **설명:** 특정 `postId`에 해당하는 게시글 정보를 반환합니다.
- **응답 예시**
  ```json
    {
      "postId": 3,
      "title": "제목 3",
      "content": "내용 3",
      "userId": 1,
      "categoryId": 1,
      "viewCount": 100,
      "createdAt": "2024-10-10T10:00:00"
    }
  ```

## 테스트 클래스 설명

1. **PostControllerTest**
    - **설명:** `PostController` 클래스에 대한 단위 테스트로, 컨트롤러 계층에서 API 요청이 예상대로 처리되는지 검증합니다.
    - **테스트 항목**
        - **모든 게시글 조회:** `/api/posts` 엔드포인트가 모든 게시글을 정상적으로 반환하는지 확인.
        - **특정 게시글 ID 조회:** `/api/posts/{postId}`로 특정 게시글을 정확하게 반환하는지 확인.
        - **게시글이 없는 경우 조회:** 게시글이 없는 경우, 빈 리스트를 반환하는지 확인.

2. **PostRepositoryTest**
    - **설명:** 데이터베이스와 직접 상호작용하는 `PostRepository`를 테스트하여 데이터 접근 계층의 기능이 올바르게 동작하는지 확인합니다.
    - **테스트 항목**
        - **모든 게시글 조회:** `findAll()` 메서드로 모든 게시글을 정상적으로 조회하는지 확인.
        - **특정 게시글 ID 조회:** `findById()`로 특정 게시글을 조회할 수 있는지 확인.
        - **게시글이 없는 경우 조회:** 데이터베이스에 게시글이 없는 경우, 빈 리스트가 반환되는지 확인.

3. **PostServiceTest**
    - **설명:** `PostService` 클래스의 비즈니스 로직을 검증하여, 서비스 계층에서 데이터가 정상적으로 처리되고 반환되는지 확인합니다.
    - **테스트 항목**
        - **모든 게시글 조회:** `getAllPosts()` 메서드를 통해 모든 게시글을 정상적으로 가져오는지 확인.
        - **특정 게시글 ID 조회:** `getPostById()` 메서드를 통해 특정 게시글이 정확하게 반환되는지 확인.
        - **게시글이 없는 경우 조회:** 게시글이 없을 때, 빈 리스트가 반환되는지 확인.

      ![{3FE8CBBB-7936-401A-ABF3-A5DEC0A881A0}](https://github.com/user-attachments/assets/5abe4619-b4e8-4ab8-ba3a-9979034c9d9a)

</details>