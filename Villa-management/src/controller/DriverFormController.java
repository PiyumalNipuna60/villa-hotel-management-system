package controller;

import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import view.tm.CustomerTm;
import view.tm.driverTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DriverFormController {

    @FXML
    private TableColumn colAddress;

    @FXML
    private TableColumn colContact;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colName;

    @FXML
    private Label lblDate;

    @FXML
    private TableView tblCustomer;

    @FXML
    private TextField txtCusAddress;

    @FXML
    private TextField txtCusContact;

    @FXML
    private TextField txtCusId;

    @FXML
    private TextField txtCusName;

    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory("driverId"));
        colName.setCellValueFactory(new PropertyValueFactory("driverName"));
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colContact.setCellValueFactory(new PropertyValueFactory("contact"));

        LoadAllCustomer();
        generateRealTime();

    }

    private void LoadAllCustomer() {
        tblCustomer.getItems().clear();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select*from driver");
            ResultSet rst = pstm.executeQuery();
            while (rst.next()) {
                tblCustomer.getItems().add(new driverTm(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4)));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void SearchOnKeyPress(KeyEvent event) {
        String id = txtCusId.getText();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select*from driver where driverId=?");
            pstm.setString(1, id);
            ResultSet rst = pstm.executeQuery();

            if (rst.next()) {
                txtCusAddress.setText(rst.getString(3));
                txtCusName.setText(rst.getString(2));
                txtCusContact.setText(rst.getString(4));
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnClearOnAction() {
        txtCusId.clear();
        txtCusName.clear();
        txtCusAddress.clear();
        txtCusContact.clear();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchOnAction() {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }








}