package com.problem.pl.interceptor

import org.springframework.boot.origin.Origin
import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 过滤 跨域请
 */
@Component
@WebFilter(urlPatterns = ["/*"], filterName = "CorsFilter")
class CorsFilter : Filter {
    override fun doFilter(res: ServletRequest?, resp: ServletResponse?, chain: FilterChain?) {
        val response = resp as HttpServletResponse
        val request = res as HttpServletRequest
        val origin = request.getHeader("Origin") ?: request.getHeader("Referer")
        response.setHeader("Access-Control-Allow-Origin", origin)
        response.setHeader("Access-Control-Allow-Credentials", "true")
        response.setHeader("Access-Control-Allow-Methods","POST, GET, PATCH, DELETE, PUT,OPTIONS")
        response.setHeader("Access-Control-Max-Age", "3600")
        response.setHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept,Cookie,token")
        chain?.doFilter(res, resp)
    }
}