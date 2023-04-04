package dto;

public class BookingRoomDTO {
    private String cusId;
    private String cusName;
    private String contact;
    private String roomId;
    private String roomType;
    private String roomPrice;
    private String paymentType;
    private String payment;
    private String cash;

    public BookingRoomDTO() {
    }

    public BookingRoomDTO(String cusId, String cusName, String contact, String roomId, String roomType, String roomPrice, String paymentType, String payment, String cash) {
        this.cusId = cusId;
        this.cusName = cusName;
        this.contact = contact;
        this.roomId = roomId;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.paymentType = paymentType;
        this.payment = payment;
        this.cash = cash;
    }


    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
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

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    @Override
    public String toString() {
        return "BookingRoom{" +
                "cusId='" + cusId + '\'' +
                ", cusName='" + cusName + '\'' +
                ", contact='" + contact + '\'' +
                ", roomId='" + roomId + '\'' +
                ", roomType='" + roomType + '\'' +
                ", roomPrice='" + roomPrice + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", payment='" + payment + '\'' +
                ", cash='" + cash + '\'' +
                '}';
    }
}

