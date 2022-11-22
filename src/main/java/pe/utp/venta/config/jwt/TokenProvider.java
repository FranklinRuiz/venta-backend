package pe.utp.venta.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import pe.utp.venta.persistence.entity.Usuario;
import pe.utp.venta.persistence.repository.IUsuarioRepository;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";

    private Key key;

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.token-validity-in-seconds}")
    private long tokenValidityInSeconds;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication) {
        Optional<Usuario> usuario = usuarioRepository.findOneWithAuthoritiesByUsername(authentication.getName());
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Map<String, Object> extra = new HashMap<>();
        extra.put(AUTHORITIES_KEY, authorities);

        if (usuario.isPresent()) {
            extra.put("id", usuario.get().getUsuarioId());
            extra.put("name", usuario.get().getNombre());
            extra.put("email", usuario.get().getCorreo());
        }

        long now = (new Date()).getTime();
        Date validity = new Date(now + (tokenValidityInSeconds * 1000));

        return Jwts.builder()
                .setSubject(authentication.getName())
                .addClaims(extra)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("Firma JWT no válida.");
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            logger.info("Token JWT caducado.");
        } catch (UnsupportedJwtException e) {
            logger.info("Token JWT no admitido.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT el token no es válido.");
        }
        return false;
    }
}
