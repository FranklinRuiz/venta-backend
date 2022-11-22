package pe.utp.venta.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VentaDetalleRequestDto {
    private Long productoId;
    private float precio;
    private int cantidad;
    private float total;
}
