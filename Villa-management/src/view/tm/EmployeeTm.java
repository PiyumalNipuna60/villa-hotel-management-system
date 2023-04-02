package view.tm;

public class EmployeeTm {

    private String empId;
    private String name;
    private String address;
    private String dob;
    private String nic;
    private String contact;
    private String salary;

    public EmployeeTm() {
    }

    public EmployeeTm(String empId, String name, String address, String dob, String nic, String contact, String salary) {
        this.empId = empId;
        this.name = name;
        this.address = address;
        this.dob = dob;
        this.nic = nic;
        this.contact = contact;
        this.salary = salary;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "EmployeeTm{" +
                "empId='" + empId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", dob='" + dob + '\'' +
                ", nic='" + nic + '\'' +
                ", contact='" + contact + '\'' +
                ", salary='" + salary + '\'' +
                '}';
    }
}
