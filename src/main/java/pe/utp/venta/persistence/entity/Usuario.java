package pe.utp.venta.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.utp.venta.generic.util.Audit;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuario")
public class Usuario extends Audit<String> {

    @Id
    @Column(name = "usuario_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuarioId;
    private String username;
    @JsonIgnore
    private String password;
    private boolean estado;
    private String nombre;
    private String correo;

    @ManyToMany
    @JoinTable(
            name = "usuario_rol",
            joinColumns = {@JoinColumn(name = "usuario_id", referencedColumnName = "usuario_id")},
            inverseJoinColumns = {@JoinColumn(name = "rol_id", referencedColumnName = "rol_id")}
    )
    private Set<Authority> authorities;
}
