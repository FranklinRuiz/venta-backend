package pe.utp.venta.generic.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Audit<U> {

    @CreatedBy
    @JsonIgnore
    @Column(updatable = false)
    private U usuarioCreacion;

    @CreatedDate
    @JsonIgnore
    @Column(updatable = false)
    private LocalDateTime fechaCreacion;


    @LastModifiedBy
    @JsonIgnore
    private U usuarioActualizacion;

    @LastModifiedDate
    @JsonIgnore
    private LocalDateTime fechaActualizacion;

}
