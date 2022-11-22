package pe.utp.venta.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModelItem {
    private String itemName;
    private String itemDesc;
    private int quantity;
    private int disAmount;
    private int vat;
    private int netAmount;
}
