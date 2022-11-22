package pe.utp.venta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.utp.venta.config.advice.BusinessException;
import pe.utp.venta.dto.LoginDto;
import pe.utp.venta.dto.TokenDto;
import pe.utp.venta.generic.util.ErrorConstant;
import pe.utp.venta.config.jwt.TokenProvider;

import javax.validation.Valid;

@RestController
@RequestMapping("/external")
public class AuthController {

    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/authenticate")
    public TokenDto authorize(@Valid @RequestBody LoginDto loginDto) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.createToken(authentication);
            return TokenDto.builder()
                    .accessToken(jwt)
                    .build();
        } catch (AuthenticationException e) {
            throw new BusinessException(ErrorConstant.ERROR_LOGIN);
        }
    }
}
