package com.gettogether.project.service

import com.gettogether.project.domain.Category
import com.gettogether.project.domain.Post
import com.gettogether.project.domain.User
import com.gettogether.project.repository.CategoryRepository
import com.gettogether.project.repository.PostRepository
import com.gettogether.project.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class PostServiceTest @Autowired constructor(
        private val postService: PostService,
        private val postRepository: PostRepository,
        private val userRepository: UserRepository,
        private val categoryRepository: CategoryRepository
) {

    private lateinit var user: User
    private lateinit var category: Category
    private lateinit var posts: List<Post>

    @BeforeAll
    fun setup() {
        user = userRepository.save(User(
                email = "test@example.com",
                password = "password",
                name = "테스트 유저",
                nickname = "닉네임",
                createdAt = LocalDateTime.now()
        ))

        category = categoryRepository.save(Category(name = "카테고리"))

        posts = (1..10).map {
            Post(
                    id = it.toLong(),
                    title = "제목 $it",
                    content = "내용 $it",
                    user = user,
                    category = category,
                    createdAt = LocalDateTime.now()
            )
        }
        postRepository.saveAll(posts)
    }

    @Test
    fun `모든 게시글 조회 테스트`() {
        val allPosts = postService.getAllPosts()
        assertThat(allPosts).hasSize(posts.size)

        println("----- 모든 게시글 조회 결과 -----")
        allPosts.forEach { post ->
            println("Post ID: ${post.id}, 제목: ${post.title}, 내용: ${post.content}")
        }
        println("-------------------------------")
    }

    @Test
    fun `특정 게시글 ID로 조회 테스트`() {
        val postId = 7L
        val specificPost = postService.getPostById(postId)
        assertThat(specificPost).isNotNull
        assertThat(specificPost.id).isEqualTo(postId)

        println("----- Post ID가 ${postId}인 게시글 조회 -----")
        println("Post ID: ${specificPost.id}, 제목: ${specificPost.title}, 내용: ${specificPost.content}")
        println("-----------------------------")
    }

    @Test
    fun `게시글이 없는 경우 조회 테스트`() {
        postRepository.deleteAll()
        val emptyPosts = postService.getAllPosts()
        assertThat(emptyPosts).isEmpty()

        println("----- 게시글이 없는 경우 -----")
        if (emptyPosts.isEmpty()) {
            println("조회된 게시글이 없습니다.")
        }
        println("-----------------------------")
    }
}
