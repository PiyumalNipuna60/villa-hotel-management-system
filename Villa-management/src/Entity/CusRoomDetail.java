package Entity;

public class CusRoomDetail {
    private String name;
    private String  contact;
    private String  roomId;
    private String roomType;
    private String roomPrice;

    public CusRoomDetail(){}

    public CusRoomDetail(String name, String contact, String roomId, String roomType, String roomPrice) {
        this.name = name;
        this.contact = contact;
        this.roomId = roomId;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(String roomPrice) {
        this.roomPrice = roomPrice;
    }

    @Override
    public String toString() {
        return "CusRoomDetail{" +
                "name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", roomId='" + roomId + '\'' +
                ", roomType='" + roomType + '\'' +
                ", roomPrice='" + roomPrice + '\'' +
                '}';
    }
}
