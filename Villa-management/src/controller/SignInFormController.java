package controller;

import Entity.Employee;
import db.DBConnection;
import dto.EmployeeDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignInFormController {
    public TextField txtEmpId;
    public TextField txtEmpName;
    public TextField txtEmpAddress;
    public Label txtxSignIn;
    public TextField txtEmpAge;
    public TextField txtEmpNic;
    public TextField txtEmpContact;
    public TextField txtEmpSalary;
    public TextField txtEmpUsername;
    public PasswordField txtPassword;
    public AnchorPane MainAnchorPane;

    public void btnSignInAction(ActionEvent actionEvent) {
        String id = txtEmpId.getText();
        String name = txtEmpName.getText();
        String address = txtEmpAddress.getText();
        String age = txtEmpAge.getText();
        String nic = txtEmpNic.getText();
        String contact = txtEmpContact.getText();
        String salary = txtEmpSalary.getText();
        String userName = txtEmpUsername.getText();
        String password = txtPassword.getText();

        try {
            if (!existCustomer(id)) {
                Employee employee = new Employee();
                boolean save = employee.save(new EmployeeDTO(id, name, address, age, nic, contact, salary, userName, password));
                if (save) {
                    new Alert(Alert.AlertType.INFORMATION, id + " Employee Added..!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Employee Id Already Add..!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnLoginOnAction(ActionEvent mouseEvent) {
     try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/LoginForm.fxml"));
            Parent parent = loader.load();
            Scene scene=new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage stage1= (Stage)MainAnchorPane.getScene().getWindow();
            stage1.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
       
    }
    
        //----------------------existCustomer--------------------------------------------------------------
    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT empId FROM employee WHERE empId=?");
        pstm.setString(1, id);
        return pstm.executeQuery().next();
    }


}
