package lk.ijse.alphamodifications.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class PreOrderDto {
    private String preOrderId;
    private String userId;
    private String itemId;
    private double advance;
}
