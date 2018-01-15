package net.todo

import org.springframework.boot.Banner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry


@SpringBootApplication
open class TodoApplication//: SpringBootServletInitializer()
{

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
    /** Registers an endpoint for STOMP messages. */
    @EnableWebSocketMessageBroker
    open class WebSocketConfig : AbstractWebSocketMessageBrokerConfigurer() {
        override fun registerStompEndpoints(registry: StompEndpointRegistry) {
            registry.addEndpoint("/stomp").withSockJS()
        }
    }
}
fun main(args: Array<String>) {
    //runApplication<TodoApplication>(*args)
    val app = SpringApplication(TodoApplication::class.java)
    app.setBannerMode(Banner.Mode.OFF)
    app.isWebEnvironment = true
    app.run(*args)
}
