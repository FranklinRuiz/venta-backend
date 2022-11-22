package pe.utp.venta.config.advice;


import lombok.Getter;

@Getter
public class VentaException extends RuntimeException {
    private String code;
    protected String shortMessage;
    protected Integer httpCode;

    public VentaException(String code, String msg, Throwable e) {
        super(String.format("[%s] %s", code, msg), e);
        this.code = code;
    }

    public VentaException(String code, String msg) {
        super(String.format("[%s] %s", code, msg));
        this.code = code;
    }

    public VentaException(String code, String msg, String shortMessage) {
        super(String.format("[%s] %s", code, msg));
        this.code = code;
        this.shortMessage = shortMessage;
    }

    public VentaException(String code) {
        super(String.format("[%s]", code));
        this.code = code;
    }

    public VentaException(String code, Integer httpCode) {
        super(String.format("[%s]", code));
        this.code = code;
        this.httpCode = httpCode;
    }

    public VentaException(String msg, Throwable e) {
        this("DEFAULT", msg, e);
    }
}

