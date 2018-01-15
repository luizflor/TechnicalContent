package net.todo.security

import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CorsFilter : Filter {
    override fun init(p0: FilterConfig?) {
        TODO("not implemented")
    }

    override fun destroy() {
        TODO("not implemented")
    }

    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, filterChain: FilterChain) {
        val response = servletResponse as HttpServletResponse
        val request = servletRequest as HttpServletRequest

        response.setHeader("Access-Control-Allow-Origin", "*") // You should edit this
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS")
        response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", true.toString())

        if ("OPTIONS".equals(request.method, ignoreCase = true)) {
            //response.status = HttpServletResponse.SC_OK
            response.status = HttpServletResponse.SC_ACCEPTED
        } else {
            filterChain.doFilter(servletRequest, servletResponse)
        }
    }
}