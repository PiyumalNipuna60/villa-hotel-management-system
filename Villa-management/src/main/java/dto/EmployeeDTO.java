package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeDTO {

    private String empId;
    private String name;
    private String address;
    private String age;
    private String nic;
    private String contact;
    private String salary;
    private String userName;
    private String password;

}
