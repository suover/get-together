package com.gettogether.project.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "comment_likes", uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "comment_id"])])
data class CommentLike(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "like_id", nullable = false)
        val id: Long? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        val user: User,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "comment_id", nullable = false)
        val comment: Comment,

        @Column(name = "created_at", nullable = false)
        val createdAt: LocalDateTime = LocalDateTime.now()
)
