package com.techware.nodejs

import com.techware.generate.Generate
import kotlin.reflect.KClass

class GenerateNodeJSImpl: Generate {
    override fun generateClass(fileName: String, className: String, packageName: String, targetFolder: String) {
        GenerateNodeJS.generateFakeNodeJS(
            fileName = fileName,
            className = className,
            packageName = "",
            targetFolder = targetFolder
        )
    }

    override fun <T : Any> generateDescriptor(kClass: KClass<T>, targetFolder: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}