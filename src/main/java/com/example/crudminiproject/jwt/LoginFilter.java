package com.example.crudminiproject.jwt;

import com.example.crudminiproject.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.Authentication;

import java.util.Collection;
import java.util.Iterator;


@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter { // UsernamePasswordAuthenticationFilter 를 상속받아서 로그인 필터를 생성.

    // 검증 방법? DB에서 username, password를 가져와서 UserDetailsService를 통해 검증
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        // 클라이언트 요청에서 username, password를 받아서 UsernamePasswordAuthenticationToken 객체를 생성
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        // test
        System.out.println("username: " + username);
        System.out.println("password: " + password);

        // Spring Security에서 username, password를 검증하기 위해서는 token에 담아야 함
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        // token에 담은 username, password를 검증. authenticationManager 가 검증을 담당
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
//        System.out.println("success");
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal(); // 인증이 성공하면 Principal 객체를 반환
        String username = customUserDetails.getUsername();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();
        // 토큰 생성 - 1일 동안 유효한 토큰
        String token = jwtUtil.createJwt("username", "role", 86400000L);


        response.addHeader("Authorization", "Bearer " + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {

        response.setStatus(401);
    }
}