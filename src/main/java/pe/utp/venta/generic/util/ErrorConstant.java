package pe.utp.venta.generic.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ErrorConstant {
    public static final String ERROR_LOGIN = "error.login";
    public static final String ERROR_CLIENTE_BUSQUEDA = "error.cliente.busqueda";
    public static final String ERROR_PRODUCTO_BUSQUEDA = "error.producto.busqueda";
    public static final String ERROR_VENTA_REGISTRO = "error.venta.registro";
}
