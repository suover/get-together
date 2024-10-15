package com.gettogether.project.repository

import com.gettogether.project.domain.Post
import com.gettogether.project.domain.Category
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long> {
    override fun findAll(): List<Post>  // 전체 게시글 조회
    fun findAllByCategory(category: Category): List<Post>  // 특정 카테고리별 게시글 조회
}
