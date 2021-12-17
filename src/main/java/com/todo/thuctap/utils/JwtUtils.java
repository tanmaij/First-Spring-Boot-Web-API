package com.todo.thuctap.utils;

import com.todo.thuctap.exception.AuthorizationException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private String setcretKey;
    private String setcretKeyRefresh;
    private int expiration;

    @Value("${bezkoder.app.jwtSecret}")
    public void setSetcretKey(String setcretkey) {
        setcretKey = setcretkey;
    }

    @Value("${bezkoder.app.jwtExpirationMs}")
    public void setjwtExpirationMs(int jwtexpirationMs) {
        expiration = (jwtexpirationMs);
    }

    @Value("${bezkoder.app.jwtSecretRefresh}")
    public void setSetcretKeyRefresh(String setcretKeyRefresh) {
        this.setcretKeyRefresh = setcretKeyRefresh;
    }

    public String generateAccessToken(String username) {

        return Jwts.builder().
                setSubject(username).
                setIssuedAt(new Date()).
                setExpiration(new Date((new Date()).getTime() + expiration)).
                signWith(SignatureAlgorithm.HS256, setcretKey).
                compact();
    }

    public String generateRefreshToken(String username) {
        return Jwts.builder().
                setSubject(username).
                setIssuedAt(new Date()).
                setExpiration(new Date((new Date()).getTime() + expiration)).
                signWith(SignatureAlgorithm.HS256, setcretKeyRefresh).
                compact();
    }

    public String getUsernameFromAccessToken(String accessToken) {
        try {
            return Jwts.parser().setSigningKey(setcretKey).parseClaimsJws(accessToken).getBody().getSubject();
        } catch (Exception e) {
            throw new AuthorizationException("Ban chưa đăng nhập");
        }
    }


}
