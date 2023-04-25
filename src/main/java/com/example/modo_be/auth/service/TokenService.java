package com.example.modo_be.auth.service;

import com.example.modo_be.auth.dto.TokenUserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;


@Service
@PropertySource("classpath:jwtSecretKey.properties")
public class TokenService {

//    @Value("${secretKey}")
    static String secretKey = "MODOMODOMODOMODOMODOMODOMODOMODOMODOMODOMODOMODO";

    private static final int EXPIRATED_MINUTE = (60*1000)*3000; // 30분
//    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private Key key;

    // token create

    @PostConstruct
    protected void init() {
        // key를 base64로 인코딩
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        key= Keys.hmacShaKeyFor(encodedKey.getBytes());
    }
    public String createToken(TokenUserInfo tokenUserInfo){
        Date now = new Date();
        Date expiryDate =  new Date(now.getTime()+EXPIRATED_MINUTE);
        // setExpiration 매개변수가 Date로 되어있어 LocalDateTime를 사용하지 못함
        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setSubject(tokenUserInfo.getUserEmail()) // 이름
                .setClaims(createClaims(tokenUserInfo))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
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
        accessToken = accessToken.substring("Bearer ".length()).trim();
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody().get("userEmail").toString();
    }
//    ExpiredJwtException : JWT를 생성할 때 지정한 유효기간 초과할 때.
//    UnsupportedJwtException : 예상하는 형식과 일치하지 않는 특정 형식이나 구성의 JWT일 때
//    MalformedJwtException : JWT가 올바르게 구성되지 않았을 때
//    SignatureException :  JWT의 기존 서명을 확인하지 못했을 때

    private Map<String, Object> createClaims(TokenUserInfo tokenUserInfo) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userEmail", tokenUserInfo.getUserEmail()); // userEmail
        claims.put("userNickName", tokenUserInfo.getUserNickName());// 인가정보
        return claims;

    }

}
