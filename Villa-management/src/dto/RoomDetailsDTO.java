package dto;

public class RoomDetailsDTO {

    private String roomId;
    private String empId;
    private String availabele;

    public RoomDetailsDTO() {
    }

    public RoomDetailsDTO(String roomId, String empId, String availabele) {
        this.roomId = roomId;
        this.empId = empId;
        this.availabele = availabele;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getAvailabele() {
        return availabele;
    }

    public void setAvailabele(String availabele) {
        this.availabele = availabele;
    }

    @Override
    public String toString() {
        return "RoomDetails{" +
                "roomId='" + roomId + '\'' +
                ", empId='" + empId + '\'' +
                ", availabele='" + availabele + '\'' +
                '}';
    }
}
