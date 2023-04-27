package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoomDetailsDTO {

    private String roomId;
    private String cusId;
    private String paymentType;
    private String payment;

}
