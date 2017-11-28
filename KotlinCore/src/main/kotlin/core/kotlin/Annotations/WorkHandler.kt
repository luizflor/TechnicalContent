package core.kotlin

import jdk.nashorn.internal.runtime.linker.Bootstrap

@Target(AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class WorkHandler(val useThreadPool: Boolean = true)


