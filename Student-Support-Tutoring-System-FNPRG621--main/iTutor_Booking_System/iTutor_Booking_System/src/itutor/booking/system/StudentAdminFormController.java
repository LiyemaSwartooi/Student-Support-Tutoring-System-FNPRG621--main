/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itutor.booking.system;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import itutor.booking.system.Tutorial.TutorialDAO;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.PasswordField;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Optional;
import javafx.geometry.Pos;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Admin
 */
public class StudentAdminFormController implements Initializable {

    @FXML
    private TableView<Tutorial> Student_tableView;

    @FXML
    private Button ViewDetails;

    @FXML
    private TableColumn<Tutorial, Integer> Student_id;

    @FXML
    private TableColumn<Tutorial, String> Student_Username;

    @FXML
    private TableColumn<Tutorial, String> StudentModule_Name;

    @FXML
    private TableColumn<Tutorial, String> StudentMeeting_Date;

    @FXML
    private TableColumn<Tutorial, String> StudentModule_Time;

    @FXML
    private TableColumn<Tutorial, String> Student_SpecialComments;

    @FXML
    private TableColumn<Tutorial, String> Student_Status;
    @FXML
    private TableColumn<Tutorial, String> Student_Location;
    @FXML
    private DatePicker Module_Date;

    @FXML
    private TextField Module_Username;

    @FXML
    private TextArea Special_Comments;

    @FXML
    private ComboBox<String> Module_Name;

    @FXML
    private ComboBox<String> Module_Time;

    @FXML
    private ComboBox<String> DesiredLocation;

    @FXML
    private Hyperlink Student_hyperlink1;

    @FXML
    private Hyperlink Student_hyperlink10;

    @FXML
    private Hyperlink Student_hyperlink2;

    @FXML
    private Hyperlink Student_hyperlink3;

    @FXML
    private Hyperlink Student_hyperlink4;

    @FXML
    private Hyperlink Student_hyperlink5;

    @FXML
    private Hyperlink Student_hyperlink6;

    @FXML
    private Hyperlink Student_hyperlink7;

    @FXML
    private Hyperlink Student_hyperlink8;

    @FXML
    private Hyperlink Student_hyperlink9;

    @FXML
    private Button Student_Attemptatest;

    @FXML
    private AnchorPane Student_AttemptatestDashBoared;

    @FXML
    private Button Student_BookaTutorial;

    @FXML
    private AnchorPane Student_BookaTutorialForm;

    @FXML
    private AnchorPane Student_DashBoared;

    @FXML
    private Button Student_Selfstudylessons;

    @FXML
    private Button Student_Notfiying;

    @FXML
    private AnchorPane Student_NotifyingForm;

    @FXML
    private ProgressBar progressBar;

    private final Duration SEARCH_DURATION = Duration.seconds(20);
    private Timeline searchTimeline;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateTableView();
        NameLists();
        ModuleLists();
        Location();

        ViewDetails.setOnAction(event -> {
            Tutorial selectedTutorial = Student_tableView.getSelectionModel().getSelectedItem();

            if (selectedTutorial != null) {
                // Display tutor details in an alert dialog
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Tutor Details");
                alert.setHeaderText(null);
                alert.setContentText("ID: " + selectedTutorial.getId() + "\n"
                        + "Username: " + selectedTutorial.getStudentUsername() + "\n"
                        + "Module Name: " + selectedTutorial.getModuleName() + "\n"
                        + "Module Date: " + selectedTutorial.getModuleDate() + "\n"
                        + "Module Time: " + selectedTutorial.getModuleTime() + "\n"
                        + "Special Comments: " + selectedTutorial.getSpecialComments() + "\n"
                        + "Location: " + selectedTutorial.getLocation() + "\n"
                        + "Status: " + selectedTutorial.getStatus());
                alert.showAndWait();
            } else {
                // If no tutorial is selected, show a warning
                showAlert("No Tutorial Selected", "Please select a tutorial to view details.", Alert.AlertType.WARNING);
            }

        });

