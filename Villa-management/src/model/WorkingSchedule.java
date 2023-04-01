package model;

public class WorkingSchedule {

    private String scheduleId;
    private String time;
    private String date;
    private String shift;

    public WorkingSchedule() {
    }

    public WorkingSchedule(String scheduleId, String time, String date, String shift) {
        this.scheduleId = scheduleId;
        this.time = time;
        this.date = date;
        this.shift = shift;
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

    @Override
    public String toString() {
        return "WorkingSchedule{" +
                "scheduleId='" + scheduleId + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", shift='" + shift + '\'' +
                '}';
    }
}
