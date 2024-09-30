package com.jh.cavy.jwt;


import com.jh.cavy.cache.service.CacheService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtTokenUtil implements InitializingBean {
    @Resource
    private CacheService cacheService;
    private final JwtProperties jwtProperties;
    private Key key;


    @Autowired
    public JwtTokenUtil(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    //属性初始化后会执行
    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(this.jwtProperties.getBase64Secret());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Map<String, Object> claims) {
        Assert.notNull(claims.get("scope"), "Scope must not be null!");
        claims.put("iat", new Date());
        Date expirationDate = new Date(System.currentTimeMillis() + this.jwtProperties.getTokenValidityInSeconds());
        return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(this.key, SignatureAlgorithm.HS512).compact();
    }

    public String generateToken(JwtUser jwtUser, String... scopes) {
        Assert.notNull(scopes, "Scope must not be null!");
        Map<String, Object> claims = new HashMap<>(2);
        claims.put("account", jwtUser.getAccount());
        claims.put("username", jwtUser.getUsername());
        claims.put("sub", jwtUser.getAccount());
        claims.put("iat", new Date());
        claims.put("scope", Arrays.asList(scopes));
        return generateToken(claims);
    }

    public Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            log.error("token过期 请重新登录", e);
        } catch (Exception e) {
            log.error("解析token异常", e);
        }
        return claims;
    }

    public String getAccountFromToken(String token) {
        String account = null;
        try {
            Claims claims = getClaimsFromToken(token);
            account = claims.get("account").toString();
        } catch (Exception e) {
            log.error("获取token中account异常", e);
        }
        return account;
    }

    public String getAccountFromRequest(HttpServletRequest request) {
        String account = null;
        try {
            Claims claims = getClaimsFromToken(getToken(request));
            account = claims.get("account").toString();
        } catch (Exception e) {
            log.error("获取request中account异常", e);
        }
        return account;
    }

    public String getUsernameFromToken(String token) {
        String username = null;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.get("username").toString();
        } catch (Exception e) {
            log.error("获取token中username异常", e);
        }
        return username;
    }

    public String getUsernameFromRequest(HttpServletRequest request) {
        String username = null;
        try {
            Claims claims = getClaimsFromToken(getToken(request));
            username = claims.get("username").toString();
        } catch (Exception e) {
            log.error("获取request中username异常", e);
        }
        return username;
    }

    public String getScopeFromToken(String token) {
        String scope = null;
        try {
            Claims claims = getClaimsFromToken(token);
            scope = (String) claims.get("scope", String.class);
        } catch (Exception e) {
            log.error("获取token中scope异常", e);
        }
        return scope;
    }

    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            if (claims == null)
                return Boolean.TRUE;
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            log.error("验证token有效期异常", e);
        }
        return Boolean.TRUE;
    }

    public String refreshToken(String token) {
        String refreshedToken = null;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put("iat", new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            log.error("刷新token异常", e);
        }
        return refreshedToken;
    }

    public boolean validateToken(String token, String subject) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token).getBody();
            return (subject.equals(claims.getSubject())) && (!isTokenExpired(token));
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature.", e);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token.", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token.", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT token compact of handler are invalid.", e);
        }
        return false;
    }

    public JwtUser validateToken(String token) {
        try {
            if (isTokenExpired(token)) {
                log.warn("Expired JWT token.");
                return null;
            }
            JwtUser jwtUser = (JwtUser) cacheService.hget(jwtProperties.getCacheKey(), token);
            if (jwtUser == null) {
                log.warn("Invalid JWT token.");
                return null;
            }
            String username = getUsernameFromToken(token);
            Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token);
            return (username.equals(jwtUser.getUsername())) && (!isTokenExpired(token)) ? jwtUser : null;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature.", e);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token.", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token.", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT token compact of handler are invalid.", e);
        }
        return null;
    }

    public boolean validateToken(String token, JwtUser jwtUser) {
        try {
            String username = getUsernameFromToken(token);
            Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token);
            return (username.equals(jwtUser.getUsername())) && (!isTokenExpired(token));
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature.", e);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token.", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token.", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT token compact of handler are invalid.", e);
        }
        return false;
    }

    public String getToken(HttpServletRequest request) {
        String requestHeader = request.getHeader(this.jwtProperties.getHeader());
        if ((requestHeader != null) && (requestHeader.startsWith(this.jwtProperties.getTokenStartWith()))) {
            return requestHeader.substring(7);
        }
        if (null != request.getCookies()) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(this.jwtProperties.getCookieKey())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
