package com.kanban.taskmanagement.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {
    /**
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String header = request.getHeader("Authorization");
        String path = request.getRequestURI();

//        if (path.startsWith("/api/v1/kanbans/users/public")) {
//            filterChain.doFilter(request,response);
//            return;
//        }

        if (header == null || !header.startsWith("Bearer ")) {
            throw new ServletException("Invalid or missing token");
        } else {
            String token = header.substring(7);


            Claims claims = Jwts.parser().setSigningKey("secret-key").parseClaimsJws(token).getBody();
            request.setAttribute("current-user", claims.get("email"));
            filterChain.doFilter(request, response);
        }

    }
}
