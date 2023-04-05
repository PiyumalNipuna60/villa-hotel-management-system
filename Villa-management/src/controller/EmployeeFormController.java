package controller;

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
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import tm.EmployeeTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EmployeeFormController {

    public TextField txtEmpUserName;
    public TextField txtPassword;
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

    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory("empId"));
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colBAge.setCellValueFactory(new PropertyValueFactory("age"));
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
            if (!existCustomer(id)) {

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
    void btnSaveOnAction(ActionEvent event) {
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
            if (!existCustomer(id)) {
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

            if (search==null) {
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


    //----------------------existCustomer--------------------------------------------------------------
    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT empId FROM employee WHERE empId=?");
        pstm.setString(1, id);
        return pstm.executeQuery().next();
    }

}
