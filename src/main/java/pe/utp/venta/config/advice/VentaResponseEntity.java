package pe.utp.venta.config.advice;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class VentaResponseEntity<T> {
    private String traceId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String shortMessage;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
}

