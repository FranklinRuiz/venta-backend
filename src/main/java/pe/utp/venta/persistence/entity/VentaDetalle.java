package pe.utp.venta.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.utp.venta.generic.util.Audit;

import javax.persistence.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "venta_detalle")
public class VentaDetalle extends Audit<String> {
    @Id
    @Column(name = "venta_detalle_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ventaDetalleId;
    private Long ventaId;
    private Long productoId;
    private float precio;
    private int cantidad;
    private float total;
}
