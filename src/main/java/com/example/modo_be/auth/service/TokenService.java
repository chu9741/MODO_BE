package com.example.modo_be.auth.service;

import com.example.modo_be.auth.dto.TokenUserInfo;
import com.example.modo_be.user.domain.User;
import com.example.modo_be.user.dto.PrimeUserInfo;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@PropertySource("classpath:jwtSecretKey.properties")
public class TokenService {

    @Value("${secretKey}")
    String secretKey;

    private static final int EXPIRATED_MINUTE = 30; // 30분
    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // token create

    public String createToken(TokenUserInfo tokenUserInfo){
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, EXPIRATED_MINUTE);
        Date expiryDate =  calendar.getTime();
        // setExpiration 매개변수가 Date로 되어있어 LocalDateTime를 사용하지 못함

        return Jwts.builder()
                .setSubject(tokenUserInfo.getUserNickName()) // 이름
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .setClaims(createClaims(tokenUserInfo))
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String accessToken) throws JwtException{
        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken);
        return true;
    }

    public String getJwtSubject(String accessToken){
        //반드시 validation 이후 진행
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
                .getSubject();
    }
//    ExpiredJwtException : JWT를 생성할 때 지정한 유효기간 초과할 때.
//    UnsupportedJwtException : 예상하는 형식과 일치하지 않는 특정 형식이나 구성의 JWT일 때
//    MalformedJwtException : JWT가 올바르게 구성되지 않았을 때
//    SignatureException :  JWT의 기존 서명을 확인하지 못했을 때



    private Map<String, Object> createClaims(TokenUserInfo tokenUserInfo) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", tokenUserInfo.getUserId()); // userId
        claims.put("userNickName", tokenUserInfo.getUserNickName());// 인가정보
        return claims;

    }

}
