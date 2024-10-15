package com.gettogether.project.repository

import com.gettogether.project.domain.CommentLike
import com.gettogether.project.domain.User
import com.gettogether.project.domain.Comment
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface CommentLikeRepository : JpaRepository<CommentLike, Long> {
    fun findByUserAndComment(user: User, comment: Comment): Optional<CommentLike>  // 특정 사용자가 댓글에 좋아요를 눌렀는지 확인
    fun countByComment(comment: Comment): Int  // 댓글의 좋아요 수 조회
}
