package pe.utp.venta.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.utp.venta.dto.ClienteDto;
import pe.utp.venta.dto.ProductoDto;
import pe.utp.venta.dto.StockDto;
import pe.utp.venta.service.ClienteService;
import pe.utp.venta.service.ProductoService;

@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping("/busqueda/{dni}")
    @ApiOperation("Buscar cliente por dni")
    public ClienteDto busqueda(@PathVariable String dni) {
        return clienteService.buscarCliente(dni);
    }

}
