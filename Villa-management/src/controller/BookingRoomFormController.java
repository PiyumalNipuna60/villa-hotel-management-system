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
import javafx.util.Duration;
import view.tm.BookingRoomTM;
import view.tm.SafaryTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BookingRoomFormController {

    @FXML
    private ComboBox cmbCustomerId;

    @FXML
    private ComboBox cmbPaymentType;

    @FXML
    private ComboBox cmbRoomId;

    @FXML
    private ComboBox cmbRoomType;

    @FXML
    private TableColumn colCash;

    @FXML
    private TableColumn colContact;

    @FXML
    private TableColumn colCusId;

    @FXML
    private TableColumn colCusName;

    @FXML
    private TableColumn colPayment;

    @FXML
    private TableColumn colPaymentType;

    @FXML
    private TableColumn colRoomId;

    @FXML
    private TableColumn colRoomPrice;

    @FXML
    private TableColumn colRoomType;

    @FXML
    private Label lblAvailable;

    @FXML
    private Label lblContact;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPeice;

    @FXML
    private TableView<BookingRoomTM> tblCustomer;

    @FXML
    private TextField txtPayment;

    public void initialize() {
        colCusId.setCellValueFactory(new PropertyValueFactory("cusId"));
        colCusName.setCellValueFactory(new PropertyValueFactory("cusName"));
        colContact.setCellValueFactory(new PropertyValueFactory("contact"));
        colRoomId.setCellValueFactory(new PropertyValueFactory("roomId"));
        colRoomType.setCellValueFactory(new PropertyValueFactory("roomType"));
        colRoomPrice.setCellValueFactory(new PropertyValueFactory("roomPrice"));
        colPaymentType.setCellValueFactory(new PropertyValueFactory("paymentType"));
        colPayment.setCellValueFactory(new PropertyValueFactory("payment"));
        colCash.setCellValueFactory(new PropertyValueFactory("cash"));

        LoadAllCustomer();
        generateRealTime();
        loadComboBox();

        cmbRoomType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    setRoomFields(newValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        cmbRoomId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    setRoomDetails(newValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    setCustomerDetails(newValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void setCustomerDetails(Object newValue) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from customer where cusId=?");
            pstm.setString(1, String.valueOf(newValue));
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                lblName.setText(resultSet.getString(2));
                lblContact.setText(resultSet.getString(6));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setRoomDetails(Object newValue) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from room where roomId=?");
            pstm.setString(1, String.valueOf(newValue));
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                lblPeice.setText(resultSet.getString(5));
                lblAvailable.setText(resultSet.getString(4));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setRoomFields(Object newValue) {
        ObservableList obList2 = FXCollections.observableArrayList();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from room where type=?");
            pstm.setString(1, String.valueOf(newValue));
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                obList2.add(new String(resultSet.getString(1)));
            }
            cmbRoomId.setItems(obList2);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadComboBox() {
        ObservableList obList = FXCollections.observableArrayList();
        obList.add("Full");
        obList.add("Half");
        cmbPaymentType.setItems(obList);


        ObservableList obList2 = FXCollections.observableArrayList();
        obList2.add("AC");
        obList2.add("Non-AC");
        obList2.add("Non-AC/Food");
        obList2.add("AC/Food");
        cmbRoomType.setItems(obList2);

        ObservableList obList3 = FXCollections.observableArrayList();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from customer");
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                obList3.add(new String(resultSet.getString(1)));
            }
            cmbCustomerId.setItems(obList3);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void LoadAllCustomer() {
        tblCustomer.getItems().clear();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT customer.cusId,customer.name,customer.contact,room.roomId,room.type,room.price,roomDetails.paymentType,roomDetails.payment,(room.price-roomDetails.payment) FROM customer JOIN roomDetails ON customer.cusId = roomDetails.cusId JOIN room ON room.roomId= roomDetails.roomId;");
            ResultSet rst = pstm.executeQuery();
            while (rst.next()) {
                tblCustomer.getItems().add(new BookingRoomTM(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5),
                        rst.getString(6),
                        rst.getString(7),
                        rst.getString(8),
                        rst.getString(9)
                ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void btnClearOnAction() {
        cmbCustomerId.getSelectionModel().clearSelection();
        cmbRoomId.getSelectionModel().clearSelection();
        cmbPaymentType.getSelectionModel().clearSelection();
        cmbRoomType.getSelectionModel().clearSelection();
        txtPayment.clear();
        lblName.setText("");
        lblContact.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM roomDetails WHERE roomId=? && cusId=?");
            pstm.setString(1, String.valueOf(cmbRoomId.getValue()));
            pstm.setString(1, String.valueOf(cmbCustomerId.getValue()));

            boolean b = pstm.executeUpdate() > 0;
            if (b) {
                new Alert(Alert.AlertType.INFORMATION, "Room  " + cmbRoomId.getValue() + " Booking Deleted..!").show();
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
        Object roomId =cmbRoomId.getValue();
        Object cusId = cmbCustomerId.getValue();
        Object paymentType = cmbPaymentType.getValue();
        String payment = txtPayment.getText();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO roomDetails VALUES (?,?,?,?)");
            pstm.setString(1, String.valueOf(roomId));
            pstm.setString(2, String.valueOf(cusId));
            pstm.setString(3, String.valueOf(paymentType));
            pstm.setString(4, payment);
            boolean save = pstm.executeUpdate() > 0;
            if (save) {
                new Alert(Alert.AlertType.INFORMATION, roomId + " Room "+cusId+" ..!").show();
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
    void btnSearchOnAction(ActionEvent event) {
        Object cusId = cmbCustomerId.getValue();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select*from roomDetails where cusId=?");
            pstm.setString(1, String.valueOf(cusId));
            ResultSet rst = pstm.executeQuery();

            if (!existRoom(String.valueOf(cusId))) {
                new Alert(Alert.AlertType.ERROR, cusId + " Customer Not Booking Room..!").show();
            } else {
                if (rst.next()) {
                    cmbRoomId.setValue(rst.getString(1));
                    cmbPaymentType.setValue(rst.getString(3));
                    txtPayment.setText(rst.getString(4));


                    PreparedStatement pstm2 = connection.prepareStatement("select*from customer where cusId=?");
                    pstm2.setString(1, String.valueOf(cusId));
                    ResultSet rst2 = pstm2.executeQuery();
                    if (rst2.next()){
                        lblName.setText(rst2.getString(2));
                        lblContact.setText(rst2.getString(4));
                    }

                    PreparedStatement pstm3 = connection.prepareStatement("select*from room where roomId=?");
                    pstm3.setString(1, String.valueOf(cmbRoomId.getValue()));
                    ResultSet rst3 = pstm3.executeQuery();
                    if (rst3.next()){
                        cmbRoomType.setValue(rst3.getString(2));
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        Object roomId =cmbRoomId.getValue();
        Object cusId = cmbCustomerId.getValue();
        Object paymentType = cmbPaymentType.getValue();
        String payment = txtPayment.getText();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("UPDATE roomDetails SET paymentType=?, payment=? WHERE roomId=? && cusId=?");
            pstm.setString(3, String.valueOf(roomId));
            pstm.setString(4, String.valueOf(cusId));
            pstm.setString(1, String.valueOf(paymentType));
            pstm.setString(2, payment);
            boolean update = pstm.executeUpdate() > 0;
            if (update) {
                new Alert(Alert.AlertType.INFORMATION, "Room  " + roomId + " Booking Update..!").show();
                btnClearOnAction();
                LoadAllCustomer();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }





    //----------------------existCustomer--------------------------------------------------------------


}