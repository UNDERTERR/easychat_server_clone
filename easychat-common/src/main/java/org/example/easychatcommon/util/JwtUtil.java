package org.example.easychatcommon.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    //我们的密钥
    private static final String KEY = "xiaojie666";

    public static String createJwt(Map<String, Object> claims, long time) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .signWith(getSecretKey())
                .compact();
    }

    public static Map<String, Object> parseJwt(String jwt) {
        Claims claims= Jwts.parserBuilder()
                .setAllowedClockSkewSeconds(60)
                .setSigningKey(getSecretKey())
                .build()
                //构建解析器后：正式解析
                .parseClaimsJws(jwt)
                .getBody();
        //默认存在的创建时间与过期时间字段
        claims.remove("iat");
        claims.remove("exp");
        return claims;
    }
    private static boolean verifyJwt(String jwt) {
        try{
            Jwts.parserBuilder()
                    .setAllowedClockSkewSeconds(60)
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(jwt);
        }catch (JwtException e){
            return false;
        }
        return true;
    }

    private static SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(KEY.getBytes());
    }
}
