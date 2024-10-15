package com.gettogether.project.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
data class User(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id", nullable = false)
        val id: Long? = null,

        @Column(nullable = false, unique = true)
        val email: String,

        @Column(nullable = false)
        val password: String,

        @Column(nullable = false)
        val name: String,

        @Column(nullable = false, unique = true)
        val nickname: String,

        @Column(name = "is_active", nullable = false)
        val isActive: Boolean = true,

        @Enumerated(EnumType.STRING)
        @Column(name = "user_role", nullable = false)
        val role: UserRole = UserRole.USER,

        @Column(name = "created_at", nullable = false)
        val createdAt: LocalDateTime = LocalDateTime.now()
)

enum class UserRole {
    USER, ADMIN
}