        // Assign the handleHyperlinkAction method to each hyperlink's setOnAction event handler
        Student_hyperlink1.setOnAction(event -> openWebsite("https://www.youtube.com/@freecodecamp"));
        Student_hyperlink2.setOnAction(event -> openWebsite("https://www.youtube.com/@BroCodez"));
        Student_hyperlink3.setOnAction(event -> openWebsite("https://www.youtube.com/@programmingwithmosh"));
        Student_hyperlink4.setOnAction(event -> openWebsite("https://www.youtube.com/@codecademy"));
        Student_hyperlink5.setOnAction(event -> openWebsite("https://www.youtube.com/@ProgrammingTutorials1M"));
        Student_hyperlink6.setOnAction(event -> openWebsite("https://quizlet.com/gb"));
        Student_hyperlink7.setOnAction(event -> openWebsite("https://www.coursehero.com/"));
        Student_hyperlink8.setOnAction(event -> openWebsite("https://www.codecademy.com/"));
        Student_hyperlink9.setOnAction(event -> openWebsite("https://www.khanacademy.org/"));
        Student_hyperlink10.setOnAction(event -> openWebsite("https://kahoot.it/"));

    }

    private void populateTableView() {
        Student_id.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        Student_Username.setCellValueFactory(cellData -> cellData.getValue().studentUsernameProperty());
        StudentModule_Name.setCellValueFactory(cellData -> cellData.getValue().moduleNameProperty());
        StudentMeeting_Date.setCellValueFactory(cellData -> cellData.getValue().moduleDateProperty().asString());
        StudentModule_Time.setCellValueFactory(cellData -> cellData.getValue().moduleTimeProperty().asString());
        Student_SpecialComments.setCellValueFactory(cellData -> cellData.getValue().specialCommentsProperty());
        Student_Status.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        Student_Location.setCellValueFactory(cellData -> cellData.getValue().locationProperty());

        try {
            ObservableList<Tutorial> tutorials = FXCollections.observableArrayList(TutorialDAO.getAllTutorials());
            Student_tableView.setItems(tutorials);
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

    public void NameLists() {
        List<String> listR = new ArrayList<>();

        for (String data : ListData.Student) {
            listR.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listR);
        Module_Name.setItems(listData);
    }

    public void ModuleLists() {
        List<String> listR = new ArrayList<>();

        for (String data : ListData.ModuleTimes) {
            listR.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listR);
        Module_Time.setItems(listData);
    }

    public void Location() {
        List<String> listR = new ArrayList<>();

        for (String data : ListData.DesiredLocations) {
            listR.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listR);
        DesiredLocation.setItems(listData);
    }

    private AlertMessage alert = new AlertMessage();

    @FXML
    private void bookTutorial(ActionEvent event) {
        String username = Module_Username.getText();
        // Validate student number format for username
        if (!username.matches("\\d{9}@spu\\.ac\\.za")) {
            alert.errorMessage("Invalid username format. Please enter your 9 uniquely identifiable valid student number followed by '@spu.ac.za'");
            return;
        }

        // Check if any mandatory field is empty
        if (username.isEmpty() || Module_Name.getValue() == null || Module_Date.getValue() == null || Module_Time.getValue() == null) {
            alert.errorMessage("Please fill in all fields as they are mandatory");
            return;
        }

        // All fields are filled, proceed to insert data into the database
        String insertData = "INSERT INTO student_module_schedule (Student_Username, Module_Name, Module_Date, Module_Time, Meeting_Date, Special_Comments, Location) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection connect = Database.connectDB();
        try {
            PreparedStatement prepare = connect.prepareStatement(insertData);
            prepare.setString(1, username);
            prepare.setString(2, Module_Name.getValue());
            prepare.setDate(3, java.sql.Date.valueOf(Module_Date.getValue()));

            // Parse Module_Time string to LocalTime
            LocalTime moduleTime = LocalTime.parse(Module_Time.getValue());

            // Convert LocalTime to java.sql.Time
            prepare.setTime(4, java.sql.Time.valueOf(moduleTime));

            // Assuming Meeting_Date is the current date when booking is made
            prepare.setDate(5, java.sql.Date.valueOf(java.time.LocalDate.now()));
            prepare.setString(6, Special_Comments.getText());
            prepare.setString(7, DesiredLocation.getValue()); // Updated to use Student_Location

            // Execute the query to insert data
            int result = prepare.executeUpdate();
            if (result > 0) {
                // Data inserted successfully
                alert.successMessage("Tutorial booked successfully \n system searching"
                        + " TutorList to identify a tutor for the module specified wait patiently please \n"
                        + "");

                // Show the progress bar and start searching for a tutor
                progressBar.setVisible(true);
                progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);

                // Set up timeline to hide progress bar after 20 seconds
                setupSearchTimeline();

            } else {
                // Failed to insert data
                alert.errorMessage("Failed to book a Tutorial");
            }
        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace();
            alert.errorMessage("An error occurred while booking a Tutorial");
        } finally {
            try {
                // Close the database connection
                connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

// Method to set up timeline to hide progress bar after 40 seconds
    private void setupSearchTimeline() {
        searchTimeline = new Timeline(new KeyFrame(SEARCH_DURATION, e -> {
            // Hide progress bar after 40 seconds
            progressBar.setVisible(false);
            progressBar.setProgress(0);
            alert.successMessage("Tutor Found Student Request sent For approval wait patiently please");
        }));
        searchTimeline.setCycleCount(1);
        searchTimeline.play();
    }

    public void StudentSwitchForms() {
        // Method implementation...
        // Initialize button event handlers
        Student_Selfstudylessons.setOnAction(event -> {
            // Activate Student_Selfstudylessons button and its corresponding AnchorPane
            Student_Selfstudylessons.setDisable(true);
            Student_DashBoared.setVisible(true);

            // Deactivate other buttons and their corresponding AnchorPanes
            Student_Attemptatest.setDisable(false);
            Student_AttemptatestDashBoared.setVisible(false);
            Student_BookaTutorial.setDisable(false);
            Student_BookaTutorialForm.setVisible(false);
            Student_Notfiying.setDisable(false);
            Student_NotifyingForm.setVisible(false);
        });

        Student_Attemptatest.setOnAction(event -> {
            // Activate Student_Attemptatest button and its corresponding AnchorPane
            Student_Attemptatest.setDisable(true);
            Student_AttemptatestDashBoared.setVisible(true);

            // Deactivate other buttons and their corresponding AnchorPanes
            Student_Selfstudylessons.setDisable(false);
            Student_DashBoared.setVisible(false);
            Student_BookaTutorial.setDisable(false);
            Student_BookaTutorialForm.setVisible(false);
            Student_Notfiying.setDisable(false);
            Student_NotifyingForm.setVisible(false);
        });

        Student_BookaTutorial.setOnAction(event -> {
            // Activate Student_BookaTutorial button and its corresponding AnchorPane
            Student_BookaTutorial.setDisable(true);
            Student_BookaTutorialForm.setVisible(true);

            // Deactivate other buttons and their corresponding AnchorPanes
            Student_Selfstudylessons.setDisable(false);
            Student_DashBoared.setVisible(false);
            Student_Attemptatest.setDisable(false);
            Student_AttemptatestDashBoared.setVisible(false);
            Student_Notfiying.setDisable(false);
            Student_NotifyingForm.setVisible(false);
        });

        Student_Notfiying.setOnAction(event -> {
            // Activate Student_Notfiying button and its corresponding AnchorPane
            Student_Notfiying.setDisable(true);
            Student_NotifyingForm.setVisible(true);

            // Deactivate other buttons and their corresponding AnchorPanes
            Student_Selfstudylessons.setDisable(false);
            Student_DashBoared.setVisible(false);
            Student_Attemptatest.setDisable(false);
            Student_AttemptatestDashBoared.setVisible(false);
            Student_BookaTutorial.setDisable(false);
            Student_BookaTutorialForm.setVisible(false);
        });
    }

    @FXML
    private void openWebsite(String url) {
        // Method implementation...
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (java.io.IOException e) {
            System.out.println("Error opening website: " + e.getMessage());
        }

    }

    @FXML
    private void handleHyperlinkAction(ActionEvent event) {
        Hyperlink clickedHyperlink = (Hyperlink) event.getSource();
        String url = null;

        // Determine which hyperlink was clicked and set the URL accordingly
        if (clickedHyperlink == Student_hyperlink1) {
            url = "http://google.com";
        } else if (clickedHyperlink == Student_hyperlink2) {
            url = "http://www.example.com/2";
        } else if (clickedHyperlink == Student_hyperlink3) {
            url = "http://www.example.com/3";
        } else if (clickedHyperlink == Student_hyperlink4) {
            url = "http://www.example.com/4";
        } else if (clickedHyperlink == Student_hyperlink5) {
            url = "http://www.example.com/5";
        } else if (clickedHyperlink == Student_hyperlink6) {
            url = "http://www.example.com/6";
        } else if (clickedHyperlink == Student_hyperlink7) {
            url = "http://www.example.com/7";
        } else if (clickedHyperlink == Student_hyperlink8) {
            url = "http://www.example.com/8";
        } else if (clickedHyperlink == Student_hyperlink9) {
            url = "http://www.example.com/9";
        } else if (clickedHyperlink == Student_hyperlink10) {
            url = "http://www.example.com/10";
        }

        // Open the website
        if (url != null) {
            openWebsite(url);
        }
    }

}
