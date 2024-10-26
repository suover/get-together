package com.gettogether.project.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
data class User(

        /**
         * todo
         * 엔티티는 데이터 클래스를 사용하지 않습니다.
         * 제약이 많이 주어집니다.
         * ex) equals() 오버라이딩
         * https://spoqa.github.io/2022/08/16/kotlin-jpa-entity.html
         *
         * 실무에서 not null, unique 등의 제약은 꼭 필요한 경우가 아니면 안 하는 것이 비즈니스 변화에 대응하기 좋습니다.
         */

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
