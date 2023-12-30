package com.study.toDoList.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static org.yaml.snakeyaml.tokens.Token.ID.Key;

@Component
@Slf4j
public class TokenProvider {

    private final UserDetailsService userDetailsService;
    private Key secretKey;
    private final long tokenValidMillisecond = 1000L * 60 * 60;
   @Autowired
    public TokenProvider(UserDetailsService userDetailsService){
        this.userDetailsService =userDetailsService;
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }


    public String createToken(String id, List<String> roles){
        log.info("토큰 생성 시작");
        Claims claims = Jwts.claims().setSubject(id);
        claims.put("roles",roles);
        Date now = new Date();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+tokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
        log.info("토큰 생성 완료");
        return token;
    }

    public Authentication getAuthentication(String token){
        log.info("토큰 인증 정보 조회 시작");
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(token));
        log.info("토큰 인증 정보 조회 완료 user:{}",userDetails.getUsername());
        log.info("토큰 인증 정보 조회 완료 user:{}",userDetails.getAuthorities());
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    public String getUsername(String token){
        log.info("토큰으로 회원 정보 추출");
        String info = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
        log.info("토큰으로 회원 정보 추출 완료 info:{}",info);
        return info;
    }
    public String resolveToken(HttpServletRequest request){
        log.info("헤더에서 토큰 값 추출");
        return request.getHeader("Authentication");
    }

    public boolean validateToken(String token){
        log.info("토큰 유효성 검증 시작");
        try{
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        }catch (Exception e){
            log.info("토큰 유효 체크 예외 발생");
            return false;
        }
    }




}
