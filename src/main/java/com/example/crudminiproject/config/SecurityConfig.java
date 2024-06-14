package com.example.crudminiproject.config;

import com.example.crudminiproject.jwt.JWTFilter;
import com.example.crudminiproject.jwt.JWTUtil;
import com.example.crudminiproject.jwt.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity // 해당 클래스가 SpringSecurity 에서도 관리가 된다.
@RequiredArgsConstructor
public class SecurityConfig {

    // AuthenticationConfiguration 객체를 주입
    private final AuthenticationConfiguration authenticationConfiguration;
    // JWTUtil 객체를 주입
    private final JWTUtil jwtUtil;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // 비밀번호 암호화. BCryptPasswordEncoder를 사용하지 않고 PasswordEncoder 타입으로 return 시 의존성 주입이 안된다.
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // JWT를 통한 인증/인가를 위해 stateless 상태로 설정하는 것이 중요.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //csrf disable
        http
                .csrf((auth) -> auth.disable());
        //From 로그인 방식 disable
        http
                .formLogin((auth) -> auth.disable());

        //http basic 인증 방식 disable
        http
                .httpBasic((auth) -> auth.disable());

        //경로별 인가 작업
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login", "/", "/join").permitAll() //로그인, 회원가입은 인증 없이 접근 가능
                        .anyRequest().authenticated());
        //JWTFilter 등록
        http
                .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        //필터 추가. LoginFilter()는 인자를 받음. AuthenticationManager() 메소드에 authenticationConfiguration 객체를 넣어야 함.
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);

        //세션 설정
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }



}
