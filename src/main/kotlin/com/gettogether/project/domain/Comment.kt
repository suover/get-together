package com.gettogether.project.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "comments")
data class Comment(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "comment_id", nullable = false)
        val id: Long? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "post_id", nullable = false)
        val post: Post,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        val user: User,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "parent_comment_id")
        val parentComment: Comment? = null,

        @Column(nullable = false, columnDefinition = "TEXT")
        val content: String,

        @Column(name = "created_at", nullable = false)
        val createdAt: LocalDateTime = LocalDateTime.now(),

        @Column(name = "updated_at")
        val updatedAt: LocalDateTime? = null
)
