package com.WebProject.jwt;

import com.WebProject.jwt.JwtTokenProvider;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class JwtAuthenticationFilter extends  OncePerRequestFilter  {


    private final JwtTokenProvider jwtTokenProvider;
    private final AccountDetailsService accountDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, AccountDetailsService accountDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.accountDetailsService = accountDetailsService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (!Objects.isNull(authorization)) {
            String atk = authorization.substring(7);
            try {
                Subject subject = jwtTokenProvider.getSubject(atk);
                String requestURI = request.getRequestURI();
                if (subject.getType().equals("RTK") && !requestURI.equals("/reissue")) {
                    throw new JwtException("토큰을 확인하세요.");
                }
                UserDetails userDetails = accountDetailsService.loadUserByUsername(subject.getEmail());
                Authentication token = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(token);
            } catch (JwtException e) {
                request.setAttribute("exception", e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}