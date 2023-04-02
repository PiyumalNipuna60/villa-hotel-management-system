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
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import view.tm.RoomTM;
import view.tm.SafaryTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SafaryFormController {
    @FXML
    private ComboBox cmbDriverId;

    @FXML
    private ComboBox cmbSafaryType;

    @FXML
    private TableColumn colDate;

    @FXML
    private TableColumn colDriverContact;

    @FXML
    private TableColumn colDriverId;

    @FXML
    private TableColumn colDriverName;

    @FXML
    private TableColumn colSafaryId;

    @FXML
    private TableColumn colTime;

    @FXML
    private TableColumn colType;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDriverContct;

    @FXML
    private Label lblDriverName;

    @FXML
    private TableView<SafaryTm> tblSafary;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtSafaryId;

    @FXML
    private TextField txtTime;

    public void initialize() {
        colSafaryId.setCellValueFactory(new PropertyValueFactory("safaryId"));
        colType.setCellValueFactory(new PropertyValueFactory("type"));
        colDate.setCellValueFactory(new PropertyValueFactory("date"));
        colTime.setCellValueFactory(new PropertyValueFactory("time"));
        colDriverId.setCellValueFactory(new PropertyValueFactory("driverId"));
        colDriverName.setCellValueFactory(new PropertyValueFactory("driverName"));
        colDriverContact.setCellValueFactory(new PropertyValueFactory("driverContact"));

        LoadAllCustomer();
        generateRealTime();
        loadComboBox();

        cmbDriverId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    setDriverFields(newValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void setDriverFields(Object newValue) {

    }

    private void loadComboBox() {

    }

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
    void btnSearchOnAction(ActionEvent event) {
        String id = txtSafaryId.getText();

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }






}
