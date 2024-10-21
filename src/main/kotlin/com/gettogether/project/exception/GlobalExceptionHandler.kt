package com.gettogether.project.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.NoSuchElementException

@ControllerAdvice
class GlobalExceptionHandler {

    /**
     * NoSuchElementException을 처리합니다.
     * 리소스가 존재하지 않는 경우 404 Not Found를 반환합니다.
     */
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoSuchElementException(ex: NoSuchElementException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 리소스를 찾을 수 없습니다.")
    }

    /**
     * MethodArgumentNotValidException을 처리합니다.
     * 요청 본문이 유효성 검사를 통과하지 못했을 때 400 Bad Request를 반환합니다.
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<String> {
        val errors = ex.bindingResult.fieldErrors.joinToString(", ") { "${it.field}: ${it.defaultMessage}" }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효성 검사 실패: $errors")
    }

    /**
     * 기타 예외를 처리합니다.
     * 500 Internal Server Error로 반환합니다.
     */
    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버에서 오류가 발생했습니다.")
    }
}
