package Entity;

import dto.BillDTO;
import dto.BookingRoomDTO;
import dto.RoomDetailsDTO;
import tm.BookingRoomTM;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Bill {
    public static CusRoomDetail searchCust(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT c.name, c.contact, r.roomId, r.type ,r.price FROM customer c JOIN roomdetails rd on c.cusId = rd.cusId JOIN room r on rd.roomId = r.roomId WHERE c.cusId=?";
        ResultSet resultSet = CrudUtil.executeQuery(sql,id);
        CusRoomDetail cusRoomDetail = null;
        if(resultSet.next()) {
            cusRoomDetail = new CusRoomDetail(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),
                    resultSet.getString(4),resultSet.getString(5));
        }
        return cusRoomDetail;

    }

    public boolean save(String cusId, String bill, String cash, String balance) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO bill VALUES (?,?,?,?)",
                cusId,
                bill,
                cash,
                balance
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

    public static ResultSet search(String id) throws SQLException, ClassNotFoundException {
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
