package Entity;

public class CustomerDetails {

    private String empId;
    private String cusId;
    private String details;
    private String date;
    private String time;

    public CustomerDetails() {
    }

    public CustomerDetails(String empId, String cusId, String details, String date, String time) {
        this.empId = empId;
        this.cusId = cusId;
        this.details = details;
        this.date = date;
        this.time = time;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "CustomerDetails{" +
                "empId='" + empId + '\'' +
                ", cusId='" + cusId + '\'' +
                ", details='" + details + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
