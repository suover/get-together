package com.gettogether.project.repository

import com.gettogether.project.domain.Comment
import com.gettogether.project.domain.Post
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long> {
    fun findAllByPost(post: Post): List<Comment>  // 특정 게시글의 모든 댓글 조회
    fun findAllByParentComment(parentComment: Comment): List<Comment>  // 답글 조회
}
