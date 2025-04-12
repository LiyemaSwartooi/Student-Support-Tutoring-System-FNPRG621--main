/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itutor.booking.system;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import itutor.booking.system.Tutorial.TutorialDAO;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * @author Admin
 */
public class TutorAdminFormController implements Initializable {

    @FXML
    private Button Calculatebtn;
    @FXML
    private AnchorPane CONTRACTCLAIMFORM;

    @FXML
    private Button Claimbtn;

    @FXML
    private ComboBox<String> FromTime;

    @FXML
    private TextField RatePerUnit;

    @FXML
    private Button SubmitClaimbtn;

    @FXML
    private ComboBox<String> ToTime;

    @FXML
    private TextField TotalHoursWorkedforthemonth;
    @FXML
    private TextField Username;

    @FXML
    private TextField ValueofClaim;
///////////////////////////////////////////////////////////////
    @FXML
    private TableColumn<Tutorial, String> ViewPaymentsFullName;

    @FXML
    private TableColumn<Tutorial, Integer> ViewPaymentsID;

    @FXML
    private TableView<Tutorial> ViewPaymentsTableView;

    @FXML
    private TableColumn<Tutorial, String> ViewPaymentsTutorUsername;

    @FXML
    private TableColumn<Tutorial, String> ViewPaymentsYearofEmployment;

    @FXML
    private Button ViewPaymentsbtn;

    @FXML
    private TableColumn<Tutorial, String> ViewStatuscolPayments;

    @FXML
    private TableColumn<Tutorial, String> ViewcolPayments;

    /////////////////////////////////////////////////////////////////
    @FXML
    private Hyperlink Student_hyperlink11;

    @FXML
    private Hyperlink Student_hyperlink12;

    @FXML
    private Hyperlink Student_hyperlink13;

    @FXML
    private Hyperlink Student_hyperlink14;

    @FXML
    private Hyperlink Student_hyperlink15;

    @FXML
    private TableColumn<Tutorial, LocalDate> Tutor_Date;

    @FXML
    private TableColumn<Tutorial, String> Tutor_Name;

    @FXML
    private Button Tutor_Notifications;

    @FXML
    private AnchorPane Tutor_PaymentForm;

    @FXML
    private TableColumn<Tutorial, String> Tutor_SpecialComments;

    @FXML
    private TableColumn<Tutorial, LocalTime> Tutor_Time;

    @FXML
    private TableColumn<Tutorial, String> Tutor_Username;

    @FXML
    private TableColumn<Tutorial, Integer> Tutor_id;

    @FXML
    private AnchorPane Tutor_notifyForm;

    @FXML
    private ScrollPane Tutor_resourcesForm;

    @FXML
    private TableView<Tutorial> Tutor_tableView;

    @FXML
    private Button Tutors_Payments;

    @FXML
    private Button Tutors_resources;

    @FXML
    private AnchorPane greet_username;

    @FXML
    private Button Approvebtn;

    @FXML
    private Button Rejectbtn;

    @FXML
    private TableColumn<Tutorial, String> Tutor_Location;

    @FXML
    private TableColumn<Tutorial, String> Tutor_Status;
private AlertMessage alert = new AlertMessage();
    private List<String> generate24HourTimes() {
        List<String> times = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.of(0, 0);
        while (!time.equals(LocalTime.MIDNIGHT) || times.isEmpty()) {
            times.add(time.format(formatter));
            time = time.plusMinutes(30);
        }
        return times;
    }

