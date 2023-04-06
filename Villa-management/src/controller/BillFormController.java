package controller;

import Entity.*;
import db.DBConnection;
import dto.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import tm.BillTM;
import tm.BookingRoomTM;
import tm.CustomerTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class BillFormController {

    @FXML
    private ComboBox cmbCusId;

    @FXML
    private TableColumn colBalance;

    @FXML
    private TableColumn colBill;

    @FXML
    private TableColumn colCash;

    @FXML
    private TableColumn colCusContact;

    @FXML
    private TableColumn colCusId;

    @FXML
    private TableColumn colCusName;

    @FXML
    private TableColumn colPayment;

    @FXML
    private TableColumn colRoomId;

    @FXML
    private TableColumn colRoomPrice;

    @FXML
    private TableColumn colSafaryType;

    @FXML
    private TableColumn colType;

    @FXML
    private Label lblCusContact;

    @FXML
    private Label lblCusName;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblPayment;

    @FXML
    private Label lblRoomId;

    @FXML
    private Label lblRoomPrice;

    @FXML
    private Label lblRoomType;

    @FXML
    private Label lblSafaryType;

    @FXML
    private TableView tblSafary;

    @FXML
    private TextField txtBalance;

    @FXML
    private TextField txtBill;

    @FXML
    private TextField txtCash;

    public void initialize() {
        colCusId.setCellValueFactory(new PropertyValueFactory("cusId"));
        colCusName.setCellValueFactory(new PropertyValueFactory("Name"));
        colCusContact.setCellValueFactory(new PropertyValueFactory("contact"));
        colSafaryType.setCellValueFactory(new PropertyValueFactory("safaryType"));
        colRoomId.setCellValueFactory(new PropertyValueFactory("roomId"));
        colType.setCellValueFactory(new PropertyValueFactory("roomType"));
        colRoomPrice.setCellValueFactory(new PropertyValueFactory("roomPrice"));
        colPayment.setCellValueFactory(new PropertyValueFactory("payment"));
        colBill.setCellValueFactory(new PropertyValueFactory("bill"));
        colCash.setCellValueFactory(new PropertyValueFactory("cash"));
        colBalance.setCellValueFactory(new PropertyValueFactory("balance"));



        LoadAllCustomer();
        generateRealTime();
        loadComboBox();

//        cmbCusId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                try {
//                    setDetailsFields(newValue);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });


    }

    private void LoadAllCustomer() {
        try {
            tblSafary.getItems().clear();
            Bill bill = new Bill();
            ArrayList<BillDTO> all = bill.getAll();

            for (BillDTO booking : all) {
                tblSafary.getItems().add(
                        new BillDTO(
                                booking.getCusId(),
                                booking.getName(),
                                booking.getContact(),
                                booking.getSafaryType(),
                                booking.getRoomId(),
                                booking.getRoomType(),
                                booking.getRoomPrice(),
                                booking.getPayment(),
                                booking.getBill(),
                                booking.getCash(),
                                booking.getBalance()
                        ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadComboBox() {
        try {
            ObservableList<String> objects = FXCollections.observableArrayList();
            Customer customer=new Customer();
            ArrayList<CustomerTm> all = customer.getAll();
            for (CustomerTm tm:all) {
                objects.add(tm.getCusId());
            }
            cmbCusId.setItems(objects);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDetailsFields(Object newValue) {

//        try {
//            Customer customer = new Customer();
//            CustomerDTO search = customer.search(String.valueOf(newValue));
//            lblCusName.setText(search.getName());
//            lblCusContact.setText(search.getContact());
//            lblSafaryType.setText(search.getSafaryType());
//
//
//            RoomDetails room = new RoomDetails();
//            ResultSet search1 = room.search(String.valueOf(newValue));
//            if (search1!=null){
//                lblRoomId.setText(search1.getString(1));
//                lblPayment.setText(search1.getString(4));
//            }
//
//
//
//            Room room1 = new Room();
//            ResultSet search2 = room1.search(String.valueOf(newValue));
//            if (search2!=null){
//                lblRoomPrice.setText(search2.getString(5));
//                lblRoomType.setText(search2.getString(2));
//            }
//
//
//            Bill bill = new Bill();
//            ResultSet search3 = bill.search(String.valueOf(newValue));
//            if (search3!=null){
//                txtBill.setText(search3.getString(2));
//                txtCash.setText(search3.getString(3));
//                txtBalance.setText(search3.getString(4));
//            }

/*            Bill bill = new Bill();
            ResultSet search1 = bill.search(String.valueOf(newValue));
            lblCusName.setText(search1.getString(1));
            lblCusContact.setText(search1.getString(2));
            lblSafaryType.setText(search1.getString(3));
            lblRoomId.setText(search1.getString(4));
            lblPayment.setText(search1.getString(5));
            lblRoomPrice.setText(search1.getString(6));
            lblRoomType.setText(search1.getString(7));
            txtBill.setText(search1.getString(8));
            txtCash.setText(search1.getString(9));
            txtBalance.setText(search1.getString(10));*/
//
//        } catch (SQLException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
    }

    @FXML
    void btnClearOnAction() {
         cmbCusId.getSelectionModel().clearSelection();
         txtBill.clear();
         txtCash.clear();
         txtBalance.clear();
         lblCusContact.setText("");
         lblCusName.setText("");
         lblPayment.setText("");
         lblRoomId.setText("");
         lblRoomPrice.setText("");
         lblRoomType.setText("");
         lblSafaryType.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        try {
            Bill bill = new Bill();
            boolean delete = bill.delete(String.valueOf(cmbCusId.getValue()));
            if (delete) {
                new Alert(Alert.AlertType.INFORMATION,  " Bill Delete..!").show();
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
        String cusId = String.valueOf(cmbCusId.getValue());
        String name = lblCusName.getText();
        String contact = lblCusContact.getText();
        String safaryType = lblSafaryType.getText();
        String roomId = lblRoomId.getText();
        String roomType = lblRoomType.getText();
        String roomPrice = lblRoomPrice.getText();
        String payment = lblPayment.getText();
        String bill = txtBill.getText();
        String cash = txtCash.getText();
        String balance = txtBalance.getText();

        try {
            Bill bill1 = new Bill();
            boolean save = bill1.save(new BillDTO(cusId,name,contact,safaryType,roomId,roomType,roomPrice,payment,bill,cash,balance));
            if (save) {
                new Alert(Alert.AlertType.INFORMATION,  " Bill Save..!").show();
                LoadAllCustomer();
                btnClearOnAction();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String cusId = String.valueOf(cmbCusId.getValue());
        String name = lblCusName.getText();
        String contact = lblCusContact.getText();
        String safaryType = lblSafaryType.getText();
        String roomId = lblRoomId.getText();
        String roomType = lblRoomType.getText();
        String roomPrice = lblRoomPrice.getText();
        String payment = lblPayment.getText();
        String bill = txtBill.getText();
        String cash = txtCash.getText();
        String balance = txtBalance.getText();

        try {
            Bill bill1 = new Bill();
            boolean save = bill1.update(new BillDTO(cusId,name,contact,safaryType,roomId,roomType,roomPrice,payment,bill,cash,balance));
            if (save) {
                new Alert(Alert.AlertType.INFORMATION,  " Bill Update..!").show();
                LoadAllCustomer();
                btnClearOnAction();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
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
    boolean existRoom(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT cusId FROM roomDetails WHERE cusId=?");
        pstm.setString(1, id);
        return pstm.executeQuery().next();
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {

        try {
            Bill bill = new Bill();
            ResultSet search1 = bill.search(String.valueOf(cmbCusId.getValue()));
            lblCusName.setText(search1.getString(1));
            lblCusContact.setText(search1.getString(2));
            lblSafaryType.setText(search1.getString(3));
            lblRoomId.setText(search1.getString(4));
            lblPayment.setText(search1.getString(5));
            lblRoomPrice.setText(search1.getString(6));
            lblRoomType.setText(search1.getString(7));
            txtBill.setText(search1.getString(8));
            txtCash.setText(search1.getString(9));
            txtBalance.setText(search1.getString(10));

        } catch (SQLException | ClassNotFoundException e) {

        }

    }
}
