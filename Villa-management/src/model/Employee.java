package Entity;

import db.DBConnection;
import dto.EmployeeDTO;
import tm.EmployeeTm;
import util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Employee {

    public boolean save(EmployeeDTO dto) throws SQLException, ClassNotFoundException {
       return CrudUtil.executeUpdate("INSERT INTO employee VALUES (?,?,?,?,?,?,?,?,?)",
                dto.getEmpId(),
                dto.getName(),
                dto.getAddress(),
                dto.getAge(),
                dto.getNic(),
                dto.getContact(),
                dto.getSalary(),
                dto.getUserName(),
                dto.getPassword()
                );
    }

    public boolean update(EmployeeDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE employee SET name=?, address=?,age=?,nic=?,contact=?,salary=?, WHERE empId=?",
                dto.getName(),
                dto.getAddress(),
                dto.getAge(),
                dto.getNic(),
                dto.getContact(),
                dto.getSalary(),
                dto.getEmpId()
                );
    }

    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM employee WHERE empid=?", id);
    }

    public EmployeeDTO search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("select*from employee where empId=?", id);
        if (rst.next()) {
            return new EmployeeDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9)
            );
        }
        return null;
    }

    public ArrayList<EmployeeTm> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<EmployeeTm> all = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("select*from employee");
        while (rst.next()) {
            all.add(new EmployeeTm(
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

    //----------------------existCustomer--------------------------------------------------------------
    public static boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT empId FROM employee WHERE empId=?");
        pstm.setString(1, id);
        return pstm.executeQuery().next();
    }
}