    @FXML
    private void ViewData() {
        Tutor_id.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        Tutor_Username.setCellValueFactory(cellData -> cellData.getValue().studentUsernameProperty());
        Tutor_Name.setCellValueFactory(cellData -> cellData.getValue().moduleNameProperty());
        Tutor_Date.setCellValueFactory(cellData -> cellData.getValue().moduleDateProperty());
        Tutor_Time.setCellValueFactory(cellData -> cellData.getValue().moduleTimeProperty());
        Tutor_SpecialComments.setCellValueFactory(cellData -> cellData.getValue().specialCommentsProperty());
        Tutor_Location.setCellValueFactory(cellData -> cellData.getValue().locationProperty());
        Tutor_Status.setCellValueFactory(cellData -> cellData.getValue().statusProperty()); // Set cell value factory for Tutor_Status

        try {
            ObservableList<Tutorial> tutorials = FXCollections.observableArrayList(TutorialDAO.getAllTutorials());
            Tutor_tableView.setItems(tutorials);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Approvebtn.setOnAction(event -> {
            Tutorial selectedTutorial = Tutor_tableView.getSelectionModel().getSelectedItem();

            if (selectedTutorial == null) {
                showAlert("No Row Selected", "Please select a row to approve.", Alert.AlertType.WARNING);
            } else {
                try {
                    // Update the status to "Approved" in the database
                    selectedTutorial.setStatus("Approved");
                    TutorialDAO.updateTutorial(selectedTutorial);

                    refreshTableView();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Approval Successful");
                    alert.setHeaderText(null);
                    alert.setContentText("The request has been approved successfully.");
                    alert.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert("Error", "An error occurred while approving the request. Please try again.", Alert.AlertType.ERROR);
                }
            }
        });

        Rejectbtn.setOnAction(event -> {
            Tutorial selectedTutorial = Tutor_tableView.getSelectionModel().getSelectedItem();

            if (selectedTutorial == null) {
                showAlert("No Row Selected", "Please select a row to reject.", Alert.AlertType.WARNING);
            } else {
                try {
                    // Update the status to "Rejected" in the database
                    selectedTutorial.setStatus("Rejected");
                    TutorialDAO.updateTutorial(selectedTutorial);

                    refreshTableView();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Rejection Successful");
                    alert.setHeaderText(null);
                    alert.setContentText("The request has been rejected successfully.");
                    alert.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert("Error", "An error occurred while rejecting the request. Please try again.", Alert.AlertType.ERROR);
                }
            }
        });

        // Implement refreshTableView() here
        refreshTableView();
    }

    private void refreshTableView() {
        try {
            ObservableList<Tutorial> tutorials = FXCollections.observableArrayList(TutorialDAO.getAllTutorials());
            Tutor_tableView.setItems(tutorials);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void ToTime() {
        List<String> timeList = generate24HourTimes();
        ObservableList<String> listData = FXCollections.observableArrayList(timeList);
        ToTime.setItems(listData);
    }

    private void FromTime() {
        List<String> timeList = generate24HourTimes();
        ObservableList<String> listData = FXCollections.observableArrayList(timeList);
        FromTime.setItems(listData);
    }

    @FXML
    public void TutorSwitchForms() {
        Tutors_resources.setOnAction(event -> {
            // Deactivate other buttons and their corresponding AnchorPanes
            Tutors_Payments.setDisable(false);
            Tutor_PaymentForm.setVisible(false);
            Tutor_notifyForm.setVisible(false);
            CONTRACTCLAIMFORM.setVisible(false);
            Claimbtn.setDisable(false);

            // Activate Tutors_resources button and its corresponding AnchorPane
            Tutors_resources.setDisable(true);
            Tutor_resourcesForm.setVisible(true);

            // Deselect Tutor_Notifications button
            Tutor_Notifications.setDisable(false);
        });

        Tutors_Payments.setOnAction(event -> {
            // Deactivate other buttons and their corresponding AnchorPanes
            Tutors_resources.setDisable(false);
            Tutor_resourcesForm.setVisible(false);
            Tutor_notifyForm.setVisible(false);
            CONTRACTCLAIMFORM.setVisible(false);
            Claimbtn.setDisable(false);

            // Activate Tutors_Payments button and its corresponding AnchorPane
            Tutors_Payments.setDisable(true);
            Tutor_PaymentForm.setVisible(true);

            // Deselect Tutor_Notifications button
            Tutor_Notifications.setDisable(false);
        });

        Tutor_Notifications.setOnAction(event -> {
            // Deactivate other buttons and their corresponding AnchorPanes
            Tutors_Payments.setDisable(false);
            Tutor_PaymentForm.setVisible(false);
            Tutors_resources.setDisable(false);
            Tutor_resourcesForm.setVisible(false);
            CONTRACTCLAIMFORM.setVisible(false);
            Claimbtn.setDisable(false);

            // Activate Tutor_Notifications button and its corresponding AnchorPane
            Tutor_Notifications.setDisable(true);
            Tutor_notifyForm.setVisible(true);

            // Deselect other buttons
            Tutors_Payments.setDisable(false);
            Tutors_resources.setDisable(false);
        });

        Claimbtn.setOnAction(event -> {
            // Deactivate other buttons and their corresponding AnchorPanes
            Tutors_resources.setDisable(false);
            Tutor_resourcesForm.setVisible(false);
            Tutor_notifyForm.setVisible(false);
            Tutor_PaymentForm.setVisible(false);
            Tutors_Payments.setDisable(false);
            Tutor_Notifications.setDisable(false);

            // Activate Claimbtn button and its corresponding AnchorPane
            Claimbtn.setDisable(true);
            CONTRACTCLAIMFORM.setVisible(true);
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToTime();
        FromTime();
        SubmitClaimbtn.setOnAction(event -> submitClaim());
        // Handle click events for each hyperlink
        Student_hyperlink11.setOnAction(event -> openWebsite("https://openlibrary.org/"));
        Student_hyperlink12.setOnAction(event -> openWebsite("https://openstax.org/"));
        Student_hyperlink13.setOnAction(event -> openWebsite("https://insights.gostudent.org/en/best-resource-websites-for-online-tutors"));
        Student_hyperlink14.setOnAction(event -> openWebsite("https://schoolhouse.world/"));
        Student_hyperlink15.setOnAction(event -> openWebsite("https://za.ixl.com/?partner=bing&adGroup=Search%20-%20General%20-%20Mod%20Broad%20-%20ZA+resources&msclkid=b69bc6c3a4ab198f332aa0e2eaf3845e&utm_source=bing&utm_medium=cpc&utm_campaign=Search%20-%20General%20-%20Mod%20Broad%20-%20ZA&utm_term=%2Beducational%20%2Bresources&utm_content=resources"));

    }
// Method to open a website in the default browser

    private void openWebsite(String url) {
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (java.io.IOException e) {
            System.out.println("Error opening website: " + e.getMessage());
        }
    }

    @FXML
    private void handleViewPaymentsButtonClick(ActionEvent event) {
        try {
            ObservableList<Tutorial> paymentData = FXCollections.observableArrayList(Tutorial.TutorialDAO.getPaymentData());
            ViewPaymentsTableView.setItems(paymentData);

            // Set up the cell value factories for the payment data table columns
            ViewPaymentsID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
            ViewPaymentsFullName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullName()));
            ViewPaymentsTutorUsername.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTutorUsername()));
            ViewPaymentsYearofEmployment.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getYearOfEmployment()));
            ViewStatuscolPayments.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmpStatus()));
            ViewcolPayments.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPaymentSection()));
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException appropriately, such as showing an error message
            showAlert("Error", "Failed to fetch payment data from the database.", Alert.AlertType.ERROR);
        }
    }

   @FXML
    private void calculateValueOfClaim() {
        String username = Username.getText();
        String ratePerUnitStr = RatePerUnit.getText();
        String totalHoursWorkedStr = TotalHoursWorkedforthemonth.getText();

        // Validate username
        if (username.isEmpty()) {
            showAlert(AlertType.ERROR, "Validation Error", "Please enter the tutor username.");
            return;
        }

        // Validate rate per unit and total hours worked
        if (ratePerUnitStr.isEmpty() || totalHoursWorkedStr.isEmpty()) {
            showAlert(AlertType.ERROR, "Validation Error", "Please enter rate per unit and total hours worked.");
            return;
        }

        try {
            double ratePerUnit = Double.parseDouble(ratePerUnitStr);
            double totalHoursWorked = Double.parseDouble(totalHoursWorkedStr);

            // Check if rate is R180 per hour
            if (ratePerUnit != 180) {
                showAlert(AlertType.ERROR, "Validation Error", "Rate must be R180 per hour.");
                return;
            }

            // Check if total hours worked per day is <= 3
            double daysWorked = Math.ceil(totalHoursWorked / 24); // Assuming 24 hours per day
            if (totalHoursWorked > daysWorked * 3) {
                showAlert(AlertType.ERROR, "Validation Error", "You can only claim a maximum of 3 hours per day.");
                return;
            }

            double valueOfClaim = ratePerUnit * totalHoursWorked;

            // Display the calculated value of claim
            ValueofClaim.setText(String.valueOf(valueOfClaim));

        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Validation Error", "Please enter valid numbers for rate and hours worked.");
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void submitClaim() {
        // Get values from form fields
        String username = Username.getText();
        String fromTime = FromTime.getValue();
        String toTime = ToTime.getValue();
        String ratePerUnitStr = RatePerUnit.getText();
        String totalHoursWorkedStr = TotalHoursWorkedforthemonth.getText();
        String valueOfClaimStr = ValueofClaim.getText();

        // Validate inputs
        if (username.isEmpty() || fromTime == null || toTime == null || ratePerUnitStr.isEmpty() || totalHoursWorkedStr.isEmpty() || valueOfClaimStr.isEmpty()) {
            showAlert(AlertType.ERROR, "Validation Error", "Please fill in all fields.");
            return;
        }

        try {
            double ratePerUnit = Double.parseDouble(ratePerUnitStr);
            double totalHoursWorked = Double.parseDouble(totalHoursWorkedStr);
            double valueOfClaim = Double.parseDouble(valueOfClaimStr);

            // Insert claim into database
            insertClaim(username, fromTime, toTime, ratePerUnit, totalHoursWorked, valueOfClaim);
            showAlert(AlertType.INFORMATION, "Success", "Claim submitted successfully.");

        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Validation Error", "Please enter valid numbers for rate, hours worked, and claim value.");
        }
    }

    private void insertClaim(String username, String fromTime, String toTime, double ratePerUnit, double totalHoursWorked, double valueOfClaim) {
        Connection connection = Database.connectDB();
        String insertQuery = "INSERT INTO UserClaims (username, from_time, to_time, rate_per_unit, total_hours_worked_for_the_month, value_of_claim) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, fromTime);
            preparedStatement.setString(3, toTime);
            preparedStatement.setDouble(4, ratePerUnit);
            preparedStatement.setDouble(5, totalHoursWorked);
            preparedStatement.setDouble(6, valueOfClaim);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
