package lk.ijse.alphamodifications.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class PurchaseReportDto {
    private String reportId;
    private String orderId;
    private String description;
}
