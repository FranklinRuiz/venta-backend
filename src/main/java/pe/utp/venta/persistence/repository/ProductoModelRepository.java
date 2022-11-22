package pe.utp.venta.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.utp.venta.persistence.entity.Producto;
import pe.utp.venta.persistence.model.ProductoModel;

import java.util.Optional;

@Repository
public interface ProductoModelRepository extends JpaRepository<ProductoModel, Long> {
    @Query(value = "select " +
            "p.producto_id " +
            ",p.codigo " +
            ",p.descripcion " +
            ",p.precio " +
            ",p.vencimiento " +
            ",l.nombre as laboratorio " +
            ",t.tipo as tipo " +
            ",p.presentacion " +
            ",p.stock " +
            ",p.stock_minimo " +
            "from producto p " +
            "inner join laboratorio l on p.laboratorio_id = l.laboratorio_id " +
            "inner join tipo t on p.tipo_id = t.tipo_id " +
            "where p.codigo = :codigo ", nativeQuery = true)
    Optional<ProductoModel> findByCodigo(@Param("codigo") String codigo);

    @Query("select " +
            "p.productoId " +
            ",p.codigo " +
            ",p.descripcion " +
            ",p.precio " +
            ",p.vencimiento " +
            ",l.nombre as laboratorio " +
            ",t.tipo" +
            ",p.presentacion " +
            ",p.stock " +
            ",p.stockMinimo " +
            "from Producto p " +
            "inner join Laboratorio l on p.laboratorioId = l.laboratorioId " +
            "inner join Tipo t on p.tipoId = t.tipoId ")
    Page<ProductoModel> listaProducto(Pageable pageable);
}
