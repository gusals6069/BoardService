package com.hmahn.board.config;

import com.hmahn.board.service.user.UserAuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity  // 해당 애노테이션을 붙인 필터(현재 클래스)를 스프링 필터체인에 등록.
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserAuthService userAuthService; // 커스텀한 OAuth2UserService DI.

    // 인코더를 빈으로 등록.
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/members/**","/image/**");    // /image/** 있는 모든 파일들은 시큐리티 적용을 무시한다.
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());    // 정적인 리소스들에 대해서 시큐리티 적용 무시.
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .anyRequest()	// 모든 요청에 대해서 허용하라.
                .permitAll()
            .and()
                .csrf().disable()
                .headers().frameOptions().disable()
            .and()
                .logout()
                .logoutSuccessUrl("/")	// 로그아웃에 대해서 성공하면 "/"로 이동
            .and()
                .oauth2Login()
                .defaultSuccessUrl("/")
                .userInfoEndpoint()
                .userService(userAuthService);
    }
}