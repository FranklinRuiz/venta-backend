package pe.utp.venta.service;

import pe.utp.venta.dto.ClienteDto;

public interface ClienteService {
    ClienteDto buscarCliente(String dni);
}
