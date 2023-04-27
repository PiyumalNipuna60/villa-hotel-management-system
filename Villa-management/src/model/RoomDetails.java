package Entity;

import db.DBConnection;
import dto.BookingRoomDTO;
import dto.RoomDTO;
import dto.RoomDetailsDTO;
import util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomDetails {

    public boolean save(RoomDetailsDTO bookingRoomDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO roomDetails VALUES (?,?,?,?)",
                bookingRoomDTO.getRoomId(),
                bookingRoomDTO.getCusId(),
                bookingRoomDTO.getPaymentType(),
                bookingRoomDTO.getPayment()
        );
    }

    public boolean update(RoomDetailsDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE roomDetails SET paymentType=?, payment=? WHERE roomId=? && cusId=?",
                dto.getPaymentType(),
                dto.getPayment(),
                dto.getRoomId(),
                dto.getCusId());
    }

    public boolean delete(String roomId,String cusId) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM roomDetails WHERE roomId=? && cusId=?", roomId, cusId);
    }

    public static RoomDetailsDTO search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("select*from roomDetails where cusId=?", id);
        if (rst.next()){
            return new RoomDetailsDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
        }
        return null;
    }

    public ArrayList<RoomDetailsDTO> getAll(String id) {
        return null;
    }

   public static boolean existRoom(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT cusId FROM roomDetails WHERE cusId=?");
        pstm.setString(1, id);
        return pstm.executeQuery().next();
    }
}
