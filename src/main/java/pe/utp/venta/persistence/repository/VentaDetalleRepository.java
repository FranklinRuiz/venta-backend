package pe.utp.venta.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.utp.venta.persistence.entity.VentaDetalle;

import java.util.List;

@Repository
public interface VentaDetalleRepository extends JpaRepository<VentaDetalle, Long> {
    List<VentaDetalle> findByVentaId(Long ventaId);
}
