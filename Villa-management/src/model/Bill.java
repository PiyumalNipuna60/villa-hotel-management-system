package model;

import dto.BillDTO;
import dto.BookingRoomDTO;
import dto.RoomDetailsDTO;
import tm.BookingRoomTM;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Bill {
    public boolean save(BillDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO bill VALUES (?,?,?,?)",
                dto.getCusId(),
                dto.getBill(),
                dto.getCash(),
                dto.getBalance()
        );
    }

    public boolean update(BillDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE bill SET bill=?, cash=?, balance=? WHERE cusId=?",
                dto.getBill(),
                dto.getCash(),
                dto.getBalance(),
                dto.getCusId());
    }

    public boolean delete(String cusId) throws SQLException, ClassNotFoundException {
       return CrudUtil.executeUpdate("DELETE FROM bill WHERE cusId=?", cusId);

    }

    public ResultSet search(String id) throws SQLException, ClassNotFoundException {
        return  CrudUtil.executeQuery("SELECT customer.cusId,customer.name,customer.contact,customer.type,room.roomId,room.type,room.price,roomdetails.payment,bill.bill,bill.cash,bill.balance FROM roomdetails INNER JOIN room ON room.roomId = roomdetails.roomId INNER JOIN customer ON customer.cusId = roomdetails.cusId  INNER JOIN bill ON bill.cusId = customer.cusId where customer.cusId=?",id);

    }


    public ArrayList<BillDTO> getAll() throws SQLException, ClassNotFoundException {

        ArrayList<BillDTO> AllBooking = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT customer.cusId,customer.name,customer.contact,customer.type,room.roomId,room.type,room.price,roomdetails.payment,bill.bill,bill.cash,bill.balance FROM roomdetails INNER JOIN room ON room.roomId = roomdetails.roomId INNER JOIN customer ON customer.cusId = roomdetails.cusId  INNER JOIN bill ON bill.cusId = customer.cusId");
        while (rst.next()) {
            AllBooking.add(new BillDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9),
                    rst.getString(10),
                    rst.getString(11)
            ));
        }
        return AllBooking;
    }
}
