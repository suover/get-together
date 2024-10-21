package com.gettogether.project.controller

import com.gettogether.project.domain.Post
import com.gettogether.project.service.PostService
import com.gettogether.project.dto.PostRequest
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * 게시글과 관련된 REST API를 제공하는 컨트롤러입니다.
 *
 * @property postService 게시글 서비스
 */
@RestController
@RequestMapping("/api/posts")
@Validated
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
    fun getAllPosts(): ResponseEntity<List<Post>> {
        val posts = postService.getAllPosts()
        return ResponseEntity.ok(posts)
    }

    /**
     * 게시글 ID에 해당하는 게시글을 조회합니다.
     *
     * @param postId 조회할 게시글의 ID
     * @return 게시글 ID에 해당하는 게시글 객체
     */
    @GetMapping("/{postId}")
    @Operation(summary = "특정 게시글 조회", description = "게시글 ID에 해당하는 특정 게시글을 조회합니다.")
    fun getPostById(
            @PathVariable postId: Long
    ): ResponseEntity<Post> {
        val post = postService.getPostById(postId)
        return ResponseEntity.ok(post)
    }

    /**
     * 새로운 게시글을 작성합니다.
     *
     * @param request 게시글 작성 요청 DTO
     * @return 작성된 게시글
     */
    @PostMapping
    @Operation(summary = "게시글 작성", description = "새로운 게시글을 작성합니다.")
    fun createPost(
            @Valid @RequestBody request: PostRequest
    ): ResponseEntity<Post> {
        val createdPost = postService.createPost(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost)
    }

    /**
     * 기존 게시글을 수정합니다.
     *
     * @param postId 수정할 게시글의 ID
     * @param request 게시글 수정 요청 DTO
     * @return 수정된 게시글
     */
    @PutMapping("/{postId}")
    @Operation(summary = "게시글 수정", description = "기존 게시글을 수정합니다.")
    fun updatePost(
            @PathVariable postId: Long,
            @Valid @RequestBody request: PostRequest
    ): ResponseEntity<Post> {
        val updatedPost = postService.updatePost(postId, request)
        return ResponseEntity.ok(updatedPost)
    }

    /**
     * 특정 게시글을 삭제합니다.
     *
     * @param postId 삭제할 게시글의 ID
     */
    @DeleteMapping("/{postId}")
    @Operation(summary = "게시글 삭제", description = "특정 게시글을 삭제합니다.")
    fun deletePost(@PathVariable postId: Long): ResponseEntity<Void> {
        postService.deletePost(postId)
        return ResponseEntity.noContent().build()
    }
}
