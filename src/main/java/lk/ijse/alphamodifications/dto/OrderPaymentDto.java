package lk.ijse.alphamodifications.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class OrderPaymentDto {
    private String opId;
    private String supId;
    private String soId;
    private Double amount;
    private String payType;
}
