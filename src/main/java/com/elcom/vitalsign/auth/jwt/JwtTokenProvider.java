package com.elcom.vitalsign.auth.jwt;

import com.elcom.vitalsign.auth.CustomAccountDetails;
import java.util.Date;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author anhdv
 */
@Component
public class JwtTokenProvider {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);
    private final String JWT_SECRET = "elcom@123_2020";
    //private final long JWT_EXPIRATION = 604800000L;

    public String generateToken(CustomAccountDetails userDetails) {
        // Lấy thông tin user
        Date now = new Date();
        //Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        // Tạo chuỗi json web token từ id của user.
        return Jwts.builder()
                   .setSubject(userDetails.getAccount().getId().toString())
                   .setIssuedAt(now)
                   //.setExpiration(expiryDate)
                   .setExpiration(null)
                   .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                   .compact();
    }

    public String getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                            .setSigningKey(JWT_SECRET)
                            .parseClaimsJws(token)
                            .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            LOGGER.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            LOGGER.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            LOGGER.error("JWT claims string is empty.");
        }
        return false;
    }
}
