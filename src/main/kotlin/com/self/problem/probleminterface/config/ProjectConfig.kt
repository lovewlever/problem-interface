package com.self.problem.probleminterface.config

import com.self.problem.probleminterface.commons.RequestMappingCommon
import com.self.problem.probleminterface.interceptor.LoginVerificationInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class ProjectConfig: WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*")
        super.addCorsMappings(registry)
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(LoginVerificationInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("${RequestMappingCommon.MAPPING_USER}/**")
        super.addInterceptors(registry)
    }
}