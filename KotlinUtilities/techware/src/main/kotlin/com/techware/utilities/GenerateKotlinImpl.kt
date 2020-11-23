package com.techware.utilities

import kotlin.reflect.KClass

class GenerateKotlinImpl: Generate {
    override fun generateClass(fileName:String, className: String, packageName: String, targetFolder: String) {
        Descriptor.generateClass(fileName=fileName,packageName = packageName,className = className, targetFolder = targetFolder)
    }

    override fun<T:Any> generateDescriptor(kClass: KClass<T>, targetFolder: String) {
        Descriptor.generateDescriptor(kClass=kClass, targetFolder=targetFolder)
    }
}