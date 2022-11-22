package pe.utp.venta.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.utp.venta.config.advice.BusinessException;
import pe.utp.venta.dto.ProductoDto;
import pe.utp.venta.dto.StockDto;
import pe.utp.venta.generic.util.ErrorConstant;
import pe.utp.venta.mapper.ProductoMapper;
import pe.utp.venta.persistence.entity.Producto;
import pe.utp.venta.persistence.repository.ProductoRepository;
import pe.utp.venta.service.ProductoService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    public StockDto verificarStock(String codigo) {
        Producto producto = productoRepository.findByCodigo(codigo).orElseThrow(() -> new BusinessException(ErrorConstant.ERROR_PRODUCTO_BUSQUEDA));
        return ProductoMapper.INSTANCE.mapStock(producto);
    }

    @Override
    public List<ProductoDto> buscaProducto(String nombre) {
        List<Producto> producto = productoRepository.findByDescripcionContaining(nombre);
        return producto.stream().map(ProductoMapper.INSTANCE::mapProducto).collect(Collectors.toList());
    }

}
