package pe.utp.venta.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VentaRequestDto {
    private Long clienteId;
    private float subTotal;
    private int igv;
    private float total;
    private List<VentaDetalleRequestDto> ventaDetalle;
}
