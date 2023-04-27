package tm;

public class RoomTM {

    private String roomId;
    private String type;
    private String description;
    private String available;
    private String price;

    public RoomTM() {
    }

    public RoomTM(String roomId, String type, String description, String available, String price) {
        this.roomId = roomId;
        this.type = type;
        this.description = description;
        this.available = available;
        this.price = price;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "RoomTM{" +
                "roomId='" + roomId + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", available='" + available + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
