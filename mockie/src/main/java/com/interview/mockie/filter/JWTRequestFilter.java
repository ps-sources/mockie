package com.interview.mockie.filter;

import com.interview.mockie.service.MyUserDetailsService;
import com.interview.mockie.util.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * Desc:
 * This class is the filter class, which intercepts the upcoming requests and validates the header part and ensure the valid JWT is available.
 * MUST BE IDENTIFIED by security configure
 */
@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    @Autowired
    MyUserDetailsService myUserDetailsService;
    @Autowired
    JWTUtility jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = httpServletRequest.getHeader("Authorization");
        String username = null;
        String jwt = null;

        //1: if authHeader is not null and is starts with Bearer
        if (Objects.nonNull(authHeader) && authHeader.startsWith("Bearer ")) {
            //extract jwt and recovering username
            jwt = authHeader.substring(7);
            username = jwtUtils.extractUsername(jwt);
        }
        // hold it in context
        if (null != username && null == SecurityContextHolder.getContext().getAuthentication()) {
            UserDetails userDetails = this.myUserDetailsService.loadUserByUsername(username);
            if (Boolean.TRUE.equals(jwtUtils.validateToken(jwt, userDetails))) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        //continue the request....
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
