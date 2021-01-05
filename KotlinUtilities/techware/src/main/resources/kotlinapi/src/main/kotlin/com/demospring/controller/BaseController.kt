package com.demospring.controller

import com.demospring.errors.ErrorCode
import com.demospring.errors.ErrorMessage
import com.demospring.exceptions.NotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController

@RestController
class BaseController() {
    companion object {
        private val logger = LoggerFactory.getLogger(BaseController::class.java)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<Any> {
        return if(e is NotFoundException) {
            val errorMessage = ErrorMessage(ErrorCode.DATA_NOT_FOUND.code, e.toString())
            ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(errorMessage)
        } else {
            val errorMessage = ErrorMessage(ErrorCode.INVALID_REQUEST.code, e.toString())
            ResponseEntity.badRequest().body(errorMessage)
        }
    }
}