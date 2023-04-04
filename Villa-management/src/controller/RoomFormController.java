package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class RoomFormController {

    @FXML
    private ComboBox<?> cmbRoomAvailable;

    @FXML
    private ComboBox<?> cmbRoomDescrition;

    @FXML
    private ComboBox<?> cmbRoomType;

    @FXML
    private TableColumn<?, ?> colAvailable;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private Label lblDate;

    @FXML
    private TableView<?> tblRoom;

    @FXML
    private TextField txtRoomId;

    @FXML
    private TextField txtRoomPrice;

    @FXML
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

    }

    @FXML
    void SearchOnKeyPress(KeyEvent event) {

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
    void btnSearchOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }






}
