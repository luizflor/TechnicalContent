package com.demo.services

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.InputStream
import java.nio.charset.StandardCharsets

@Service
class FileServiceImpl() : FileService {
    companion object {
        val logger = LoggerFactory.getLogger(FileServiceImpl::class.java)

        lateinit var fileData: String
    }

    override fun saveData(fileInput: InputStream) {
        fileData = String(fileInput.readBytes(), StandardCharsets.UTF_8)
    }

    override fun getData(): String {
        return fileData
    }
}