/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itutor.booking.system;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Admin
 */
public class AlertMessage {
    private Alert alert;

    /**
     * Display a success message.
     * @param message The message to be displayed.
     */
    public void successMessage(String message) {
        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    /**
     * Display an error message.
     * @param message The error message to be displayed.
     */
    public void errorMessage(String message) {
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    /**
     * Display a warning message.
     * @param message The warning message to be displayed.
     */
    public void warningMessage(String message) {
        alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    /**
     * Display a confirmation message with OK and Cancel options.
     * @param message The confirmation message to be displayed.
     * @return true if the user clicks OK, false otherwise.
     */
    public boolean confirmMessage(String message) {
        alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> option = alert.showAndWait();

        return option.isPresent() && option.get() == ButtonType.OK;
    }

    /**
     * Display a message prompting the user to choose a unique tutor ID.
     */
    public void tutorIDMessage() {
        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Tutor ID Selection");
        alert.setHeaderText(null);
        alert.setContentText("Please choose a unique tutor ID from 1 to 100.");
        alert.show();
    }

    /**
     * Display an error message indicating that the selected tutor ID already exists.
     */
    public void tutorIDExistsMessage() {
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Tutor ID Error");
        alert.setHeaderText(null);
        alert.setContentText("The selected tutor ID already exists. Please choose another unique value.");
        alert.show();
    }

    /**
     * Display an error message indicating that the entered phone number already exists.
     */
    public void phoneNumberExistsMessage() {
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Phone Number Error");
        alert.setHeaderText(null);
        alert.setContentText("The entered phone number already exists. Please provide a unique phone number.");
        alert.show();
    }
}
