package com.techware.generate

import kotlin.reflect.KClass

interface Generate {
    /**
     * gives a descriptor and generates a class
     */
    fun generateClass(fileName:String, className: String, packageName: String, targetFolder: String)

    /**
     * gives a class and generates a descriptor
     */
    fun<T:Any> generateDescriptor(kClass: KClass<T>, targetFolder: String)
}