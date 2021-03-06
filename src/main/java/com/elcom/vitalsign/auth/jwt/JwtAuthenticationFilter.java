package com.elcom.vitalsign.auth.jwt;

import com.elcom.vitalsign.service.impl.AuthServiceImpl;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author anhdv
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthServiceImpl authService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            //String jwt = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3ZjA5NWE2Yi1kODhmLTQ2NDMtOGRhYy00Yzc0MzkwNWY1YzMiLCJpYXQiOjE1OTM3NjQwMDAsImV4cCI6MTU5NDM2ODgwMH0.pQAEJCmutxU2CRS1gBraZAwpOf8FcgtgB5c1CtXuXiVmJV5vcnFnQV0D6BHB9VMa1LBBiAKdgZhmHNBc9w7GWA";

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                String uuid = tokenProvider.getUserIdFromJWT(jwt);

                UserDetails userDetails = authService.loadUserByUuid(uuid);
                if(userDetails != null) {
                    UsernamePasswordAuthenticationToken authentication
                            = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception ex) {
            LOGGER.error("failed on set user authentication", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        
        String bearerToken = request.getHeader("Authorization");
        
        // Kiểm tra xem header Authorization có chứa thông tin jwt không
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
            return bearerToken.substring(7);
        
        return null;
    }
}
