package pe.utp.venta.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pe.utp.venta.dto.ClienteDto;
import pe.utp.venta.dto.ProductoDto;
import pe.utp.venta.dto.StockDto;
import pe.utp.venta.persistence.entity.Cliente;
import pe.utp.venta.persistence.model.ProductoModel;

@Mapper
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    default ClienteDto map(Cliente cliente) {
        return ClienteDto
                .builder()
                .clienteId(cliente.getClienteId())
                .dni(cliente.getDni())
                .nombre(cliente.getNombre())
                .telefono(cliente.getTelefono())
                .direccion(cliente.getDireccion())
                .build();
    }

}
