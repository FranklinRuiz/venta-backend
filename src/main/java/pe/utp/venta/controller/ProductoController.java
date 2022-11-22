package pe.utp.venta.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pe.utp.venta.dto.ProductoDto;
import pe.utp.venta.dto.StockDto;
import pe.utp.venta.service.ProductoService;

import java.util.List;

@RestController
@RequestMapping("/api/producto")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping("/verificar-stock/{codigo}")
    @ApiOperation("Verificar stock de producto por codigo")
    public StockDto verificarStock(@PathVariable String codigo) {
        return productoService.verificarStock(codigo);
    }

    @GetMapping("/lista-producto")
    public List<ProductoDto> getProducto(@RequestParam String producto) {
        return productoService.buscaProducto(producto);
    }
}
