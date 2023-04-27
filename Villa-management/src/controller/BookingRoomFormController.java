package controller;

import Entity.*;
import db.DBConnection;
import dto.BookingRoomDTO;
import dto.CustomerDTO;
import dto.RoomDTO;
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
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import tm.BookingRoomTM;
import tm.CustomerTm;
import util.CrudUtil;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

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

    //regex
    @FXML
    private TableView<BookingRoomTM> tblCustomer;

    @FXML
    private TextField txtPayment;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();

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
            CustomerDTO search = Customer.search(String.valueOf(newValue));
            if (!search.getCusId().equals(null)) {
                lblName.setText(search.getName());
                lblContact.setText(search.getContact());
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setRoomDetails(Object newValue) {
        try {
            RoomDTO search = Room.search(String.valueOf(newValue));
            if (!search.getRoomId().equals(null)) {
                lblPeice.setText(search.getPrice());
                lblAvailable.setText(search.getAvailable());
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setRoomFields(Object newValue) {
        ObservableList obList2 = FXCollections.observableArrayList();
        try {
            ArrayList<RoomDTO> roomDTOS = Room.searchRoomType(String.valueOf(newValue));
            for (RoomDTO x:roomDTOS) {
                obList2.add(x.getRoomId());
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
            ArrayList<CustomerTm> all = Customer.getAll();
            for (CustomerTm x:all) {
                obList3.add(x.getCusId());
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
            boolean save = bookingRoom.saveBooking(new RoomDetailsDTO(roomId, cusId, paymentType, payment));
            if (save) {
                new Alert(Alert.AlertType.INFORMATION, roomId + " Room " + cusId + " ..!").show();
                LoadAllCustomer();
                btnClearOnAction();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        Object cusId = cmbCustomerId.getValue();
        try {
            RoomDetailsDTO search = RoomDetails.search(String.valueOf(cusId));

            if (!RoomDetails.existRoom(String.valueOf(cusId))) {
                new Alert(Alert.AlertType.ERROR, cusId + " Customer Not Booking Room..!").show();
            } else {
                if (!search.getRoomId().equals(null)) {
                    cmbRoomId.setValue(search.getRoomId());
                    cmbPaymentType.setValue(search.getPaymentType());
                    txtPayment.setText(search.getPayment());

                    CustomerDTO sCus = Customer.search(String.valueOf(cusId));
                    lblName.setText(sCus.getName());
                    lblContact.setText(sCus.getContact());

                    RoomDTO sRoom = Room.search(String.valueOf(cmbRoomId.getValue()));
                    cmbRoomType.setValue(sRoom.getType());
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

    public void btnPrintOnAction(ActionEvent actionEvent) {
        String id = String.valueOf(cmbCustomerId.getValue());
        String name = lblName.getText();
        String contact = lblContact.getText();
        String roomId = String.valueOf(cmbRoomId.getValue());
        String roomType = String.valueOf(cmbRoomType.getValue());
        String price = lblPeice.getText();
        String paymentType = String.valueOf(cmbPaymentType.getValue());
        String payment = txtPayment.getText();

        HashMap hashMap = new HashMap();
        hashMap.put("id", id);
        hashMap.put("name", name);
        hashMap.put("contact", contact);
        hashMap.put("roomId", roomId);
        hashMap.put("roomType", roomType);
        hashMap.put("roomPrice", price);
        hashMap.put("paymentType", paymentType);
        hashMap.put("payment", payment);


        try {
            //Catch The Report
            JasperDesign load = JRXmlLoader.load(this.getClass().getResourceAsStream("/report/BookingReport.jrxml"));

            //Compile the Report
            JasperReport compileReport = JasperCompileManager.compileReport(load);

            //Fill the information which report needed
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, hashMap, new JREmptyDataSource(1));

            //Then the report is ready.. let's view it
            JasperViewer.viewReport(jasperPrint, false);


        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
