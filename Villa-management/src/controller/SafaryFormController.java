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
import javafx.util.Duration;
import view.tm.RoomTM;
import view.tm.SafaryTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SafaryFormController {
    @FXML
    private ComboBox cmbDriverId;

    @FXML
    private ComboBox cmbSafaryType;

    @FXML
    private TableColumn colDate;

    @FXML
    private TableColumn colDriverContact;

    @FXML
    private TableColumn colDriverId;

    @FXML
    private TableColumn colDriverName;

    @FXML
    private TableColumn colSafaryId;

    @FXML
    private TableColumn colTime;

    @FXML
    private TableColumn colType;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDriverContct;

    @FXML
    private Label lblDriverName;

    @FXML
    private TableView<SafaryTm> tblSafary;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtSafaryId;

    @FXML
    private TextField txtTime;

    public void initialize() {
        colSafaryId.setCellValueFactory(new PropertyValueFactory("safaryId"));
        colType.setCellValueFactory(new PropertyValueFactory("type"));
        colDate.setCellValueFactory(new PropertyValueFactory("date"));
        colTime.setCellValueFactory(new PropertyValueFactory("time"));
        colDriverId.setCellValueFactory(new PropertyValueFactory("driverId"));
        colDriverName.setCellValueFactory(new PropertyValueFactory("driverName"));
        colDriverContact.setCellValueFactory(new PropertyValueFactory("driverContact"));

        LoadAllCustomer();
        generateRealTime();
        loadComboBox();

        cmbDriverId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    setDriverFields(newValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void setDriverFields(Object newValue) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM driver WHERE driverId=?");
            pstm.setString(1, String.valueOf(newValue));
            ResultSet rst = pstm.executeQuery();

            if (rst.next()){
                lblDriverName.setText(rst.getString(2));
                lblDriverContct.setText(rst.getString(4));
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

        ObservableList obList2 = FXCollections.observableArrayList();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select * from Driver");
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                obList2.add(new String(resultSet.getString(1)));
            }
            cmbDriverId.setItems(obList2);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void LoadAllCustomer() {
        tblSafary.getItems().clear();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select safary.safaryId,safary.type,safary.date,safary.time,safary.driverId,driver.driverName,driver.contact FROM safary LEFT JOIN driver ON safary.driverId=driver.driverId");
            ResultSet rst = pstm.executeQuery();
            while (rst.next()) {
                tblSafary.getItems().add(new SafaryTm(
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
        String id = txtSafaryId.getText();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select*from safary where safaryId=?");
            pstm.setString(1, id);
            ResultSet rst = pstm.executeQuery();

                if (rst.next()) {
                    cmbSafaryType.setValue(rst.getString(2));
                    txtDate.setValue(LocalDate.parse(rst.getString(3)));
                    txtTime.setText(rst.getString(4));
                    cmbDriverId.setValue(rst.getString(5));


                    PreparedStatement pstm2 = connection.prepareStatement("select*from driver where driverId=?");
                    pstm2.setString(1, (rst.getString(5)));
                    ResultSet rst2 = pstm2.executeQuery();

                    if (rst2.next()){
                        lblDriverName.setText(rst2.getString(2));
                        lblDriverContct.setText(rst2.getString(4));
                    }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnClearOnAction() {
        txtTime.clear();
        txtSafaryId.clear();
        cmbDriverId.getItems().clear();
        cmbSafaryType.getItems().clear();
        lblDriverName.setText("");
        lblDriverContct.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM safary WHERE safaryId=?");
            pstm.setString(1, txtSafaryId.getText());
            boolean b = pstm.executeUpdate() > 0;
            if (b) {
                new Alert(Alert.AlertType.INFORMATION, "Safary  " + txtSafaryId.getText() + " Deleted..!").show();
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
        String id = txtSafaryId.getText();
        Object type = cmbSafaryType.getValue();
        LocalDate date = txtDate.getValue();
        String time = txtTime.getText();
        Object driverId = cmbDriverId.getValue();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO safary VALUES (?,?,?,?,?)");
            pstm.setString(1, id);
            pstm.setString(2, String.valueOf(type));
            pstm.setString(3, String.valueOf(date));
            pstm.setString(4, time);
            pstm.setString(5, String.valueOf(driverId));
            boolean save = pstm.executeUpdate() > 0;
            if (save) {
                new Alert(Alert.AlertType.INFORMATION, id + " Room Added..!").show();
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
        String id = txtSafaryId.getText();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select*from safary where safaryId=?");
            pstm.setString(1, id);
            ResultSet rst = pstm.executeQuery();

            if (!existRoom(id)) {
                new Alert(Alert.AlertType.ERROR, id + " Safary Not Register..!").show();
            } else {
                if (rst.next()) {
                    cmbSafaryType.setValue(rst.getString(2));
                    txtDate.setValue(LocalDate.parse(rst.getString(3)));
                    txtTime.setText(rst.getString(4));
                    cmbDriverId.setValue(rst.getString(5));


                    PreparedStatement pstm2 = connection.prepareStatement("select*from driver where driverId=?");
                    pstm2.setString(1, (rst.getString(5)));
                    ResultSet rst2 = pstm2.executeQuery();

                    if (rst2.next()){
                        lblDriverName.setText(rst2.getString(2));
                        lblDriverContct.setText(rst2.getString(4));
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtSafaryId.getText();
        Object type = cmbSafaryType.getValue();
        LocalDate date = txtDate.getValue();
        String time = txtTime.getText();
        Object driverId = cmbDriverId.getValue();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("UPDATE safary SET type=?, date=?,time=?,driverId=? WHERE safaryId=?");
            pstm.setString(5, id);
            pstm.setString(1, String.valueOf(type));
            pstm.setString(2, String.valueOf(date));
            pstm.setString(3, time);
            pstm.setString(4, String.valueOf(driverId));
            boolean update = pstm.executeUpdate() > 0;
            if (update) {
                new Alert(Alert.AlertType.INFORMATION, id + " Safary Updated..!").show();
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
