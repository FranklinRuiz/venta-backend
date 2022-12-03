package pe.utp.venta.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
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

    @GetMapping(value = "/generar/{ventaId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generarBoleta(@PathVariable Long ventaId) {
        ByteArrayInputStream resource = boletaService.generarBoleta(ventaId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=boleta.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(resource));
    }
}
