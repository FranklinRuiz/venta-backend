package pe.utp.venta.config.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class CustomHttpResponseException extends RuntimeException {

    private final HttpEntity payload;
    private HttpStatus status;

}
