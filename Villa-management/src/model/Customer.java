package model;

public class Customer {

    private String cusId;
    private String name;
    private String address;
    private String dob;
    private String nic;
    private String contact;
    private String sex;
    private String safaryId;

    private String safaryType;

    public Customer() {
    }

    public Customer(String cusId, String name, String address, String dob, String nic, String contact, String sex, String safaryId, String safaryType) {
        this.cusId = cusId;
        this.name = name;
        this.address = address;
        this.dob = dob;
        this.nic = nic;
        this.contact = contact;
        this.sex = sex;
        this.safaryId = safaryId;
        this.safaryType = safaryType;
    }

    public String getSafaryType() {
        return safaryType;
    }

    public void setSafaryType(String safaryType) {
        this.safaryType = safaryType;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSafaryId() {
        return safaryId;
    }

    public void setSafaryId(String safaryId) {
        this.safaryId = safaryId;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "cusId='" + cusId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", dob='" + dob + '\'' +
                ", nic='" + nic + '\'' +
                ", contact='" + contact + '\'' +
                ", sex='" + sex + '\'' +
                ", safaryId='" + safaryId + '\'' +
                ", safaryType='" + safaryType + '\'' +
                '}';
    }
}
