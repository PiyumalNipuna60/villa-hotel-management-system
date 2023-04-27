package tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class EmployeeTm {

    private String empId;
    private String name;
    private String address;
    private String dob;
    private String nic;
    private String contact;
    private String salary;

}
