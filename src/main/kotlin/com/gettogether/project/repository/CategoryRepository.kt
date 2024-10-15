package com.gettogether.project.repository

import com.gettogether.project.domain.Category
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface CategoryRepository : JpaRepository<Category, Long> {
    fun findByName(name: String): Optional<Category>  // 카테고리 이름으로 조회
}
