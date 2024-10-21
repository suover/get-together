package com.gettogether.project.controller

import com.gettogether.project.domain.Post
import com.gettogether.project.domain.User
import com.gettogether.project.dto.PostRequest
import com.gettogether.project.service.PostService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime
import java.util.NoSuchElementException

@WebMvcTest(PostController::class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class PostControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var postService: PostService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private lateinit var posts: List<Post>

    @BeforeEach
    fun setup() {
        reset(postService)

        val user = User(
                email = "test@example.com",
                password = "password",
                name = "테스트 유저",
                nickname = "닉네임",
                createdAt = LocalDateTime.now()
        )

        posts = (1..10).map {
            Post(
                    id = it.toLong(),
                    title = "제목 $it",
                    content = "내용 $it",
                    user = user,
                    createdAt = LocalDateTime.now()
            )
        }
        `when`(postService.getAllPosts()).thenReturn(posts)
    }

    /**
     * -------------------- 조회(GET) API 테스트 케이스 --------------------
     */

    @Test
    fun `모든 게시글 조회 테스트`() {
        val result = mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk)
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(10))
                .andReturn()

        println("----- 모든 게시글 조회 -----")
        println("상태 코드: ${result.response.status} (OK - 요청이 성공적으로 처리되었습니다.)")
        posts.forEach { post ->
            println("Post ID: ${post.id}, 제목: ${post.title}, 내용: ${post.content}")
        }
        println("----------------------------")
    }

    @Test
    fun `특정 게시글 ID로 조회 테스트`() {
        val postId = 3L
        val specificPost = posts.first { it.id == postId }
        `when`(postService.getPostById(postId)).thenReturn(specificPost)

        val result = mockMvc.perform(get("/api/posts/$postId"))
                .andExpect(status().isOk)
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(postId))
                .andExpect(jsonPath("$.title").value(specificPost.title))
                .andExpect(jsonPath("$.content").value(specificPost.content))
                .andReturn()

        println("----- Post ID가 ${postId}인 게시글 조회 -----")
        println("상태 코드: ${result.response.status} (OK - 요청이 성공적으로 처리되었습니다.)")
        println("Post ID: ${specificPost.id}, 제목: ${specificPost.title}, 내용: ${specificPost.content}")
        println("----------------------------")
    }

    @Test
    fun `게시글이 없는 경우 조회 테스트`() {
        val emptyPosts = emptyList<Post>()
        `when`(postService.getAllPosts()).thenReturn(emptyPosts)

        val result = mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk)
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(0))
                .andReturn()

        println("----- 게시글이 없는 경우 -----")
        println("상태 코드: ${result.response.status} (OK - 요청은 성공적으로 처리되었으나, 데이터가 없습니다.)")
        println("조회된 게시글 개수: ${emptyPosts.size}")
        println("----------------------------")
    }

    /**
     * -------------------- 삽입(POST) API 테스트 케이스 --------------------
     */

    @Test
    fun `게시글 삽입 테스트 - 성공`() {
        val request = PostRequest(userId = 1L, categoryId = 1L, title = "새 게시글", content = "게시글 내용")
        val savedPost = Post(id = 1L, user = posts[0].user, category = null, title = request.title, content = request.content)

        `when`(postService.createPost(request)).thenReturn(savedPost)

        val result = mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value(request.title))
                .andExpect(jsonPath("$.content").value(request.content))
                .andReturn()

        println("----- 게시글 삽입 성공 -----")
        println("상태 코드: ${result.response.status} (Created - 새 자원이 성공적으로 생성되었습니다.)")
        println("새로 생성된 게시글 ID: ${savedPost.id}, 제목: ${savedPost.title}, 내용: ${savedPost.content}")
        println("----------------------------")
    }

    @Test
    fun `게시글 삽입 테스트 - 필수 값 누락`() {
        val request = PostRequest(userId = 1L, categoryId = 1L, title = "", content = "")

        val result = mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest)
                .andReturn()

        println("----- 게시글 삽입 실패 - 필수 값 누락 -----")
        println("상태 코드: ${result.response.status} (Bad Request - 요청이 잘못되었습니다.)")
        println("입력된 제목: ${request.title}, 입력된 내용: ${request.content}")
        println("----------------------------")
    }

    @Test
    fun `게시글 삽입 테스트 - 잘못된 카테고리 ID`() {
        val request = PostRequest(userId = 1L, categoryId = 999L, title = "새 게시글", content = "게시글 내용")

        `when`(postService.createPost(request)).thenThrow(NoSuchElementException("카테고리를 찾을 수 없습니다."))

        val result = mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound)
                .andReturn()

        println("----- 게시글 삽입 실패 - 잘못된 카테고리 ID -----")
        println("상태 코드: ${result.response.status} (Not Found - 해당 자원을 찾을 수 없습니다.)")
        println("입력된 카테고리 ID: ${request.categoryId}")
        println("----------------------------")
    }

    /**
     * -------------------- 수정(PUT) API 테스트 케이스 --------------------
     */

    @Test
    fun `게시글 수정 테스트 - 성공`() {
        val postId = 1L
        val request = PostRequest(userId = 1L, categoryId = 1L, title = "수정된 제목", content = "수정된 내용")
        val updatedPost = Post(id = postId, user = posts[0].user, category = null, title = request.title, content = request.content)

        `when`(postService.updatePost(postId, request)).thenReturn(updatedPost)

        val result = mockMvc.perform(put("/api/posts/$postId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.title").value(request.title))
                .andExpect(jsonPath("$.content").value(request.content))
                .andReturn()

        println("----- 게시글 수정 성공 -----")
        println("상태 코드: ${result.response.status} (OK - 자원이 성공적으로 수정되었습니다.)")
        println("수정된 게시글 ID: $postId, 제목: ${request.title}, 내용: ${request.content}")
        println("----------------------------")
    }

    @Test
    fun `게시글 수정 테스트 - 존재하지 않는 ID`() {
        val postId = 999L
        val request = PostRequest(userId = 1L, categoryId = 1L, title = "수정된 제목", content = "수정된 내용")

        `when`(postService.updatePost(postId, request)).thenThrow(NoSuchElementException("해당 게시글이 존재하지 않습니다."))

        val result = mockMvc.perform(put("/api/posts/$postId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound)
                .andReturn()

        println("----- 게시글 수정 실패 - 존재하지 않는 게시글 ID -----")
        println("상태 코드: ${result.response.status} (Not Found - 해당 자원을 찾을 수 없습니다.)")
        println("수정하려는 게시글 ID: $postId")
        println("----------------------------")
    }

    @Test
    fun `게시글 수정 테스트 - 필수 값 누락`() {
        val postId = 1L
        val request = PostRequest(userId = 1L, categoryId = 1L, title = "", content = "")

        val result = mockMvc.perform(put("/api/posts/$postId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest)
                .andReturn()

        println("----- 게시글 수정 실패 - 필수 값 누락 -----")
        println("상태 코드: ${result.response.status} (Bad Request - 요청이 잘못되었습니다.)")
        println("입력된 제목: ${request.title}, 입력된 내용: ${request.content}")
        println("----------------------------")
    }

    /**
     * -------------------- 삭제(DELETE) API 테스트 케이스 --------------------
     */

    @Test
    fun `게시글 삭제 테스트 - 성공`() {
        val postId = 1L

        doNothing().`when`(postService).deletePost(postId)

        val result = mockMvc.perform(delete("/api/posts/$postId"))
                .andExpect(status().isNoContent)
                .andReturn()

        println("----- 게시글 삭제 성공 -----")
        println("상태 코드: ${result.response.status} (No Content - 요청이 성공적으로 처리되었으나, 응답 본문은 없습니다.)")
        println("삭제된 게시글 ID: $postId")
        println("----------------------------")
    }

    @Test
    fun `게시글 삭제 테스트 - 존재하지 않는 ID`() {
        val postId = 999L

        `when`(postService.deletePost(postId)).thenThrow(NoSuchElementException("해당 게시글이 존재하지 않습니다."))

        val result = mockMvc.perform(delete("/api/posts/$postId"))
                .andExpect(status().isNotFound)
                .andReturn()

        println("----- 게시글 삭제 실패 - 존재하지 않는 게시글 ID -----")
        println("상태 코드: ${result.response.status} (Not Found - 해당 자원을 찾을 수 없습니다.)")
        println("삭제하려는 게시글 ID: $postId")
        println("----------------------------")
    }

    @Test
    fun `게시글 삭제 테스트 - 이미 삭제된 게시글`() {
        val postId = 1L

        println("----- 게시글 삭제 실패 - 이미 삭제된 게시글 -----")

        // 첫 번째 삭제는 성공
        doNothing().`when`(postService).deletePost(postId)

        val firstResult = mockMvc.perform(delete("/api/posts/$postId"))
                .andExpect(status().isNoContent)
                .andReturn()

        println("----- 게시글 첫 번째 삭제 성공 -----")
        println("상태 코드: ${firstResult.response.status} (No Content - 요청이 성공적으로 처리되었으나, 응답 본문은 없습니다.)")
        println("삭제된 게시글 ID: $postId")

        // 두 번째 삭제는 실패 (이미 삭제된 게시글)
        `when`(postService.deletePost(postId)).thenThrow(NoSuchElementException("해당 게시글이 존재하지 않습니다."))

        val secondResult = mockMvc.perform(delete("/api/posts/$postId"))
                .andExpect(status().isNotFound)
                .andReturn()

        println("----- 게시글 두 번째 삭제 실패 (이미 삭제됨) -----")
        println("상태 코드: ${secondResult.response.status} (Not Found - 해당 자원을 찾을 수 없습니다.)")
        println("삭제하려던 게시글 ID: $postId")
        println("----------------------------")
    }
}
