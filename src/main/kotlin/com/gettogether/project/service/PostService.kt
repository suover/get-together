package com.gettogether.project.service

import com.gettogether.project.domain.Post
import com.gettogether.project.repository.PostRepository
import com.gettogether.project.repository.UserRepository
import com.gettogether.project.repository.CategoryRepository
import com.gettogether.project.dto.PostRequest
import org.springframework.stereotype.Service
import java.util.NoSuchElementException

/**
 * 게시글과 관련된 서비스를 제공하는 클래스입니다.
 *
 * @property postRepository 게시글 데이터 접근을 위한 리포지토리
 * @property userRepository 사용자 데이터 접근을 위한 리포지토리
 * @property categoryRepository 카테고리 데이터 접근을 위한 리포지토리
 */
@Service
class PostService(
        private val postRepository: PostRepository,
        private val userRepository: UserRepository,
        private val categoryRepository: CategoryRepository
) {

    /**
     * 전체 게시글을 조회합니다.
     *
     * @return 모든 게시글을 포함하는 리스트
     */
    fun getAllPosts(): List<Post> {
        return postRepository.findAll()
    }

    /**
     * 특정 게시글을 게시글 ID로 조회합니다.
     *
     * @param postId 조회할 게시글의 ID
     * @return 게시글 ID에 해당하는 게시글
     * @throws NoSuchElementException 게시글이 존재하지 않을 경우 발생
     */
    fun getPostById(postId: Long): Post {
        return postRepository.findById(postId)
                .orElseThrow { NoSuchElementException("해당 게시글 ID의 게시글이 존재하지 않습니다: $postId") }
    }

    /**
     * 새로운 게시글을 작성합니다.
     *
     * @param request 게시글 작성 요청 DTO
     * @return 작성된 게시글
     */
    fun createPost(request: PostRequest): Post {
        val user = userRepository.findById(request.userId)
                .orElseThrow { NoSuchElementException("사용자를 찾을 수 없습니다.") }

        val category = categoryRepository.findById(request.categoryId)
                .orElseThrow { NoSuchElementException("카테고리를 찾을 수 없습니다.") }

        val post = Post(
                user = user,
                category = category,
                title = request.title,
                content = request.content
        )
        return postRepository.save(post)
    }

    /**
     * 기존 게시글을 수정합니다.
     *
     * @param postId 수정할 게시글 ID
     * @param request 게시글 수정 요청 DTO
     * @return 수정된 게시글
     */
    fun updatePost(postId: Long, request: PostRequest): Post {
        val post = postRepository.findById(postId)
                .orElseThrow { NoSuchElementException("해당 게시글이 존재하지 않습니다.") }

        val category = categoryRepository.findById(request.categoryId)
                .orElseThrow { NoSuchElementException("카테고리를 찾을 수 없습니다.") }

        post.title = request.title
        post.content = request.content
        post.category = category

        return postRepository.save(post)
    }

    /**
     * 특정 게시글을 삭제합니다.
     *
     * @param postId 삭제할 게시글 ID
     */
    fun deletePost(postId: Long) {
        if (!postRepository.existsById(postId)) {
            throw NoSuchElementException("해당 게시글이 존재하지 않습니다.")
        }
        postRepository.deleteById(postId)
    }
}
