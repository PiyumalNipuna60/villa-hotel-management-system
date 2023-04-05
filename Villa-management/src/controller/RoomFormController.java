package controller;

import Entity.Room;
import db.DBConnection;
import dto.RoomDTO;
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
import tm.RoomTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RoomFormController {

    @FXML
    private ComboBox cmbRoomAvailable;

    @FXML
    private ComboBox cmbRoomDescrition;

    @FXML
    private ComboBox cmbRoomType;

    @FXML
    private TableColumn colAvailable;

    @FXML
    private TableColumn colDescription;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colPrice;

    @FXML
    private TableColumn colType;

    @FXML
    private Label lblDate;

    @FXML
    private TableView<RoomTM> tblRoom;

    @FXML
    private TextField txtRoomId;

    @FXML
    private TextField txtRoomPrice;

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory("roomId"));
        colType.setCellValueFactory(new PropertyValueFactory("type"));
        colDescription.setCellValueFactory(new PropertyValueFactory("description"));
        colAvailable.setCellValueFactory(new PropertyValueFactory("available"));
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));

        LoadAllCustomer();
        generateRealTime();
        loadComboBox();
    }

    private void loadComboBox() {
        ObservableList obList = FXCollections.observableArrayList();
        obList.add("AC");
        obList.add("Non-AC");
        obList.add("Non-AC/Food");
        obList.add("AC/Food");
        cmbRoomType.setItems(obList);

        ObservableList obList1 = FXCollections.observableArrayList();
        obList1.add("Single");
        obList1.add("Double");
        obList1.add("Family");
        cmbRoomDescrition.setItems(obList1);

        ObservableList obList2 = FXCollections.observableArrayList();
        obList2.add("Available");
        obList2.add("Non-Available");
        cmbRoomAvailable.setItems(obList2);
    }


    private void LoadAllCustomer() {
        tblRoom.getItems().clear();
        try {
            Room room = new Room();
            ArrayList<RoomTM> all = room.getAll();
            for (RoomTM tm : all) {
                tblRoom.getItems().add(
                        new RoomTM(
                                tm.getRoomId(),
                                tm.getType(),
                                tm.getDescription(),
                                tm.getAvailable(),
                                tm.getPrice()
                        ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void SearchOnKeyPress(KeyEvent event) {
        String id = txtRoomId.getText();
        try {
            if (!existRoom(id)) {

            } else {
                Room room = new Room();
                ResultSet rst = room.search(id);
                if (rst.next()) {
                    cmbRoomType.setValue(rst.getString(2));
                    cmbRoomDescrition.setValue(rst.getString(3));
                    cmbRoomAvailable.setValue(rst.getString(4));
                    txtRoomPrice.setText(rst.getString(5));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnClearOnAction() {
        txtRoomId.clear();
        txtRoomPrice.clear();
        cmbRoomType.getSelectionModel().clearSelection();
        cmbRoomAvailable.getSelectionModel().clearSelection();
        cmbRoomDescrition.getSelectionModel().clearSelection();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            Room room = new Room();
            boolean delete = room.delete(txtRoomId.getText());
            if (delete) {
                new Alert(Alert.AlertType.INFORMATION, "employee  " + txtRoomId.getText() + " Deleted..!").show();
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
        String id = txtRoomId.getText();
        String type = String.valueOf(cmbRoomType.getValue());
        String description = String.valueOf(cmbRoomAvailable.getValue());
        String available = String.valueOf(cmbRoomAvailable.getValue());
        String price = txtRoomPrice.getText();
        try {
            if (!existRoom(id)) {
                Room room = new Room();
                boolean save = room.save(new RoomDTO(id, type, description, available, price));
                if (save) {
                    new Alert(Alert.AlertType.INFORMATION, id + " Room Added..!").show();
                    LoadAllCustomer();
                    btnClearOnAction();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something Wrong..!").show();
                }
            }else {
                new Alert(Alert.AlertType.ERROR, "Duplicate Entry..!").show();
            }



        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String id = txtRoomId.getText();
        try {
            if (!existRoom(id)) {
                new Alert(Alert.AlertType.ERROR, id + " Driver Not Register..!").show();
            } else {
                Room room = new Room();
                ResultSet rst = room.search(id);
                if (rst.next()) {
                    cmbRoomType.setValue(rst.getString(2));
                    cmbRoomDescrition.setValue(rst.getString(3));
                    cmbRoomAvailable.setValue(rst.getString(4));
                    txtRoomPrice.setText(rst.getString(5));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtRoomId.getText();
        String type = String.valueOf(cmbRoomType.getValue());
        String description = String.valueOf(cmbRoomDescrition.getValue());
        String available = String.valueOf(cmbRoomAvailable.getValue());
        String price = txtRoomPrice.getText();

        try {
            Room room = new Room();
            boolean update = room.update(new RoomDTO(id, type, description, available, price));
            if (update) {
                new Alert(Alert.AlertType.INFORMATION, id + " Room Updated..!").show();
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
        PreparedStatement pstm = connection.prepareStatement("SELECT roomId FROM room WHERE roomId=?");
        pstm.setString(1, id);
        return pstm.executeQuery().next();
    }

}
