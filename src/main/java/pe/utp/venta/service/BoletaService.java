package pe.utp.venta.service;


import org.springframework.core.io.ByteArrayResource;

import java.io.ByteArrayInputStream;

public interface BoletaService {
    ByteArrayInputStream generarBoleta(Long ventaId);
}
