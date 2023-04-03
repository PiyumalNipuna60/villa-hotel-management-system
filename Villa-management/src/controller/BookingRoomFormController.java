package controller;

import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import view.tm.BookingRoomTM;
import view.tm.SafaryTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BookingRoomFormController {

    @FXML
    private ComboBox cmbCustomerId;

    @FXML
    private ComboBox cmbPaymentType;

    @FXML
    private ComboBox cmbRoomId;

    @FXML
    private ComboBox cmbRoomType;

    @FXML
    private TableColumn colCash;

    @FXML
    private TableColumn colContact;

    @FXML
    private TableColumn colCusId;

    @FXML
    private TableColumn colCusName;

    @FXML
    private TableColumn colPayment;

    @FXML
    private TableColumn colPaymentType;

    @FXML
    private TableColumn colRoomId;

    @FXML
    private TableColumn colRoomPrice;

    @FXML
    private TableColumn colRoomType;

    @FXML
    private Label lblAvailable;

    @FXML
    private Label lblContact;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPeice;

    @FXML
    private TableView<BookingRoomTM> tblCustomer;

    @FXML
    private TextField txtPayment;

    public void initialize() {


    }

    private void setCustomerDetails(Object newValue) {

    }

    private void setRoomDetails(Object newValue) {

    }

    private void setRoomFields(Object newValue) {

    }

    private void loadComboBox() {

    }

    private void LoadAllCustomer() {

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
    void btnSearchOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }





    //----------------------existCustomer--------------------------------------------------------------


}