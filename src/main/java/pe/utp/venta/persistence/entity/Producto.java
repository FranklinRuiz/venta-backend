package pe.utp.venta.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.utp.venta.generic.util.Audit;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "producto")
public class Producto extends Audit<String> {
    @Id
    @Column(name = "producto_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productoId;
    private String codigo;
    private String descripcion;
    private float precio;
    private LocalDate vencimiento;
    private Long laboratorioId;
    private Long tipoId;
    private int presentacion;
    private int stock;
    private int stockMinimo;
}
