package controller;

import Entity.Customer;
import Entity.Safary;
import Entity.driver;
import db.DBConnection;
import dto.DriverDTO;
import dto.SafaryDTO;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import tm.SafaryTm;
import tm.driverTm;
import util.ValidationUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class SafaryFormController {
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
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
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();

    public void initialize() {
        btnSave.setDisable(true);
        Pattern patternSId=Pattern.compile("^(SF00)[0-9]{1,5}$");
        Pattern patternTime=Pattern.compile("^[0-9]{2}(:)[0-9]{2}(:)[0-9]{2}$");

        map.put(txtSafaryId,patternSId);
        map.put(txtTime,patternTime);

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
                    if (txtSafaryId.getText()!=null && txtTime.getText()!=null && txtDate.getValue()!=null) {
                        btnSave.setDisable(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void setDriverFields(Object newValue) {
        try {
            DriverDTO search = driver.search(String.valueOf(newValue));
            if (!search.getDriverId().equals(null)){
                lblDriverName.setText(search.getDriverName());
                lblDriverContct.setText(search.getContact());
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
            ArrayList<driverTm> all = driver.getAll();
            for (driverTm x:all) {
                obList2.add(x.getDriverId());
            }
            cmbDriverId.setItems(obList2);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void LoadAllCustomer() {
        tblSafary.getItems().clear();
        try {
            Safary safary = new Safary();
            ArrayList<SafaryTm> all = safary.getAll();
            for (SafaryTm tm:all) {
                tblSafary.getItems().add(
                  new SafaryTm(
                          tm.getSafaryId(),
                          tm.getType(),
                          tm.getDate(),
                          tm.getTime(),
                          tm.getDriverId(),
                          tm.getDriverName(),
                          tm.getDriverContact()
                  )
                );
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void SearchOnKeyPress(KeyEvent event) {
        String id = txtSafaryId.getText();
        try {
            Safary safary = new Safary();
            SafaryDTO search1 = safary.search(id);

            if (search1!=null) {
                    cmbSafaryType.setValue(search1.getType());
                    txtDate.setValue(LocalDate.parse(search1.getDate()));
                    txtTime.setText(search1.getTime());
                    cmbDriverId.setValue(search1.getDriverId());

                    driver driver = new driver();
                    DriverDTO search = driver.search(search1.getDriverId());

                    if (search!=null){
                        lblDriverName.setText(search.getDriverName());
                        lblDriverContct.setText(search.getContact());
                    }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnClearOnAction() {
        txtTime.clear();
        txtSafaryId.clear();
        cmbDriverId.getSelectionModel().clearSelection();
        cmbSafaryType.getSelectionModel().clearSelection();
        lblDriverName.setText("");
        lblDriverContct.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            Safary safary = new Safary();
            boolean delete = safary.delete(txtSafaryId.getText());
            if (delete) {
                new Alert(Alert.AlertType.INFORMATION, "Safary  " + txtSafaryId.getText() + " Deleted..!").show();
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
    void btnSaveOnAction() {
        String id = txtSafaryId.getText();
        String type = String.valueOf(cmbSafaryType.getValue());
        String date = String.valueOf(txtDate.getValue());
        String time = txtTime.getText();
        String driverId = String.valueOf(cmbDriverId.getValue());

        if (id!=null && date!=null && time!=null){
            try {
                Safary safary = new Safary();
                boolean save = safary.save(new SafaryDTO(id, type, date, time, driverId));
                if (save) {
                    new Alert(Alert.AlertType.INFORMATION, id + " Room Added..!").show();
                    LoadAllCustomer();
                    btnClearOnAction();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
                }


            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Enter Data..!").show();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String id = txtSafaryId.getText();
        try {
            Safary safary = new Safary();
            SafaryDTO search1 = safary.search(id);

            if (search1!=null) {
                cmbSafaryType.setValue(search1.getType());
                txtDate.setValue(LocalDate.parse(search1.getDate()));
                txtTime.setText(search1.getTime());
                cmbDriverId.setValue(search1.getDriverId());

                driver driver = new driver();
                DriverDTO search = driver.search(search1.getDriverId());

                if (search!=null){
                    lblDriverName.setText(search.getDriverName());
                    lblDriverContct.setText(search.getContact());
                }
            }else {
                new Alert(Alert.AlertType.ERROR, "Safary id not Register..!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtSafaryId.getText();
        String type = String.valueOf(cmbSafaryType.getValue());
        String date = String.valueOf(txtDate.getValue());
        String time = txtTime.getText();
        String driverId = String.valueOf(cmbDriverId.getValue());

        try {
            Safary safary = new Safary();
            boolean update = safary.update(new SafaryDTO(id, type, date, time, driverId));
            if (update) {
                new Alert(Alert.AlertType.INFORMATION, id + " Safary Updated..!").show();
                btnClearOnAction();
                LoadAllCustomer();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private void generateRealTime() {
        //lblDate.setText(LocalDate.now().toString());
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            lblDate.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void textFieldsKeyReleasesd(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        ValidationUtil.Validation(map);
        if (keyEvent.getCode()== KeyCode.ENTER){
            Object respond = ValidationUtil.Validation(map);
            if (respond instanceof TextField){
                TextField textField= (TextField) respond;
                textField.requestFocus();
            }else {
                boolean exit= Customer.existCustomer(txtSafaryId.getText());
                if (exit){

                }else {
                    btnSaveOnAction();
                }
            }
        }
    }

}
