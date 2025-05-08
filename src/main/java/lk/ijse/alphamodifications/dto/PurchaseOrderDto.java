package lk.ijse.alphamodifications.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class PurchaseOrderDto {
    private String orderId;
    private String custId;
    private String orderDate;
    private double totAmount;
}
