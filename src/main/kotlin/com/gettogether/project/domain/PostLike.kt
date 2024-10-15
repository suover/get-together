package com.gettogether.project.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "post_likes", uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "post_id"])])
data class PostLike(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "like_id", nullable = false)
        val id: Long? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        val user: User,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "post_id", nullable = false)
        val post: Post,

        @Column(name = "created_at", nullable = false)
        val createdAt: LocalDateTime = LocalDateTime.now()
)
