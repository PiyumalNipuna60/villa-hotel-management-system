package dto;

public class EmployeeScheduleDTO {

    private String scheduleId;
    private String time;
    private String date;
    private String shift;
    private String empId;

    public EmployeeScheduleDTO() {
    }

    public EmployeeScheduleDTO(String scheduleId, String time, String date, String shift, String empId) {
        this.scheduleId = scheduleId;
        this.time = time;
        this.date = date;
        this.shift = shift;
        this.empId = empId;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    @Override
    public String toString() {
        return "EmployeeSchedule{" +
                "scheduleId='" + scheduleId + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", shift='" + shift + '\'' +
                ", empId='" + empId + '\'' +
                '}';
    }
}
