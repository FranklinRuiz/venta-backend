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
@Table(name = "laboratorio")
public class Laboratorio extends Audit<String> {
    @Id
    @Column(name = "laboratorio_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long laboratorioId;
    private String nombre;
    private String direccion;
}
