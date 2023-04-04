package dto;

public class SafaryDTO {
    private  String safaryId;
    private  String type;
    private  String date;
    private  String time;
    private  String driverId;
    private  String driverName;
    private  String driverContact;

    public SafaryDTO() {
    }

    public SafaryDTO(String safaryId, String type, String date, String time, String driverId, String driverName, String driverContact) {
        this.safaryId = safaryId;
        this.type = type;
        this.date = date;
        this.time = time;
        this.driverId = driverId;
        this.driverName = driverName;
        this.driverContact = driverContact;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverContact() {
        return driverContact;
    }

    public void setDriverContact(String driverContact) {
        this.driverContact = driverContact;
    }

    public String getSafaryId() {
        return safaryId;
    }

    public void setSafaryId(String safaryId) {
        this.safaryId = safaryId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    @Override
    public String toString() {
        return "Safary{" +
                "safaryId='" + safaryId + '\'' +
                ", type='" + type + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", driverId='" + driverId + '\'' +
                ", driverName='" + driverName + '\'' +
                ", driverContact='" + driverContact + '\'' +
                '}';
    }
}
