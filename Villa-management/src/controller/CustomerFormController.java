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
        cmbSafaryType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    setCustomerFields(newValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void setCustomerFields(Object newValue) {
        ObservableList obList2 = FXCollections.observableArrayList();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from safary where type=?");
            pstm.setString(1, String.valueOf(newValue));
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                obList2.add(new String(resultSet.getString(1)));
            }
            cmbSafaryid.setItems(obList2);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void LoadAllCustomer() {
        tblCustomer.getItems().clear();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select*from customer");
            ResultSet rst = pstm.executeQuery();
            while (rst.next()) {
                tblCustomer.getItems().add(new CustomerTm(
                        rst.getString("cusId"),
                        rst.getString("name"),
                        rst.getString("address"),
                        rst.getString("dob"),
                        rst.getString("Nic"),
                        rst.getString("Contact"),
                        rst.getString("sex"),
                        rst.getString("safaryId")));
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
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM Customer WHERE cusId=?");
            pstm.setString(1, txtCusId.getText());
            boolean b = pstm.executeUpdate() > 0;
            if (b) {
                new Alert(Alert.AlertType.INFORMATION, "Customer " + txtCusId.getText() + " Delete..!").show();
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
    void btnSaveOnAction(ActionEvent event) {
        String Id = txtCusId.getText();
        String name = txtCusName.getText();
        String address = txtCusAddress.getText();
        LocalDate dob = txtDob.getValue();
        String nic = txtCusNic.getText();
        String contact = txtContact.getText();
        String gender;
        Object safaryId = cmbSafaryid.getValue();
        Object safaryType = cmbSafaryType.getValue();

        if (rbnMale.isSelected()) {
            gender = "Male";
        } else {
            gender = "Femal";
        }


        try {
            if (!existCustomer(Id)) {
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement("Insert into Customer Values (?,?,?,?,?,?,?,?,?)");
                pstm.setString(1, Id);
                pstm.setString(2, name);
                pstm.setString(3, address);
                pstm.setString(4, String.valueOf(dob));
                pstm.setString(5, nic);
                pstm.setString(6, contact);
                pstm.setString(7, gender);
                pstm.setString(8, String.valueOf(safaryId));
                pstm.setString(9, String.valueOf(safaryType));
                boolean x = pstm.executeUpdate() > 0;

                if (x) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer " + Id + " Saved..!").show();
                    LoadAllCustomer();
                    btnClearOnAction();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Customer Id Already Add..!").show();
            }


        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnSearchOnAction() {
        String id = txtCusId.getText();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select*from customer where cusId=?");
            pstm.setString(1, id);
            ResultSet rst = pstm.executeQuery();

            if (!existCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, id + " Safary Not Register..!").show();
            } else {
                if (rst.next()) {
                    txtCusId.setText(rst.getString(1));
                    txtCusName.setText(rst.getString(2));
                    txtCusAddress.setText(rst.getString(3));
                    System.out.println(rst.getString(4));
                    txtDob.setValue(LocalDate.parse(rst.getString(4)));
                    txtCusNic.setText(rst.getString(5));
                    txtContact.setText(rst.getString(6));
                    String value = rst.getString(7);
                    System.out.println(value);
                    if (value.equals("Male")) {
                        rbnMale.setSelected(true);
                    } else {
                        rbnFemale.setSelected(true);
                    }

                    cmbSafaryid.setValue(rst.getObject(8));
                    cmbSafaryType.setValue(rst.getObject(9));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String Id = txtCusId.getText();
        String name = txtCusName.getText();
        String address = txtCusAddress.getText();
        LocalDate dob = txtDob.getValue();
        String nic = txtCusNic.getText();
        String contact = txtContact.getText();
        String gender;
        Object safaryId = cmbSafaryid.getValue();
        Object safaryType = cmbSafaryType.getValue();

        if (rbnMale.isSelected()) {
            gender = "Male";
        } else {
            gender = "Femal";
        }

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("UPDATE Customer SET name=?, address=?,dob=?,nic=?,contact=?,sex=?,safaryId=?,type=? WHERE cusId=?");
            pstm.setString(1, name);
            pstm.setString(2, address);
            pstm.setString(3, String.valueOf(dob));
            pstm.setString(4, nic);
            pstm.setString(5, contact);
            pstm.setString(6, gender);
            pstm.setString(7, String.valueOf(safaryId));
            pstm.setString(8, String.valueOf(safaryType));
            pstm.setString(9, Id);
            boolean x = pstm.executeUpdate() > 0;

            if (x) {
                new Alert(Alert.AlertType.INFORMATION, "Customer " + Id + " Update..!").show();
                LoadAllCustomer();
                btnClearOnAction();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
            }


        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void SearchOnKeyPress(KeyEvent keyEvent) {





    //----------------------Set Date & time--------------------------------------------------------------


    //----------------------existCustomer--------------------------------------------------------------

}