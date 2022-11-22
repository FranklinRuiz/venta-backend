package pe.utp.venta.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.utp.venta.config.advice.BusinessException;
import pe.utp.venta.dto.*;
import pe.utp.venta.generic.constans.VentaConstant;
import pe.utp.venta.generic.util.ErrorConstant;
import pe.utp.venta.mapper.ProductoMapper;
import pe.utp.venta.mapper.VentaMapper;
import pe.utp.venta.persistence.entity.Producto;
import pe.utp.venta.persistence.entity.Venta;
import pe.utp.venta.persistence.entity.VentaDetalle;
import pe.utp.venta.persistence.repository.ProductoRepository;
import pe.utp.venta.persistence.repository.VentaDetalleRepository;
import pe.utp.venta.persistence.repository.VentaRepository;
import pe.utp.venta.service.ProductoService;
import pe.utp.venta.service.VentaService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;
    private final VentaDetalleRepository ventaDetalleRepository;

    @Override
    public boolean registrarVenta(VentaRequestDto ventaRequestDto) {
        float subTotal = 0.00f;
        boolean rpta = false;
        try {
            for (VentaDetalleRequestDto vd : ventaRequestDto.getVentaDetalle()) {
                subTotal = subTotal + vd.getPrecio();
            }

            Venta venta = Venta.builder()
                    .clienteId(ventaRequestDto.getClienteId())
                    .subTotal(subTotal)
                    .igv(VentaConstant.IGV)
                    .total(subTotal * VentaConstant.IGV)
                    .build();

            venta = ventaRepository.save(venta);

            List<VentaDetalle> ventaDetalles = new ArrayList<>();

            for (VentaDetalleRequestDto vd : ventaRequestDto.getVentaDetalle()) {
                VentaDetalle ventaDetalle = VentaDetalle.builder()
                        .ventaId(venta.getVentaId())
                        .productoId(vd.getProductoId())
                        .precio(vd.getPrecio())
                        .cantidad(vd.getCantidad())
                        .total(vd.getPrecio() * vd.getCantidad())
                        .build();

                ventaDetalles.add(ventaDetalle);
            }

            rpta = (ventaDetalleRepository.saveAll(ventaDetalles) != null) ? true : false;
        } catch (Exception e) {
            throw new BusinessException(ErrorConstant.ERROR_VENTA_REGISTRO);
        }
        return rpta;
    }

    @Override
    public Page<VentaDto> listaVentaPage(Pageable pageable) {
        Page<Venta> entities = ventaRepository.findAll(pageable);
        return entities.map(VentaMapper.INSTANCE::map);
    }
}
