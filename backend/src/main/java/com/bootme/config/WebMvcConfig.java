package com.bootme.config;

import com.bootme.auth.util.AuthenticationArgumentResolver;
import com.bootme.common.interceptor.IPInterceptor;
import com.bootme.common.interceptor.TokenValidationInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,DELETE,TRACE,OPTIONS,PATCH,PUT";

    private final TokenValidationInterceptor tokenValidationInterceptor;
    private final IPInterceptor ipInterceptor;
    private final AuthenticationArgumentResolver authenticationArgumentResolver;
    private final String[] allowedOrigins;

    public WebMvcConfig(TokenValidationInterceptor tokenValidationInterceptor,
                        IPInterceptor ipInterceptor,
                        AuthenticationArgumentResolver authenticationArgumentResolver,
                        @Value("${allowed-origins}") String allowedOrigins) {
        this.tokenValidationInterceptor = tokenValidationInterceptor;
        this.ipInterceptor = ipInterceptor;
        this.authenticationArgumentResolver = authenticationArgumentResolver;
        this.allowedOrigins = allowedOrigins.split(",");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods(ALLOWED_METHOD_NAMES.split(","))
                .allowedHeaders("*")
                .allowCredentials(true)
                .exposedHeaders("Location");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenValidationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login")
                .excludePathPatterns("/logout")
                .excludePathPatterns("/docs/**");
        registry.addInterceptor(ipInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationArgumentResolver);
    }

}
