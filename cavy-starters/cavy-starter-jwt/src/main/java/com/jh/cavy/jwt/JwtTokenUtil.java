package com.jh.cavy.jwt;


import com.jh.cavy.cache.service.CacheService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil implements InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);
    private final JwtProperties jwtProperties;
    private Key key;
    @Resource
    private  CacheService cacheService;

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
        Date expirationDate = new Date(System.currentTimeMillis() + this.jwtProperties.getTokenValidityInSeconds().longValue());
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
            log.error("token过期 请重新登录");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    public String getAccountFromToken(String token) {
        String account = null;
        try {
            Claims claims = getClaimsFromToken(token);
            account = claims.get("account").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    public String getAccountFromRequest(HttpServletRequest request) {
        String account = null;
        try {
            Claims claims = getClaimsFromToken(getToken(request));
            account = claims.get("account").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    public String getUsernameFromToken(String token) {
        String username = null;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.get("username").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }

    public String getUsernameFromRequest(HttpServletRequest request) {
        String username = null;
        try {
            Claims claims = getClaimsFromToken(getToken(request));
            username = claims.get("username").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }

    public String getScopeFromToken(String token) {
        String scope = null;
        try {
            Claims claims = getClaimsFromToken(token);
            scope = (String) claims.get("scope", String.class);
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return refreshedToken;
    }

    public boolean validateToken(String token, String subject) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token).getBody();
            return (subject.equals(claims.getSubject())) && (!isTokenExpired(token));
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature.");
            e.printStackTrace();
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            e.printStackTrace();
        }
        return false;
    }

    public JwtUser validateToken(String token) {
        try {
            if (isTokenExpired(token)) {
                log.info("Expired JWT token.");
                return null;
            }
            JwtUser jwtUser = (JwtUser) cacheService.hget(jwtProperties.getRedisKey(), token);
            if (jwtUser == null) {
                log.info("Invalid JWT token.");
                return null;
            }
            String username = getUsernameFromToken(token);
            Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token);
            return (username.equals(jwtUser.getUsername())) && (!isTokenExpired(token)) ? jwtUser : null;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature.");
            e.printStackTrace();
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            e.printStackTrace();
        }
        return null;
    }

    public boolean validateToken(String token, JwtUser jwtUser) {
        try {
            String username = getUsernameFromToken(token);
            Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token);
            return (username.equals(jwtUser.getUsername())) && (!isTokenExpired(token));
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature.");
            e.printStackTrace();
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            e.printStackTrace();
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

    public JwtProperties getJwtProperties() {
        return this.jwtProperties;
    }
}
