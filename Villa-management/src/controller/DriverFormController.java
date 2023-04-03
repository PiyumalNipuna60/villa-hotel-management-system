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



    private void LoadAllCustomer() {

    }

    @FXML
    void SearchOnKeyPress(KeyEvent event) {

    }

    @FXML
    void btnClearOnAction() {

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