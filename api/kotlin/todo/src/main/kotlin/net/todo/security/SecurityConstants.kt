package net.todo.security

object SecurityConstants {
    val SECRET = "SecretKeyToGenJWTs"
    val EXPIRATION_TIME: Long = 600000 // 10 minutes
    val TOKEN_PREFIX = "Bearer "
    val HEADER_STRING = "Authorization"
    val SIGN_UP_URL = "/users/sign-up"
    val LOGIN_URL = "/login"
    val ANGULAR = listOf("/",
            "/index.html",
            "/polyfills.bundle.js",
            "/styles.bundle.js",
            "/vendor.bundle.js",
            "/main.bundle.js",
            "/inline.bundle.js")
    val SWAGGER = listOf("/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**")
}