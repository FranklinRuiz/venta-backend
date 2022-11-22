package pe.utp.venta.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pe.utp.venta.dto.ProductoDto;
import pe.utp.venta.dto.StockDto;
import pe.utp.venta.persistence.entity.Producto;

@Mapper
public interface ProductoMapper {

    ProductoMapper INSTANCE = Mappers.getMapper(ProductoMapper.class);

    default StockDto mapStock(Producto producto) {
        return StockDto
                .builder()
                .codigo(producto.getCodigo())
                .descripcion(producto.getDescripcion())
                .stock(producto.getStock())
                .stockMinimo(producto.getStockMinimo())
                .alerta((producto.getStock() <= producto.getStockMinimo()) ? "Producto con stock mínimo, verifique la compra antes de agotar el stock" : "")
                .build();
    }

    default ProductoDto mapProducto(Producto producto) {
        return ProductoDto
                .builder()
                .productoId(producto.getProductoId())
                .codigo(producto.getCodigo())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .vencimiento(producto.getVencimiento())
                .presentacion(producto.getCodigo())
                .stock(producto.getStock())
                .build();
    }

}
