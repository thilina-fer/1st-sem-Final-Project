package lk.ijse.alphamodifications.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class PurchasePaymentDto {
    private String paymentId;
    private String orderId;
    private String paymentType;
    private String payDayTime;
}
