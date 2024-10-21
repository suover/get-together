package com.gettogether.project.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class PostRequest(
        @field:NotNull(message = "작성자 ID는 필수 항목입니다.")
        val userId: Long,         // 게시글 작성자 ID

        @field:NotNull(message = "카테고리 ID는 필수 항목입니다.")
        val categoryId: Long,     // 카테고리 ID

        @field:NotBlank(message = "제목은 필수 항목입니다.")
        val title: String,        // 게시글 제목

        @field:NotBlank(message = "내용은 필수 항목입니다.")
        val content: String       // 게시글 내용
)