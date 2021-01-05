package com.techware.generate

import com.techware.springapi.GenerateSpringApi

class GenerateSpringApi : GenerateSolution {
    override fun generateFiles(targetDescriptor: String, targetFolder: String) {
        GenerateSpringApi.generateSpringFiles(targetDescriptor = targetDescriptor,targetFolder = targetFolder)
    }
}