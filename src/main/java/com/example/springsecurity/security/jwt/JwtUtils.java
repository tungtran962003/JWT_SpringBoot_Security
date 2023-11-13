package com.example.springsecurity.security.jwt;

import com.example.springsecurity.security.service.UserDetailsImpl;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

//    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${bezkoder.app.jwtSecret}")
    private String jwtSecrect; // Key secrect

    @Value("${bezkoder.app.jwtExpirationMs}")
    private int jwtExpirationMs; // Thời gian sống trên hệ thống - tính bằng mili giây

    // Hàm gen ra mã token
    public String genarateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal(); // Lấy user hiện tại trên hệ thống
        // Build dữ liệu từ thông tin đăng nhập xuống ra một mã token
//        return Jwts.builder().setSubject((userPrincipal.getUsername())) // Set username
//                .setIssuedAt(new Date()) // Set tại thời điểm nào
//                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)) // Set thời gian sống của token
//                .signWith(getKey(), SignatureAlgorithm.HS256) // Set key (Key, Tiêu chuẩn mã hóa)
//                .compact();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        // Tạo 1 khóa HMAC-SHA từ 1 đoạn mã token đã được giải mã dưới định dạng BASE64
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecrect));
    }

    public boolean validateToken (String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getKey()).build().parse(token);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage()); // Không đúng định dạng
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage()); // Token bị hết thời gian
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage()); // Không hỗ trợ token
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage()); // Có ký tự trống
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJwt(token).getBody().getSubject();
    }
}
