package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoginFormController {

    private PasswordField txtPassword;

    private TextField txtUserName;

    private Label txtxSignIn;

    void btnLoginOnAction(ActionEvent event) {

        try {
            String name= txtUserName.getText();
            String psw=txtPassword.getText();
            if (existEmployee(name,psw)){
                    System.out.println("AdminDashBoardForm");
                    setUi("AdminDashBoardForm");
            }else {
                System.out.println("AdminDashBoardForm");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    boolean existEmployee(String name,String psw) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("select*from employee where userName=? and password=?");
        pstm.setString(1, name);
        pstm.setString(2,psw);
         return pstm.executeQuery().next();
    }

    public void setUi(String url){
        try {
            Stage primaryStage=new Stage();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml")));
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void txtSignInOnAction(MouseEvent event) {

    }

}
