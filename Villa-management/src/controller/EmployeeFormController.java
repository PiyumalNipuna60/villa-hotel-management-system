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
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM employee WHERE empid=?");
            pstm.setString(1, txtEmpId.getText());
            boolean b = pstm.executeUpdate() > 0;
            if (b) {
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
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement("INSERT INTO employee VALUES (?,?,?,?,?,?,?,?,?)");
                pstm.setString(1, id);
                pstm.setString(2, name);
                pstm.setString(3, address);
                pstm.setString(4, age);
                pstm.setString(5, nic);
                pstm.setString(6, contact);
                pstm.setString(7, salary);
                pstm.setString(8, userName);
                pstm.setString(9, password);
                boolean update = pstm.executeUpdate() > 0;
                if (update) {
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
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select*from employee where empId=?");
            pstm.setString(1, id);
            ResultSet rst = pstm.executeQuery();

            if (!existCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, id + " Driver Not Register..!").show();
            } else {
                if (rst.next()) {
                    txtEmpName.setText(rst.getString(2));
                    txtEmpAddress.setText(rst.getString(3));
                    txtEmpAge.setText(rst.getString(4));
                    txtEmpNic.setText(rst.getString(5));
                    txtEmpContact.setText(rst.getString(6));
                    txtEmpSalary.setText(rst.getString(7));
                }
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
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("UPDATE employee SET name=?, address=?,age=?,nic=?,contact=?,salary=? WHERE empId=?");
            pstm.setString(7, id);
            pstm.setString(1, name);
            pstm.setString(2, address);
            pstm.setString(3, age);
            pstm.setString(4, nic);
            pstm.setString(5, contact);
            pstm.setString(6, salary);
            boolean update = pstm.executeUpdate() > 0;
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



}