package com.demo.controller

import com.demo.services.FileService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class DemoController(val fileService: FileService) {
    companion object {
        val logger = LoggerFactory.getLogger(DemoController::class.java)
    }
    @GetMapping("/status")
    fun getStatus() : ResponseEntity<Any> {
        return ResponseEntity.ok().body("OK")
    }
    @PostMapping("/upload")
    fun upload(@RequestParam file: MultipartFile): ResponseEntity<Any> {
        val fileName = file.originalFilename
        if(fileName.isNullOrEmpty()) {
            throw IllegalArgumentException("File name is required")
        }
        logger.info("upload: $fileName")
        fileService.saveData(file.inputStream)

        return ResponseEntity.ok().body(fileName)
    }

    @GetMapping("/download")
    fun download(@RequestParam name: String):ResponseEntity<Any>{
        if(name.isNullOrEmpty()) {
            throw java.lang.IllegalArgumentException("name is required")
        }
        val data = fileService.getData()

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName=\"$name\"")
                .body(data)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<Any> {
        return ResponseEntity.ok().body(e)
    }
}
