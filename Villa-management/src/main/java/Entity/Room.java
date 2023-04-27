package Entity;

import db.DBConnection;
import dto.BookingRoomDTO;
import dto.RoomDTO;
import tm.RoomTM;
import util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Room {

    public boolean save(RoomDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO room VALUES (?,?,?,?,?)",
                dto.getRoomId(),
                dto.getType(),
                dto.getDescription(),
                dto.getAvailable(),
                dto.getPrice()
        );
    }

    public boolean update(RoomDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE room SET type=?, description=?,available=?,price=? WHERE roomId=?",
                dto.getType(),
                dto.getDescription(),
                dto.getAvailable(),
                dto.getPrice(),
                dto.getRoomId()
        );
    }

    public static boolean updateAvailable(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE room SET available=? WHERE roomId=?", "not-Available", id);
    }

    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM room WHERE roomId=?", id);
    }

    public static RoomDTO search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("select*from room where roomId=?", id);
        if (rst.next()) {
           return new RoomDTO(
                   rst.getString(1),
                   rst.getString(2),
                   rst.getString(3),
                   rst.getString(4),
                   rst.getString(5)
           );
        }
        return null;
    }

    public static ArrayList<RoomDTO> searchRoomType(String type) throws SQLException, ClassNotFoundException {
        ArrayList<RoomDTO> all = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("select * from room where type=?", type);
        while (rst.next()) {
             all.add(new RoomDTO(
                             rst.getString(1),
                             rst.getString(2),
                             rst.getString(3),
                             rst.getString(4),
                             rst.getString(5)
                     ));
        }
        return all;
    }

    public ArrayList<RoomTM> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<RoomTM> all = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("select*from room");
        while (rst.next()) {
            all.add(new RoomTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            ));
        }
        return all;
    }

    public int getCount() throws SQLException, ClassNotFoundException {
        int count = 0;
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM room where available='Available' || available='available'");
        while (rst.next()) {
            count++;
        }
        return count;
    }

   public static boolean existRoom(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT roomId FROM room WHERE roomId=?");
        pstm.setString(1, id);
        return pstm.executeQuery().next();
    }
}
