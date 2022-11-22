package pe.utp.venta.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pe.utp.venta.dto.ProductoDto;
import pe.utp.venta.dto.StockDto;
import pe.utp.venta.dto.VentaDto;
import pe.utp.venta.dto.VentaRequestDto;
import pe.utp.venta.service.ProductoService;
import pe.utp.venta.service.VentaService;

import java.util.List;

@RestController
@RequestMapping("/api/venta")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @PostMapping("/registrar")
    @ApiOperation("Registrar venta")
    public boolean registrarVenta(@RequestBody VentaRequestDto ventaRequestDto) {
        return ventaService.registrarVenta(ventaRequestDto);
    }

    @GetMapping("/lista")
    public Page<VentaDto> getVenta(Pageable pageable) {
        return ventaService.listaVentaPage(pageable);
    }
}
