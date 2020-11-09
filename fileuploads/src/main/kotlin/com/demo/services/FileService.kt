package com.demo.services

import java.io.InputStream

interface FileService {
    fun saveData(fileInput: InputStream)
    fun getData(): String
}