package com.aboutme.springwebservice.auth.naver.security.service;

import com.aboutme.springwebservice.auth.naver.exception.InvalidAuthException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtTokenProvider {
    private static final String sercretKey = "abc123";
    private static final long validtyInMilliseconds = 3600000 * 24 * 3;

    public String createToken(long userNo) {
        Claims claims = Jwts.claims();
        claims.put("userNo", userNo);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validtyInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, sercretKey)
                .compact();
    }

    public String resolveToekn(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(sercretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalAccessError e) {
            throw new InvalidAuthException("Expired or Invalid JWT token");
        }
    }

    public Authentication getAuthentication(String token) {
        Long userNo = this.getAuthUserNo(token);

        Set<SimpleGrantedAuthority> set = new HashSet<>(1);
        set.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(userNo, "", set);
    }

    public long getAuthUserNo(String token) {
        Claims body = Jwts.parser().setSigningKey(sercretKey).parseClaimsJws(token).getBody();
        return body.get("userNo", Long.class);
    }
}
