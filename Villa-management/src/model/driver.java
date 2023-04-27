package Entity;

import db.DBConnection;
import dto.DriverDTO;
import tm.driverTm;
import util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class driver {
    public boolean save(DriverDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO driver VALUES (?,?,?,?)",
                dto.getDriverId(),
                dto.getDriverName(),
                dto.getAddress(),
                dto.getContact()
        );
    }

    public boolean update(DriverDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE driver SET driverName=?, address=?,contact=? WHERE driverId=?",
                dto.getDriverName(),
                dto.getAddress(),
                dto.getContact(),
                dto.getDriverId()
        );
    }

    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM driver WHERE driverId=?",id);
    }

    public static DriverDTO search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("select*from driver where driverId=?", id);
        if (rst.next()) {
            return new DriverDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
        }
        return null;
    }

    public static ArrayList<driverTm> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<driverTm> all = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("select*from driver");
        while (rst.next()) {
            all.add(new driverTm(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)));
        }
        return all;
    }

    public int getCount() throws SQLException, ClassNotFoundException {
        int count=0;
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM driver");
        while (rst.next()){
            count++;
        }
        return count;
    }

    //----------------------existCustomer--------------------------------------------------------------
    public static boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT driverId FROM driver WHERE driverId=?");
        pstm.setString(1, id);
        return pstm.executeQuery().next();
    }
}
