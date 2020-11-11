///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.elcom.vitalsign.auth.jwt;
//
//import com.elcom.vitalsign.auth.CustomAccountDetails;
//import java.util.Date;
//
//import org.springframework.stereotype.Component;
//import io.jsonwebtoken.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// *
// * @author Admin
// */
//@Component
//public class JwtTokenProvider {
//    
//    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);
//    private final String JWT_SECRET = "elcom@123_2020";
//    //private final long JWT_EXPIRATION = 604800000L;
//
//    public String generateToken(CustomAccountDetails accountDetails) {
//        // Lấy thông tin account
//        Date now = new Date();
//        //Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
//        // Tạo chuỗi json web token từ id của account.
//        return Jwts.builder()
//                   .setSubject(accountDetails.getAccount().getId().toString())
//                   .setIssuedAt(now)
//                   //.setExpiration(expiryDate)
//                   .setExpiration(null)
//                   .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
//                   .compact();
//    }
//
//    public String getAccountIdFromJWT(String token) {
//        Claims claims = Jwts.parser()
//                            .setSigningKey(JWT_SECRET)
//                            .parseClaimsJws(token)
//                            .getBody();
//
//        return claims.getSubject();
//    }
//
//    public boolean validateToken(String authToken) {
//        try {
//            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
//            return true;
//        } catch (MalformedJwtException ex) {
//            LOGGER.error("Invalid JWT token");
//        } catch (ExpiredJwtException ex) {
//            LOGGER.error("Expired JWT token");
//        } catch (UnsupportedJwtException ex) {
//            LOGGER.error("Unsupported JWT token");
//        } catch (IllegalArgumentException ex) {
//            LOGGER.error("JWT claims string is empty.");
//        }
//        return false;
//    }
//}
//
