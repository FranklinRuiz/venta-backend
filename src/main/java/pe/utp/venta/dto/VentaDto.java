package pe.utp.venta.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VentaDto {
    private Long ventaId;
    private String codigo;
    private LocalDateTime fecha;
    private float total;
}
