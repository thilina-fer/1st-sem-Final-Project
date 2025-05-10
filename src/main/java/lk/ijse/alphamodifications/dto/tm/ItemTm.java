package lk.ijse.alphamodifications.dto.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class ItemTm {
    private String itemId;
    private String name;
    private int itemQuantity;
    private double buyingPrice;
    private double sellingPrice;
}
