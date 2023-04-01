package model;

public class Employee {

    private String empId;
    private String name;
    private String address;
    private String dob;
    private String nic;
    private String contact;
    private String salary;
    private String userName;
    private String password;

    public Employee() {
    }

    public Employee(String empId, String name, String address, String dob, String nic, String contact, String salary, String userName, String password) {
        this.empId = empId;
        this.name = name;
        this.address = address;
        this.dob = dob;
        this.nic = nic;
        this.contact = contact;
        this.salary = salary;
        this.userName = userName;
        this.password = password;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId='" + empId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", dob='" + dob + '\'' +
                ", nic='" + nic + '\'' +
                ", contact='" + contact + '\'' +
                ", salary='" + salary + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
