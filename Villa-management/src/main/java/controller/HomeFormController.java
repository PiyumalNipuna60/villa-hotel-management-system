package controller;

import Entity.Customer;
import Entity.Room;
import Entity.Safary;
import Entity.driver;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HomeFormController {
    public Pane secondAnchorPane;
    public Label lbltotalCustomer;
    public Label lblSafariCount;
    public Label lblAvailableRoom;
    public Label lblAvailableDriver;
    public Label lbtDate;
    public Label lblTime;

    public void initialize() {
        generateRealTime();
        setCountDetails();
        AvailableDriver();
        AvailableRoom();
    }

    private void AvailableRoom() {
        try {
            Room room = new Room();
            int count = room.getCount();
            if (count<10){
                lblAvailableRoom.setText("0"+count);
            }else {
                lblAvailableRoom.setText(String.valueOf(count));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void AvailableDriver() {
        try {
            driver d1 = new driver();
            int count = d1.getCount();
            if (count<10){
                lblAvailableDriver.setText("0"+count);
            }else {
                lblAvailableDriver.setText(String.valueOf(count));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCountDetails() {
        CustomerCount();
        SafaryCount();

    }

    private void SafaryCount() {
        try {
            Safary safary = new Safary();
            int count = safary.getCount();
            if (count<10){
                lblSafariCount.setText("0"+count);
            }else {
                lblSafariCount.setText(String.valueOf(count));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void CustomerCount() {
        try {
            Customer customer = new Customer();
            int count = customer.getCount();
            if (count<10){
                lbltotalCustomer.setText("0"+count);
            }else {
                lbltotalCustomer.setText(String.valueOf(count));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    //----------------------Set Date & time--------------------------------------------------------------
    private void generateRealTime() {
        lbtDate.setText(LocalDate.now().toString());
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            lblTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
