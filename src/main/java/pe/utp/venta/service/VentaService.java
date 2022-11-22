package pe.utp.venta.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.utp.venta.dto.VentaDto;
import pe.utp.venta.dto.VentaRequestDto;

public interface VentaService {
    boolean registrarVenta(VentaRequestDto ventaRequestDto);

    Page<VentaDto> listaVentaPage(Pageable pageable);
}
