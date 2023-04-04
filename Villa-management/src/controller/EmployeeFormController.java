package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;

public class EmployeeFormController {

    @FXML
    private TableColumn colAddress;

    @FXML
    private TableColumn colBAge;

    @FXML
    private TableColumn colContact;

    @FXML
    private TableColumn colGender;

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
    private RadioButton rbnFemale;

    @FXML
    private RadioButton rbnMale;

    @FXML
    private ToggleGroup sexuaol;

    @FXML
    private TableView<?> tblEmployee;

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

    @FXML
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
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select*from employee");
            ResultSet rst = pstm.executeQuery();
            while (rst.next()) {
                tblEmployee.getItems().add(new EmployeeTm(
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
        String id = txtEmpId.getText();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select*from employee where empId=?");
            pstm.setString(1, id);
            ResultSet rst = pstm.executeQuery();

            if (rst.next()) {
                txtEmpName.setText(rst.getString(2));
                txtEmpAddress.setText(rst.getString(3));
                txtEmpAge.setText(rst.getString(4));
                txtEmpNic.setText(rst.getString(5));
                txtEmpContact.setText(rst.getString(6));
                txtEmpSalary.setText(rst.getString(7));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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