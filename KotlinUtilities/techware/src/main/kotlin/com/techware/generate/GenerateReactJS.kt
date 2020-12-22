package com.techware.generate

import com.techware.react.GenerateReactJS

class GenerateReactJS :GenerateSolution  {
    override fun generateFiles(targetDescriptor: String, targetFolder: String) {
        GenerateReactJS.generateReactJSFiles(targetDescriptor=targetDescriptor, targetFolder = targetFolder)
    }
}