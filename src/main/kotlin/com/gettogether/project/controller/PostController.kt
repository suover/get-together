package com.gettogether.project.controller

import com.gettogether.project.domain.Post
import com.gettogether.project.service.PostService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 게시글과 관련된 REST API를 제공하는 컨트롤러입니다.
 *
 * @property postService 게시글 서비스
 */
@RestController
@RequestMapping("/api/posts")
class PostController(
        private val postService: PostService
) {

    /**
     * 모든 게시글을 조회합니다.
     *
     * @return 모든 게시글을 포함하는 리스트
     */
    @GetMapping
    @Operation(summary = "전체 게시글 조회", description = "모든 게시글을 조회합니다.")
    fun getAllPosts(): List<Post> {
        return postService.getAllPosts()
    }

    /**
     * 게시글 ID에 해당하는 게시글을 조회합니다.
     *
     * @param postId 조회할 게시글의 ID
     * @return 게시글 ID에 해당하는 게시글 객체
     * @throws NoSuchElementException 게시글이 존재하지 않을 경우 발생
     */
    @GetMapping("/{postId}")
    @Operation(summary = "특정 게시글 조회", description = "게시글 ID에 해당하는 특정 게시글을 조회합니다.")
    fun getPostById(
            @PathVariable postId: Long
    ): Post {
        return postService.getPostById(postId)
    }
}
