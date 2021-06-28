package com.jh.cavybackend.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenUtil implements InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);
    private final JwtProperties jwtProperties;
    public static final String JWT_SCOPE = "scope";
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
            claims = Jwts.parser().setSigningKey(this.key).parseClaimsJws(token).getBody();
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
            Claims claims = (Claims) Jwts.parser().setSigningKey(this.key).parseClaimsJws(token).getBody();
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

    public boolean validateToken(String token, JwtUser jwtUser) {
        try {
            String username = getUsernameFromToken(token);
            Jwts.parser().setSigningKey(this.key).parseClaimsJws(token);
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
