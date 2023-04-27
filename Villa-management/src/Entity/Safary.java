package Entity;

import db.DBConnection;
import dto.BookingRoomDTO;
import dto.SafaryDTO;
import tm.SafaryTm;
import util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Safary {
    public boolean save(SafaryDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO safary VALUES (?,?,?,?,?)",
                dto.getSafaryId(),
                dto.getType(),
                dto.getDate(),
                dto.getTime(),
                dto.getDriverId()
                );
    }

    public boolean update(SafaryDTO dto) throws SQLException, ClassNotFoundException {
      return CrudUtil.executeUpdate("UPDATE safary SET type=?, date=?,time=?,driverId=? WHERE safaryId=?",
               dto.getType(),
               dto.getDate(),
               dto.getTime(),
               dto.getDriverId(),
               dto.getSafaryId()
               );
    }

    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM safary WHERE safaryId=?",id);
    }

    public SafaryDTO search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("select*from safary where safaryId=?", id);
        if (rst.next()){
           return new SafaryDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }
        return null;
    }

    public static ArrayList<SafaryDTO> searchAllSafary(String type) throws SQLException, ClassNotFoundException {
        ArrayList<SafaryDTO> all = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("select * from safary where type=?",type);
        while (rst.next()) {
            all.add(new SafaryDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            ));
        }
        return all;
    }

    public static ArrayList<SafaryTm> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<SafaryTm> all = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("select safary.safaryId,safary.type,safary.date,safary.time,safary.driverId,driver.driverName,driver.contact FROM safary LEFT JOIN driver ON safary.driverId=driver.driverId");
        while (rst.next()) {
            all.add(new SafaryTm(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            ));
        }
        return all;
    }

    public int getCount() throws SQLException, ClassNotFoundException {
        int count=0;
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM safary");
        while (rst.next()){
            count++;
        }
        return count;
    }
}
