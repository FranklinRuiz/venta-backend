package pe.utp.venta.config.advice;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
public abstract class VentaResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    protected final VentaMessageSource icaaMessageSource;
    private static final String ERROR_INESPERADO = "ERROR INESPERADO";

    public VentaResponseEntityExceptionHandler(VentaMessageSource icaaMessageSource) {
        this.icaaMessageSource = icaaMessageSource;
    }

    @ExceptionHandler(value = {VentaException.class})
    protected ResponseEntity<Object> handlerIcaaCore(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handlerException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorCode = "default";
        String shortMessage = null;
        HttpStatus finalStatus = status;
        if (ex instanceof VentaException) {
            VentaException icaaException = (VentaException) ex;
            errorCode = icaaException.getCode();
            shortMessage = icaaException.getShortMessage();
            if (icaaException.getHttpCode() != null) {
                finalStatus = HttpStatus.resolve(icaaException.getHttpCode());
            }
        }
        if (ex instanceof CustomHttpResponseException) {
            CustomHttpResponseException mainException = (CustomHttpResponseException) ex;
            HttpStatus httpStatus = mainException.getStatus() == null ? status : mainException.getStatus();
            return new ResponseEntity<>(mainException.getPayload(), headers, httpStatus);
        }
        VentaResponseEntity<Object> icaaResponseEntity = new VentaResponseEntity();
        VentaMessageSource.Message message = icaaMessageSource.getMessage(errorCode);
        icaaResponseEntity.setMessage(message.getMessage());
        if (shortMessage == null || !shortMessage.equals(ERROR_INESPERADO)) {
            icaaResponseEntity.setShortMessage(message.getShortMessage());
        } else {
            icaaResponseEntity.setShortMessage(shortMessage);
        }
        icaaResponseEntity.setErrorCode(errorCode);
        icaaResponseEntity.setTraceId(getTraceId());
        log.error("code: [{}] message: [{}]", icaaResponseEntity.getErrorCode(), icaaResponseEntity.getMessage(), ex);
        return new ResponseEntity<>(icaaResponseEntity, headers, finalStatus);
    }

    protected abstract String getTraceId();
}

