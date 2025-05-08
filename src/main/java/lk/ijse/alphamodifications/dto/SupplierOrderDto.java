package lk.ijse.alphamodifications.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class SupplierOrderDto {
    private String soId;
    private String supId;
    private String userId;
    private String date;
    private String itemId;
}
