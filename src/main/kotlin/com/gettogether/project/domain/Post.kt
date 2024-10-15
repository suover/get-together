package com.gettogether.project.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "posts")
data class Post(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "post_id", nullable = false)
        val id: Long? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        val user: User,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "category_id")
        val category: Category? = null,

        @Column(nullable = false)
        val title: String,

        @Column(nullable = false, columnDefinition = "TEXT")
        val content: String,

        @Column(name = "view_count", nullable = false)
        val viewCount: Int = 0,

        @Column(name = "created_at", nullable = false)
        val createdAt: LocalDateTime = LocalDateTime.now(),

        @Column(name = "updated_at")
        val updatedAt: LocalDateTime? = null
)
