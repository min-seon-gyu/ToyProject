package com.WebProject.jwt;

import com.WebProject.Member.MemberDetailsService;
import com.WebProject.exception.BadRequestException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
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
    private final MemberDetailsService memberDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, MemberDetailsService memberDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDetailsService = memberDetailsService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (!Objects.isNull(authorization)) {
            String atk = authorization.substring(7);
            try {
                Subject subject = jwtTokenProvider.getSubject(atk);
                String requestURI = request.getRequestURI();

                if(subject.getType().equals("RTK") && !requestURI.equals("/member/reissue")){
                    request.setAttribute("exception", "토큰을 다시 확인해주세요");
                }

                UserDetails userDetails = memberDetailsService.loadUserByUsername(subject.getEmail());
                Authentication token = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(token);
            } catch (ExpiredJwtException e) {
                request.setAttribute("exception", e.getMessage());
            } catch (JwtException e) {
                request.setAttribute("exception", e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}