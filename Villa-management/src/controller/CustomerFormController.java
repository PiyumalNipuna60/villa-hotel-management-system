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
import view.tm.CustomerTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.util.Duration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomerFormController {

    public DatePicker txtDob;
    @FXML
    private ComboBox cmbSafaryid;
    @FXML
    private ComboBox cmbSafaryType;

    @FXML
    private TableColumn colAddress;

    @FXML
    private TableColumn colBirthday;

    @FXML
    private TableColumn colContact;

    @FXML
    private TableColumn colGender;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colNic;

    @FXML
    private TableColumn colSalary;

    @FXML
    private Label lblDate;

    @FXML
    private RadioButton rbnFemale;

    @FXML
    private RadioButton rbnMale;

    @FXML
    private ToggleGroup sexuaol;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtCusAddress;

    @FXML
    private TextField txtCusId;

    @FXML
    private TextField txtCusName;

    @FXML
    private TextField txtCusNic;

    public void initialize() {
        loadComboBox();

        colId.setCellValueFactory(new PropertyValueFactory("cusId"));
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colBirthday.setCellValueFactory(new PropertyValueFactory("dob"));
        colNic.setCellValueFactory(new PropertyValueFactory("nic"));
        colContact.setCellValueFactory(new PropertyValueFactory("contact"));
        colGender.setCellValueFactory(new PropertyValueFactory("sex"));
        colSalary.setCellValueFactory(new PropertyValueFactory("safaryId"));

        LoadAllCustomer();
        generateRealTime();

    }

    private void LoadAllCustomer() {

    }

    private void loadComboBox() {


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

    public void SearchOnKeyPress(KeyEvent keyEvent) {
        btnSearchOnAction();
    }

    public void btnClearOnAction() {


    }


    //----------------------Set Date & time--------------------------------------------------------------
    private void generateRealTime() {

    }


    //----------------------existCustomer--------------------------------------------------------------
    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {

    }
}
