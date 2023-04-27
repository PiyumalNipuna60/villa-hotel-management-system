package tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class BillTM {
    private String cusId;
    private String Name;
    private String contact;
    private String safaryType;
    private  String roomId;
    private String roomType;
    private String roomPrice;
    private String payment;
    private String bill;
    private String cash;
    private String balance;
}
