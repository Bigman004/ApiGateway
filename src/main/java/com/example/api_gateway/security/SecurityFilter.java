package com.example.api_gateway.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Value("${shared.key}")
    private String sharedKey;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ModifiedRequest request1 = new ModifiedRequest(request);

        String signature = request.getHeader("x-paystack-signature");
        if(!(signature == null)){
            request1.addHeader("x-paystack-signature", signature);
        }
        String username = "user";
        String password = sharedKey;
        String credentials = username + ":" + password;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
        request1.addHeader("Authorization", "Basic " + encodedCredentials);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                "user", null, Arrays.asList(new SimpleGrantedAuthority("GATE_WAY"))
        );
        System.out.println(encodedCredentials);
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request1));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request1, response);

    }

}
