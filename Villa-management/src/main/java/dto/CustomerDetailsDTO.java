package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerDetailsDTO {

    private String empId;
    private String cusId;
    private String details;
    private String date;
    private String time;


}
