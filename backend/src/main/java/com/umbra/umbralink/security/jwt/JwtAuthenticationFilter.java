package com.umbra.umbralink.security.jwt;

import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.umbra.umbralink.security.UserDetailService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private JwtService jwtService;
  private UserDetailService userDetailService;

  public JwtAuthenticationFilter(JwtService jwtService, UserDetailService userDetailService) {
    this.jwtService = jwtService;
    this.userDetailService = userDetailService;
  }

  @SuppressWarnings("null")
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }
    String jwt = authHeader.substring(7);
    String email = jwtService.extractEmail(jwt);
    if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = userDetailService.loadUserByUsername(email);
      if (userDetails != null && jwtService.isTokenValid(jwt)) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
    }
    filterChain.doFilter(request, response);
  }

}
