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
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select*from room");
            ResultSet rst = pstm.executeQuery();
            while (rst.next()) {
                tblRoom.getItems().add(new RoomTM(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5)
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
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select*from room where roomId=?");
            pstm.setString(1, id);
            ResultSet rst = pstm.executeQuery();

            if (rst.next()) {
                cmbRoomType.setValue(rst.getString(2));
                cmbRoomDescrition.setValue(rst.getString(3));
                cmbRoomAvailable.setValue(rst.getString(4));
                txtRoomPrice.setText(rst.getString(5));
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
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM room WHERE roomId=?");
            pstm.setString(1, txtRoomId.getText());
            boolean b = pstm.executeUpdate() > 0;
            if (b) {
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
        Object type = cmbRoomType.getValue();
        Object description = cmbRoomAvailable.getValue();
        Object available = cmbRoomAvailable.getValue();
        String price = txtRoomPrice.getText();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO room VALUES (?,?,?,?,?)");
            pstm.setString(1, id);
            pstm.setString(2, String.valueOf(type));
            pstm.setString(3, String.valueOf(description));
            pstm.setString(4, String.valueOf(available));
            pstm.setString(5, price);
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
        String id = txtRoomId.getText();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("select*from room where roomId=?");
            pstm.setString(1, id);
            ResultSet rst = pstm.executeQuery();

            if (!existRoom(id)) {
                new Alert(Alert.AlertType.ERROR, id + " Driver Not Register..!").show();
            } else {
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
        Object type = cmbRoomType.getValue();
        Object description = cmbRoomDescrition.getValue();
        Object available = cmbRoomAvailable.getValue();
        String price = txtRoomPrice.getText();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("UPDATE room SET type=?, description=?,available=?,price=? WHERE roomId=?");
            pstm.setString(5, id);
            pstm.setString(1, String.valueOf(type));
            pstm.setString(2, String.valueOf(description));
            pstm.setString(3, String.valueOf(available));
            pstm.setString(4, price);
            boolean update = pstm.executeUpdate() > 0;
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
