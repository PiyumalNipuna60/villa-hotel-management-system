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
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM driver WHERE driverId=?");
            pstm.setString(1, String.valueOf(newValue));
            ResultSet rst = pstm.executeQuery();

            if (rst.next()){
                lblDriverName.setText(rst.getString(2));
                lblDriverContct.setText(rst.getString(4));
            }


        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadComboBox() {
        ObservableList obList = FXCollections.observableArrayList();
        obList.add("Yala");
        obList.add("Beach");
        obList.add("Camping");
        obList.add("diving");
        cmbSafaryType.setItems(obList);

        ObservableList obList2 = FXCollections.observableArrayList();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from Driver");
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                obList2.add(new String(resultSet.getString(1)));
            }
            cmbDriverId.setItems(obList2);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void LoadAllCustomer() {
        tblSafary.getItems().clear();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select safary.safaryId,safary.type,safary.date,safary.time,safary.driverId,driver.driverName,driver.contact FROM safary LEFT JOIN driver ON safary.driverId=driver.driverId");
            ResultSet rst = pstm.executeQuery();
            while (rst.next()) {
                tblSafary.getItems().add(new SafaryTm(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5),
                        rst.getString(6),
                        rst.getString(7)
                ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

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
