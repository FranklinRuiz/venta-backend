package pe.utp.venta.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pe.utp.venta.dto.ProductoDto;
import pe.utp.venta.dto.StockDto;
import pe.utp.venta.dto.VentaDto;
import pe.utp.venta.persistence.entity.Producto;
import pe.utp.venta.persistence.entity.Venta;

import java.time.LocalDateTime;

@Mapper
public interface VentaMapper {

    VentaMapper INSTANCE = Mappers.getMapper(VentaMapper.class);

    default VentaDto map(Venta venta) {
        return VentaDto
                .builder()
                .ventaId(venta.getVentaId())
                .codigo(String.format("%08d", venta.getVentaId()))
                .fecha(venta.getFechaCreacion())
                .total(venta.getTotal())
                .build();
    }

}
