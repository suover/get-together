package com.gettogether.project.repository

import com.gettogether.project.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): Optional<User>  // 이메일로 사용자 조회
    fun findByNickname(nickname: String): Optional<User>  // 닉네임으로 사용자 조회
}
