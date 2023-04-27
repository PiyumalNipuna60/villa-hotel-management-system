package controller;

import Entity.Customer;
import Entity.Employee;
import db.DBConnection;
import dto.EmployeeDTO;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import tm.EmployeeTm;
import util.ValidationUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class EmployeeFormController {

    public TextField txtEmpUserName;
    public TextField txtPassword;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    @FXML
    private TableColumn colAddress;

    @FXML
    private TableColumn colBAge;

    @FXML
    private TableColumn colContact;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colSalary;

    @FXML
    private TableColumn colnic;

    @FXML
    private Label lblDate;

    @FXML
    private TableView tblEmployee;

    @FXML
    private TextField txtEmpAddress;

    @FXML
    private TextField txtEmpAge;

    @FXML
    private TextField txtEmpContact;

    @FXML
    private TextField txtEmpId;

    @FXML
    private TextField txtEmpName;

    @FXML
    private TextField txtEmpNic;

    @FXML
    private TextField txtEmpSalary;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();

    public void initialize() {

        Pattern patternId = Pattern.compile("^(E0)[0-9]{1,5}$");
        Pattern patternName = Pattern.compile("^[A-z]{3,}$");
        Pattern patternAddress = Pattern.compile("^[A-z 0-9 ,/]{5,}$");
        Pattern patternAge = Pattern.compile("^[0-9]{1,3}$");
        Pattern patternNic = Pattern.compile("^[0-9 v]{10,12}$");
        Pattern patternContact = Pattern.compile("^(071|072|070|075|076|078|)[0-9]{10}$");
        Pattern patternSalary = Pattern.compile("^[0-9]{2,}$");

        map.put(txtEmpId, patternId);
        map.put(txtEmpName, patternName);
        map.put(txtEmpAddress, patternAddress);
        map.put(txtEmpAge, patternAge);
        map.put(txtEmpNic, patternNic);
        map.put(txtEmpContact, patternContact);
        map.put(txtEmpSalary, patternSalary);

        colId.setCellValueFactory(new PropertyValueFactory("empId"));
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colBAge.setCellValueFactory(new PropertyValueFactory("dob"));
        colnic.setCellValueFactory(new PropertyValueFactory("nic"));
        colContact.setCellValueFactory(new PropertyValueFactory("contact"));
        colSalary.setCellValueFactory(new PropertyValueFactory("salary"));

        LoadAllCustomer();
        generateRealTime();

    }

    private void LoadAllCustomer() {
        tblEmployee.getItems().clear();
        try {
            Employee employee = new Employee();
            ArrayList<EmployeeTm> all = employee.getAll();
            for (EmployeeTm tm : all) {
                tblEmployee.getItems().add(
                        new EmployeeTm(
                                tm.getEmpId(),
                                tm.getName(),
                                tm.getAddress(),
                                tm.getDob(),
                                tm.getNic(),
                                tm.getContact(),
                                tm.getSalary()
                        ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void SearchOnKeyPress(KeyEvent event) {
        String id = txtEmpId.getText();
        try {
            if (!Employee.existCustomer(id)) {

            } else {
                Employee employee = new Employee();
                EmployeeDTO search = employee.search(id);

                txtEmpName.setText(search.getName());
                txtEmpAddress.setText(search.getAddress());
                txtEmpAge.setText(search.getAge());
                txtEmpNic.setText(search.getNic());
                txtEmpContact.setText(search.getContact());
                txtEmpSalary.setText(search.getSalary());
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnClearOnAction() {
        txtEmpId.clear();
        txtEmpName.clear();
        txtEmpAddress.clear();
        txtEmpAge.clear();
        txtEmpNic.clear();
        txtEmpContact.clear();
        txtEmpSalary.clear();
        txtEmpUserName.clear();
        txtPassword.clear();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            Employee employee = new Employee();
            boolean delete = employee.delete(txtEmpId.getText());
            if (delete) {
                new Alert(Alert.AlertType.INFORMATION, "employee  " + txtEmpId.getText() + " Deleted..!").show();
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
        String id = txtEmpId.getText();
        String name = txtEmpName.getText();
        String address = txtEmpAddress.getText();
        String age = txtEmpAge.getText();
        String nic = txtEmpNic.getText();
        String contact = txtEmpContact.getText();
        String salary = txtEmpSalary.getText();
        String userName = txtEmpUserName.getText();
        String password = txtPassword.getText();

        try {
            if (id.equals("") && name.equals("") && address.equals("") && age.equals("") && nic.equals("") && contact.equals("") && !salary.equals("")){
                if (!Employee.existCustomer(id)) {
                    Employee employee = new Employee();
                    boolean save = employee.save(new EmployeeDTO(id, name, address, age, nic, contact, salary, userName, password));
                    if (save) {
                        new Alert(Alert.AlertType.INFORMATION, id + " Employee Added..!").show();
                        LoadAllCustomer();
                        btnClearOnAction();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Employee Id Already Add..!").show();
                }
            }else {
                new Alert(Alert.AlertType.ERROR, "Enter Data..!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSearchOnAction() {

        String id = txtEmpId.getText();
        try {
            Employee employee = new Employee();
            EmployeeDTO search = employee.search(id);

            if (search == null) {
                new Alert(Alert.AlertType.ERROR, id + " Driver Not Register..!").show();
            } else {
                txtEmpName.setText(search.getName());
                txtEmpAddress.setText(search.getAddress());
                txtEmpAge.setText(search.getAge());
                txtEmpNic.setText(search.getNic());
                txtEmpContact.setText(search.getContact());
                txtEmpSalary.setText(search.getSalary());
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtEmpId.getText();
        String name = txtEmpName.getText();
        String address = txtEmpAddress.getText();
        String age = txtEmpAge.getText();
        String nic = txtEmpNic.getText();
        String contact = txtEmpContact.getText();
        String salary = txtEmpSalary.getText();
        String userName = txtEmpUserName.getText();
        String password = txtPassword.getText();

        try {
            Employee employee = new Employee();
            boolean update = employee.update(new EmployeeDTO(id, name, address, age, nic, contact, salary, userName, password));
            if (update) {
                new Alert(Alert.AlertType.INFORMATION, id + " Employee Updated..!").show();
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
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object respond =  ValidationUtil.Validation(map);
            if (respond instanceof TextField) {
                TextField textField = (TextField) respond;
                textField.requestFocus();
            } else {
                boolean exit = Employee.existCustomer(txtEmpId.getText());
                if (exit) {
                  btnSave.setDisable(true);
                    btnDelete.setDisable(false);
                    btnUpdate.setDisable(false);
                } else {
                    btnSave.setDisable(false);
                    btnDelete.setDisable(true);
                    btnUpdate.setDisable(true);
                    btnSaveOnAction();
                }
            }
        }
    }
}
