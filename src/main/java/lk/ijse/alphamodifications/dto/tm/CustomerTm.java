package lk.ijse.alphamodifications.dto.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class CustomerTm {
    private String customerId;
    private String name;
    private String contact;
    private String address;

}
