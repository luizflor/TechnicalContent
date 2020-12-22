package com.techware.generate

import com.techware.nodejs.GenerateNodeJS

class GenerateNodeJS : GenerateSolution {
    override fun generateFiles(targetDescriptor: String, targetFolder: String) {
        GenerateNodeJS.generateNodeJSFiles(targetDescriptor=targetDescriptor, targetFolder = targetFolder)
    }
}