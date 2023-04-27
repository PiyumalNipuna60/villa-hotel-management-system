package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SafaryDTO {
    private  String safaryId;
    private  String type;
    private  String date;
    private  String time;
    private  String driverId;

}
