package pe.utp.venta.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto {
    private Long clienteId;
    private String dni;
    private String nombre;
    private String telefono;
    private String direccion;
}
