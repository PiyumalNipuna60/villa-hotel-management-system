package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingRoomDTO {
    private String cusId;
    private String cusName;
    private String contact;
    private String roomId;
    private String roomType;
    private String roomPrice;
    private String paymentType;
    private String payment;
    private String cash;


}

