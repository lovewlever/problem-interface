package com.problem.pl.config

import com.problem.pl.commons.RequestMappingCommon
import com.problem.pl.interceptor.LoginVerificationInterceptor
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