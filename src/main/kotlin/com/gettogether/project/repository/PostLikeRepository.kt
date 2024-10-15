package com.gettogether.project.repository

import com.gettogether.project.domain.PostLike
import com.gettogether.project.domain.User
import com.gettogether.project.domain.Post
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface PostLikeRepository : JpaRepository<PostLike, Long> {
    fun findByUserAndPost(user: User, post: Post): Optional<PostLike>  // 특정 사용자가 게시글에 좋아요를 눌렀는지 확인
    fun countByPost(post: Post): Int  // 게시글의 좋아요 수 조회
}
