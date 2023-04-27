package Entity;

import db.DBConnection;
import dto.BookingRoomDTO;
import dto.RoomDetailsDTO;
import javafx.scene.control.Alert;
import tm.BookingRoomTM;
import util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookingRoom {
    public boolean save(RoomDetailsDTO bookingRoomDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO roomDetails VALUES (?,?,?,?)",
                bookingRoomDTO.getRoomId(),
                bookingRoomDTO.getCusId(),
                bookingRoomDTO.getPaymentType(),
                bookingRoomDTO.getPayment()
        );

    }
    //Transaction
    public boolean saveBooking(RoomDetailsDTO bookingRoomDTO) throws SQLException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            boolean save = save(bookingRoomDTO);
            if (save){
                boolean update = Room.updateAvailable(bookingRoomDTO.getRoomId());
                if (update){
                    con.commit();
                    return true;
                }
            }
               return false;
        } catch (SQLException | ClassNotFoundException throwables) {
            con.rollback();
            return false;
        }finally {
            System.out.println("Transaction complete");
            con.setAutoCommit(true);
        }
    }

    public boolean update(RoomDetailsDTO dto) throws SQLException, ClassNotFoundException {
       return CrudUtil.executeUpdate("UPDATE roomDetails SET paymentType=?, payment=? WHERE roomId=? && cusId=?",
               dto.getPaymentType(),
               dto.getPayment(),
               dto.getRoomId(),
               dto.getCusId());
    }

    public boolean delete(String roomId, String cusId) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM roomDetails WHERE roomId=? && cusId=?", roomId, cusId);

    }

    public BookingRoomDTO search(String id) {
        return null;
    }

    public ArrayList<BookingRoomTM> getAll() throws SQLException, ClassNotFoundException {

        ArrayList<BookingRoomTM> AllBooking = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT customer.cusId,customer.name,customer.contact,room.roomId,room.type,room.price,roomDetails.paymentType,roomDetails.payment,(room.price-roomDetails.payment) FROM customer JOIN roomDetails ON customer.cusId = roomDetails.cusId JOIN room ON room.roomId= roomDetails.roomId;");
        while (rst.next()) {
            AllBooking.add(new BookingRoomTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9)
            ));
        }
        return AllBooking;
    }
}

