package com.ss.contains.light.util;

import com.ss.contains.light.config.security.AdminUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author NotEdibleSalt
 * @version 1.0
 */
public class JWTUtil {

    private static final String secret = "$2a$10$0qjyFQVJXxfcVAL5UBxypuCE42q1b80hy4VpcOlzT4qLQyulaLh7u";

    /**
     * 生成令牌
     *
     * @param adminUserDetails 用户
     * @return 令牌
     */
    public static String generateToken(AdminUserDetails adminUserDetails) {

        Map<String, Object> claims = new HashMap<>(4);
        claims.put("id", adminUserDetails.getId());
        claims.put("sub", adminUserDetails.getUsername());
        return generateToken(claims);
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    public static String generateToken(Map<String, Object> claims) {

        LocalDateTime localDateTime = LocalDateTime.now();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(localDateTime.toInstant(ZoneOffset.UTC)))
                .setExpiration(Date.from(localDateTime.plusDays(1L).toInstant(ZoneOffset.UTC)))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        return token;
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public static String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    public static Claims getClaimsFromToken(String token) {

        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public static Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }


}
