package pe.utp.venta.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.utp.venta.service.BoletaService;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/boleta")
@RequiredArgsConstructor
public class BoletaController {

    private final BoletaService boletaService;

    @GetMapping("/generar/{ventaId}")
    public ByteArrayInputStream getBoleta(@PathVariable Long ventaId) {
        ByteArrayInputStream resource = boletaService.generarBoleta();

        return resource;
    }
}
