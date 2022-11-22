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
@Table(name = "venta")
public class Venta extends Audit<String> {
    @Id
    @Column(name = "venta_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ventaId;
    private Long clienteId;
    private float subTotal;
    private float igv;
    private float total;
}
