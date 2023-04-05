package controller;

import Entity.BookingRoom;
import Entity.RoomDetails;
import db.DBConnection;
import dto.BookingRoomDTO;
import dto.RoomDetailsDTO;
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
import tm.BookingRoomTM;
import util.CrudUtil;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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
        try {
            tblCustomer.getItems().clear();
            BookingRoom bookingRoom = new BookingRoom();
            ArrayList<BookingRoomTM> all = bookingRoom.getAll();

            for (BookingRoomTM booking : all) {
                tblCustomer.getItems().add(
                        new BookingRoomTM(
                                booking.getCusId(),
                                booking.getCusName(),
                                booking.getContact(),
                                booking.getRoomId(),
                                booking.getRoomType(),
                                booking.getRoomPrice(),
                                booking.getPaymentType(),
                                booking.getPayment(),
                                booking.getCash()
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
            BookingRoom bookingRoom = new BookingRoom();
            boolean delete = bookingRoom.delete(String.valueOf(cmbRoomId.getValue()), String.valueOf(cmbCustomerId.getValue()));
            if (delete) {
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
        String roomId = String.valueOf(cmbRoomId.getValue());
        String cusId = String.valueOf(cmbCustomerId.getValue());
        String paymentType = String.valueOf(cmbPaymentType.getValue());
        String payment = txtPayment.getText();

        try {
        BookingRoom bookingRoom = new BookingRoom();
            boolean save = bookingRoom.save(new RoomDetailsDTO(roomId, cusId, paymentType, payment));
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
        String roomId = String.valueOf(cmbRoomId.getValue());
        String cusId = String.valueOf(cmbCustomerId.getValue());
        String paymentType = String.valueOf(cmbPaymentType.getValue());
        String payment = txtPayment.getText();

        try {
            BookingRoom bookingRoom = new BookingRoom();
            boolean update = bookingRoom.update(new RoomDetailsDTO(roomId, cusId, paymentType, payment));
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

}
