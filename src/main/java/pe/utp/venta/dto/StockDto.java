package pe.utp.venta.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockDto {
    private String codigo;
    private String descripcion;
    private String laboratorio;
    private int stock;
    private int stockMinimo;
    private String alerta;
}
