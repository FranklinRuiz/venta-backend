package pe.utp.venta.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.utp.venta.generic.util.Audit;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoModel {
    @Id
    private Long productoId;
    private String codigo;
    private String descripcion;
    private float precio;
    private LocalDate vencimiento;
    private String laboratorio;
    private String tipo;
    private int presentacion;
    private int stock;
    private int stockMinimo;
}
