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
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import tm.CustomerTm;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

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
//                } catch (Exception e) {neke hari

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

    @FXML
    void btnClearOnAction() {
         cmbCusId.getSelectionModel().clearSelection();
         txtBill.clear();
         txtCash.clear();
         txtBalance.clear();
         lblCusContact.setText("");
         lblCusName.setText("");
         lblRoomId.setText("");
         lblRoomPrice.setText("");
         lblRoomType.setText("");
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
        String roomId = lblRoomId.getText();
        String roomType = lblRoomType.getText();

        String roomPrice = lblRoomPrice.getText();
        String bill = txtBill.getText();
        String cash = txtCash.getText();
        String balance = txtBalance.getText();

        try {
            Bill bill1 = new Bill();
            boolean save = bill1.save(cusId,bill,cash,balance);
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
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            lblDate.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
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

    public void cmbCusIdOnAction(ActionEvent actionEvent){
        if (!String.valueOf(cmbCusId.getValue()).equals("null")){
            try {
                CusRoomDetail cusRoomDetail = Bill.searchCust(String.valueOf(cmbCusId.getValue()));
                lblCusName.setText(cusRoomDetail.getName());
                lblCusContact.setText(cusRoomDetail.getContact());
                lblRoomId.setText(cusRoomDetail.getRoomId());
                lblRoomPrice.setText(cusRoomDetail.getRoomPrice());
                lblRoomType.setText(cusRoomDetail.getRoomType());
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void btnPrintOnAction(ActionEvent actionEvent) {
        String cusId = String.valueOf(cmbCusId.getValue());
        String bill = txtBill.getText();
        String cash = txtCash.getText();
        String balance = txtBalance.getText();

        HashMap hashMap = new HashMap();
        hashMap.put("id",cusId);
        hashMap.put("bill",bill);
        hashMap.put("cash",cash);
        hashMap.put("balance",balance);

        try {
            //Catch The Report
            JasperDesign load = JRXmlLoader.load(this.getClass().getResourceAsStream("/report/BillReport.jrxml"));

            //Compile the Report
            JasperReport compileReport = JasperCompileManager.compileReport(load);

            //Fill the information which report needed
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, hashMap, new JREmptyDataSource(1));

            //Then the report is ready.. let's view it
            JasperViewer.viewReport(jasperPrint,false);


        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void btnReportOnAction(ActionEvent actionEvent) {

        try {
            JasperReport compileReport = (JasperReport) JRLoader.loadObject(Objects.requireNonNull(this.getClass().getResource("/report/FullBillReport.jasper")));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, getCon());
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }
    public Connection getCon(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/villaManagement","root","1234");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
