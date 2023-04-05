package controller;

import Entity.driver;
import db.DBConnection;
import dto.DriverDTO;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import tm.CustomerTm;
import tm.driverTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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
            driver d1 = new driver();
            ArrayList<driverTm> alls = d1.getAll();
            for (driverTm all : alls) {
                tblCustomer.getItems().add(
                        new driverTm(
                                all.getDriverId(),
                                all.getDriverName(),
                                all.getAddress(),
                                all.getContact()
                        ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void SearchOnKeyPress(KeyEvent event) {
        String id = txtCusId.getText();
        try {
            driver d1 = new driver();
            DriverDTO search = d1.search(id);
            if (search == null) {

            } else {
                txtCusAddress.setText(search.getAddress());
                txtCusName.setText(search.getDriverName());
                txtCusContact.setText(search.getContact());
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
        try {
            driver d1 = new driver();
            boolean delete = d1.delete(txtCusId.getText());
            if (delete) {
                new Alert(Alert.AlertType.INFORMATION, "Driver  " + txtCusId.getText() + " Deleted..!").show();
                btnClearOnAction();
                LoadAllCustomer();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtCusId.getText();
        String name = txtCusName.getText();
        String address = txtCusAddress.getText();
        String contact = txtCusContact.getText();

        try {
            driver driver = new driver();
            boolean save = driver.save(new DriverDTO(id, name, address, contact));

            if (save) {
                new Alert(Alert.AlertType.INFORMATION, id + " Driver Added..!").show();
                LoadAllCustomer();
                btnClearOnAction();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSearchOnAction() {
        String id = txtCusId.getText();
        try {
            driver d1 = new driver();
            DriverDTO search = d1.search(id);
            if (search == null) {
                new Alert(Alert.AlertType.ERROR, id+" Not System..!").show();
            } else {
                txtCusAddress.setText(search.getAddress());
                txtCusName.setText(search.getDriverName());
                txtCusContact.setText(search.getContact());
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtCusId.getText();
        String name = txtCusName.getText();
        String address = txtCusAddress.getText();
        String contact = txtCusContact.getText();

        try {
            driver driver = new driver();
            boolean update = driver.update(new DriverDTO(id, name, address, contact));
            if (update) {
                new Alert(Alert.AlertType.INFORMATION, id + " Driver Updated..!").show();
                btnClearOnAction();
                LoadAllCustomer();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    //----------------------Set Date & time--------------------------------------------------------------
    private void generateRealTime() {
        //lblDate.setText(LocalDate.now().toString());
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            lblDate.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


    //----------------------existCustomer--------------------------------------------------------------
    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT driverId FROM driver WHERE driverId=?");
        pstm.setString(1, id);
        return pstm.executeQuery().next();
    }


}
