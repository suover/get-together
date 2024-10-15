package com.gettogether.project.service

import com.gettogether.project.domain.Post
import com.gettogether.project.repository.PostRepository
import org.springframework.stereotype.Service
import java.util.NoSuchElementException

/**
 * 게시글과 관련된 서비스를 제공하는 클래스입니다.
 *
 * @property postRepository 게시글 데이터 접근을 위한 리포지토리
 */
@Service
class PostService(
        private val postRepository: PostRepository
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
}