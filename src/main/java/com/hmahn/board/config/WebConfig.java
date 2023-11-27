package com.hmahn.board.config;

import com.hmahn.board.config.component.LoginInterceptor;
import com.hmahn.board.config.component.LoginResolver;
import com.hmahn.board.config.component.WebInterceptor;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final LoginResolver loginResolver;
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new WebInterceptor())
                .order(1)
                .addPathPatterns("/*")
                .excludePathPatterns("/error"); // 해당 경로는 인터셉터가 가로채지 않는다.

        registry.addInterceptor(new LoginInterceptor())
                .order(2)
                .addPathPatterns("/*")
                .excludePathPatterns("/","/error"); // 해당 경로는 인터셉터가 가로채지 않는다
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginResolver);
    }
}
