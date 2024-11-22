package dev.playground.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfiguration {
    
    /**
     * Configuration just for demo purpose, not recommended to use in production
     */
    @Bean
    fun getCorsConfiguration(): WebMvcConfigurer {
        return object: WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                //super.addCorsMappings(registry)
                registry.addMapping("/**")
                    .allowedOrigins("*")
                    .allowedMethods("*")
                    .allowedHeaders("*")
            }
        }
    }
    
}