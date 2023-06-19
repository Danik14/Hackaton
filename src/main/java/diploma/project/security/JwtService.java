package diploma.project.security;

import java.security.Key;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET_KEY = "GACHI_B INDERAGACHI_BINDERAGACHI_BINDERAGACHI_BINDERAGACHI_BINDERAGACHI_BINDERAGACHI_BINDERAGACHI_BINDERAGACHI_BINDERAGACHI_BINDERAGACHI_BINDERAGACHI_BINDERAGACHI_BINDERAGACHI_BINDERAGACHI_BINDERAGACHI_BINDERAGACHI_BINDERAGACHI_BINDERAGACHI_BINDERAGACHI_BINDERAGACHI_BINDERA";

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Function with generic return type
    // Basically it accepts token and a function for getting
    // a needed field of a token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);

        // using apply method of a function type to use a function on claims
        return claimsResolver.apply(claims);
    }

    // public String generateToken(Map<String, Object> claims, UserDetails
    // userDetails) {

    // }

    // Gettin all claims (parts) of jwt
    // first it sets the sign key to verify the token
    // then it users creted parser to extract the jwt claims
    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // making signature using secret key
    private Key getSigInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
