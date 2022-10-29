package board.Security.token;

import board.Security.token.Model.Token;
import board.Security.token.Service.TokenService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final String SECRET_KEY = "sercret";
    private static final int SIXTYMINUTE = 1000 * 60 * 60;
    private static final int ONEDAY = 1000 * 60 * 60 * 24;


    @Autowired
    TokenService tokenService;

    public String createAccessToken(String userEmail) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + SIXTYMINUTE);

        return Jwts.builder()
                .claim("email", userEmail)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractUserEmail(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException ex) {
            return ex.getClaims().get("email", String.class);
        }
        return claims.get("email", String.class);
    }

    public boolean isValid(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean checkSameToken(String token, String userEmail) {
        Token saveToken = tokenService.getTokenByUserEmail(userEmail);
        saveToken.validateSameToken(token);
        return true;
    }

}