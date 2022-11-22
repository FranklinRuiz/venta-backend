package pe.utp.venta.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.utp.venta.config.advice.BusinessException;
import pe.utp.venta.dto.ClienteDto;
import pe.utp.venta.generic.util.ErrorConstant;
import pe.utp.venta.mapper.ClienteMapper;
import pe.utp.venta.persistence.entity.Cliente;
import pe.utp.venta.persistence.repository.ClienteRepository;
import pe.utp.venta.service.ClienteService;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    public ClienteDto buscarCliente(String dni) {
        Cliente cliente = clienteRepository.findByDni(dni).orElseThrow(() -> new BusinessException(ErrorConstant.ERROR_CLIENTE_BUSQUEDA));
        return ClienteMapper.INSTANCE.map(cliente);
    }

}
