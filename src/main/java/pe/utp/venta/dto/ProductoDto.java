package pe.utp.venta.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDto {
    private Long productoId;
    private String codigo;
    private String descripcion;
    private float precio;
    private LocalDate vencimiento;
    private String laboratorio;
    private String tipo;
    private String presentacion;
    private int stock;
}
