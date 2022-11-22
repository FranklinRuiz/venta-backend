package pe.utp.venta.config.advice;

import brave.Tracer;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class CustomResponseEntityExceptionHandler extends VentaResponseEntityExceptionHandler {

    private final Tracer tracer;

    public CustomResponseEntityExceptionHandler(MessageSource messageSource, Tracer tracer) {
        super((VentaMessageSource)messageSource);
        this.tracer = tracer;
    }

    @Override
    protected String getTraceId() {
        return tracer.currentSpan().context().traceIdString();
    }
}