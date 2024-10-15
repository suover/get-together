package com.gettogether.project.controller

import com.gettogether.project.domain.Post
import com.gettogether.project.domain.User
import com.gettogether.project.service.PostService
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime

@WebMvcTest(PostController::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var postService: PostService
    private lateinit var posts: List<Post>

    @BeforeAll
    fun setup() {
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

    @Test
    fun `모든 게시글 조회 테스트`() {
        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk)
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(10))

        println("----- 모든 게시글 조회 -----")
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

        mockMvc.perform(get("/api/posts/$postId"))
                .andExpect(status().isOk)
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(postId))
                .andExpect(jsonPath("$.title").value(specificPost.title))
                .andExpect(jsonPath("$.content").value(specificPost.content))

        println("----- Post ID가 ${postId}인 게시글 조회 -----")
        println("Post ID: ${specificPost.id}, 제목: ${specificPost.title}, 내용: ${specificPost.content}")
        println("----------------------------")
    }

    @Test
    fun `게시글이 없는 경우 조회 테스트`() {
        val emptyPosts = emptyList<Post>()
        `when`(postService.getAllPosts()).thenReturn(emptyPosts)

        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk)
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(0))

        println("----- 게시글이 없는 경우 -----")
        println("조회된 게시글 개수: ${emptyPosts.size}")
        println("----------------------------")
    }
}