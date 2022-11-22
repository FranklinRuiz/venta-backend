package pe.utp.venta.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.utp.venta.dto.ProductoDto;
import pe.utp.venta.dto.StockDto;

import java.util.List;

public interface ProductoService {
    StockDto verificarStock(String codigo);

    List<ProductoDto> buscaProducto(String nombre);
}
