package itutor.booking.system;

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
import javafx.beans.property.SimpleDoubleProperty;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdministratorAdminForm implements Initializable {

    @FXML
    private TableView<Claim> ClaimFormTableView;
    @FXML
    private TableColumn<Claim, String> fromtimeClaim;
    @FXML
    private TableColumn<Claim, String> toTimeClaim;
    @FXML
    private TableColumn<Claim, String> usernameClaim;
    @FXML
    private TableColumn<Claim, Double> totalhoursworkedforthemonth;
    @FXML
    private TableColumn<Claim, Double> valueofclaim;
    @FXML
    private AnchorPane greet_username;
    @FXML
    private TableColumn<Claim, Integer> idClaim;
    @FXML
    private Button paymentBtn;
    @FXML
    private AnchorPane DisplayClaim;
    @FXML
    private Button ViewClaimsbtn;

    @FXML
    private Button CloseClaimTable;

    private ObservableList<Claim> claimList = FXCollections.observableArrayList();

    ///////////////////////////////////////////////////////////
    @FXML
    private AreaChart<String, Number> BudgetOverview;
    @FXML
    private AreaChart<String, Number> DataofStudents;
    @FXML
    private AreaChart<String, Number> DataofTutor;

    @FXML
    private Label TotalNumberSudents;
    @FXML
    private Label TotalNumberTutors;

    @FXML
    private TableView<Tutorial> Administrator_tableView;
    @FXML
    private TableColumn<Tutorial, Integer> Administrator_id;
    @FXML
    private TableColumn<Tutorial, String> Administrator_StudentName;
    @FXML
    private TableColumn<Tutorial, String> Administrator_ModuleName;
    @FXML
    private TableColumn<Tutorial, LocalDate> Administrator_MeetingDate;
    @FXML
    private TableColumn<Tutorial, LocalTime> Administrator_ModuleTime;
    @FXML
    private TableColumn<Tutorial, String> Administrator_SpecialComments;
    @FXML
    private TableColumn<Tutorial, String> Administrator_Location;
    @FXML
    private TableColumn<Tutorial, String> Administrator_Status;

    @FXML
    private TableView<Tutorial> EmpData;
    @FXML
    private TableColumn<Tutorial, Integer> EmpcolId;
    @FXML
    private TableColumn<Tutorial, String> EmpcolFullName;
    @FXML
    private TableColumn<Tutorial, String> EmpcolPayment;
    @FXML
    private TableColumn<Tutorial, String> EmpcolStatuspayment;
    @FXML
    private TableColumn<Tutorial, String> EmpcolTutorUsername;
    @FXML
    private TableColumn<Tutorial, String> EmpcolYearOfEmployment;

    @FXML
    private TextField EmpStudentNumber;
    @FXML
    private DatePicker EmpYearOfEmployment;
    @FXML
    private TextField EmpFullName;
    @FXML
    private TextField EmpPaymentSection;
    @FXML
    private ComboBox<String> EmpStatus;

    @FXML
    private Button ViewDataRejection;
    @FXML
    private Button dashBoardbtn;
    @FXML
    private Button Notifyingbtn;
    @FXML
    private Button Paymentbtn;
    @FXML
    private Button Salarybtn;

    @FXML
    private AnchorPane AdministratorForm_Dashboard;
    @FXML
    private AnchorPane AdministratorForm_Notifying;
    @FXML
    private AnchorPane AdministratorForm_Payments;
    @FXML
    private AnchorPane AdministratorForm_Salaries;

    @FXML
    private TextField BudgetedAmount;
    @FXML
    private Label UpdatedAmount;

    private AlertMessage alert = new AlertMessage();

    // Track the current budgeted amount
    private double currentBudgetedAmount = 0.0;

    // Declare variables to hold the chart data
    private XYChart.Series<String, Number> budgetOverviewSeries;
    private XYChart.Series<String, Number> studentsDataSeries;
    private XYChart.Series<String, Number> tutorsDataSeries;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the table view and populate it with data
        initializeTableView();
        initializeEmpTableView(); // Initialize the EmpData TableView
        StatusList();

        // Initialize the chart data
        initializeChartData();
        updateChartData();

        // Set up the event handler for the ViewDataRejection button
        ViewDataRejection.setOnAction(event -> {
            Tutorial selectedTutorial = Administrator_tableView.getSelectionModel().getSelectedItem();
            if (selectedTutorial != null) {
                // Display tutorial details in an alert dialog
                showAlert("Tutorial Details", formatTutorialDetails(selectedTutorial), Alert.AlertType.INFORMATION);
            } else {
                // If no tutorial is selected, show a warning
                showAlert("No Tutorial Selected", "Please select a tutorial to view details.", Alert.AlertType.WARNING);
            }
        });

        // Update the TotalNumberStudents and TotalNumberTutors labels
        updateStudentsAndTutorsCount();
        // Load the current budgeted amount
        loadCurrentBudgetedAmount();

        // Initialize table columns
        idClaim.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        usernameClaim.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        fromtimeClaim.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFromTime()));
        toTimeClaim.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getToTime()));
        totalhoursworkedforthemonth.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTotalHoursWorked()).asObject());
        valueofclaim.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getValueOfClaim()).asObject());

        // Load claims from the database
        loadClaimsFromDatabase();
        ClaimFormTableView.setItems(claimList);

        // Add event handler for the CloseClaimTable button
        CloseClaimTable.setOnAction(event -> {
            hideClaimTable();
        });
    }

    private void hideClaimTable() {
        DisplayClaim.setVisible(false);
    }

    private void loadCurrentBudgetedAmount() {
        try {
            // Fetch the latest budgeted amount from the database
            double latestBudgetedAmount = getCurrentBudgetedAmount();
            currentBudgetedAmount = latestBudgetedAmount;

            // Update the UpdatedAmount label
            UpdatedAmount.setText(String.format("R%.2f", currentBudgetedAmount));
        } catch (SQLException e) {
            e.printStackTrace();
            alert.errorMessage("Failed to fetch the current budgeted amount from the database.");
        }
    }

    private double getCurrentBudgetedAmount() throws SQLException {
        // Your SQL query to fetch the latest budgeted amount from the database
        String query = "SELECT MAX(BudgetID), BudgetAmount FROM budgetedamount";
        Connection connection = Database.connectDB();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        double latestBudgetedAmount = 0.0;
        if (resultSet.next()) {
            latestBudgetedAmount = resultSet.getDouble("BudgetAmount");
        }

        statement.close();
        connection.close();
        return latestBudgetedAmount;
    }

    private void StatusList() {
        List<String> listR = new ArrayList<>();
        listR.add("Payment successful");
        listR.add("Payment unsuccessful");
        ObservableList<String> listData = FXCollections.observableArrayList(listR);
        EmpStatus.setItems(listData);
    }

    private void initializeChartData() {
        // Initialize the data series
        budgetOverviewSeries = new XYChart.Series<>();
        budgetOverviewSeries.setName("Budget Overview");

        studentsDataSeries = new XYChart.Series<>();
        studentsDataSeries.setName("Data of Students");

        tutorsDataSeries = new XYChart.Series<>();
        tutorsDataSeries.setName("Data of Tutors");
    }

    private void updateChartData() {
        try {
            // Fetch budget data from the database
            List<BudgetData> budgetData = fetchBudgetData();
            updateBudgetOverviewChart(budgetData);

            // Fetch student data from the database
            List<StudentData> studentData = fetchStudentData();
            updateStudentsDataChart(studentData);

            // Fetch tutor data from the database
            List<TutorData> tutorData = fetchTutorData();
            updateTutorsDataChart(tutorData);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException appropriately, such as showing an error message
            alert.errorMessage("Failed to fetch data from the database.");
        }
    }

    private List<BudgetData> fetchBudgetData() throws SQLException {
        // Your SQL query to fetch budget data from the database
        String query = "SELECT BudgetAmount, BudgetID FROM budgetedamount";
        Connection connection = Database.connectDB();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        List<BudgetData> budgetData = new ArrayList<>();
        while (resultSet.next()) {
            double amount = resultSet.getDouble("BudgetAmount");
            int budgetID = resultSet.getInt("BudgetID");
            budgetData.add(new BudgetData(String.valueOf(budgetID), amount));
        }

        statement.close();
        connection.close();
        return budgetData;
    }

    private List<StudentData> fetchStudentData() throws SQLException {
        // Your SQL query to fetch student data from the database
        String query = "SELECT COUNT(*) as student_count, DATE_FORMAT(Module_Date, '%Y-%m') as month FROM student_module_schedule GROUP BY month";
        Connection connection = Database.connectDB();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        List<StudentData> studentData = new ArrayList<>();
        while (resultSet.next()) {
            int studentCount = resultSet.getInt("student_count");
            String month = resultSet.getString("month");
            studentData.add(new StudentData(month, studentCount));
        }

        statement.close();
        connection.close();
        return studentData;
    }

    private List<TutorData> fetchTutorData() throws SQLException {
        // Your SQL query to fetch tutor data from the database
        String query = "SELECT COUNT(*) as tutor_count, DATE_FORMAT(Module_Date, '%Y-%m') as month FROM student_module_schedule GROUP BY month";
        Connection connection = Database.connectDB();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        List<TutorData> tutorData = new ArrayList<>();
        while (resultSet.next()) {
            int tutorCount = resultSet.getInt("tutor_count");
            String month = resultSet.getString("month");
            tutorData.add(new TutorData(month, tutorCount));
        }

        statement.close();
        connection.close();
        return tutorData;
    }

    private void updateBudgetOverviewChart(List<BudgetData> budgetData) {
        budgetOverviewSeries.getData().clear();
        for (BudgetData data : budgetData) {
            budgetOverviewSeries.getData().add(new XYChart.Data<>(data.getMonth(), data.getAmount()));
        }
        BudgetOverview.getData().clear();
        BudgetOverview.getData().add(budgetOverviewSeries);
    }

    private void updateStudentsDataChart(List<StudentData> studentData) {
        studentsDataSeries.getData().clear();
        for (StudentData data : studentData) {
            studentsDataSeries.getData().add(new XYChart.Data<>(data.getMonth(), data.getStudentCount()));
        }
        DataofStudents.getData().clear();
        DataofStudents.getData().add(studentsDataSeries);
    }

    private void updateTutorsDataChart(List<TutorData> tutorData) {
        tutorsDataSeries.getData().clear();
        for (TutorData data : tutorData) {
            tutorsDataSeries.getData().add(new XYChart.Data<>(data.getMonth(), data.getTutorCount()));
        }
        DataofTutor.getData().clear();
        DataofTutor.getData().add(tutorsDataSeries);
    }

    private void updateStudentsAndTutorsCount() {
        try {
            int totalStudents = getTotalStudents();
            int totalTutors = getTotalTutors();

            TotalNumberSudents.setText(String.valueOf(totalStudents));
            TotalNumberTutors.setText(String.valueOf(totalTutors));
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException appropriately, such as showing an error message
            alert.errorMessage("Failed to fetch student and tutor data from the database.");
        }
    }

    private int getTotalStudents() throws SQLException {
        Connection connection = Database.connectDB();
        String query = "SELECT COUNT(*) as total_students FROM student";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int totalStudents = resultSet.getInt("total_students");
        statement.close();
        connection.close();
        return totalStudents;
    }

    private int getTotalTutors() throws SQLException {
        Connection connection = Database.connectDB();
        String query = "SELECT COUNT(*) as total_tutors FROM tutor";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int totalTutors = resultSet.getInt("total_tutors");
        statement.close();
        connection.close();
        return totalTutors;
    }

    private void initializeTableView() {
        // Set up the cell value factories for table columns
        Administrator_id.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        Administrator_StudentName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudentUsername()));
        Administrator_ModuleName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModuleName()));
        Administrator_MeetingDate.setCellValueFactory(cellData -> cellData.getValue().moduleDateProperty());
        Administrator_ModuleTime.setCellValueFactory(cellData -> cellData.getValue().moduleTimeProperty());
        Administrator_SpecialComments.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSpecialComments()));
        Administrator_Location.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocation()));
        Administrator_Status.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));

        // Load data into the table view
        loadData();

    }

    private void loadClaimsFromDatabase() {
        // Clear the existing claims in the claimList
        claimList.clear();

        // Fetch claims from the database and add them to the claimList
        try (Connection connection = Database.connectDB()) {
            String selectQuery = "SELECT * FROM UserClaims";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String fromTime = resultSet.getString("from_time");
                String toTime = resultSet.getString("to_time");
                double ratePerUnit = resultSet.getDouble("rate_per_unit");
                double totalHoursWorked = resultSet.getDouble("total_hours_worked_for_the_month");
                double valueOfClaim = resultSet.getDouble("value_of_claim");

                Claim claim = new Claim(id, username, fromTime, toTime, ratePerUnit, totalHoursWorked, valueOfClaim);
                claimList.add(claim);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void viewClaims() {
        // Show the DisplayClaim AnchorPane when the ViewClaimsbtn is clicked
        DisplayClaim.setVisible(true);

        // Load the claim data into the ClaimFormTableView
        loadClaimsFromDatabase();
        ClaimFormTableView.setItems(claimList);
    }

    private void loadData() {
        try {
            ObservableList<Tutorial> tutorials = FXCollections.observableArrayList(Tutorial.TutorialDAO.getAllTutorials());
            Administrator_tableView.setItems(tutorials);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException appropriately, such as showing an error message
            alert.errorMessage("Failed to fetch tutorial data from the database.");
        }
    }

    private void initializeEmpTableView() {
        // Set up the cell value factories for table columns
        if (EmpcolId != null) {
            EmpcolId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        }
        if (EmpcolFullName != null) {
            EmpcolFullName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullName()));
        }
        if (EmpcolPayment != null) {
            EmpcolPayment.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPaymentSection()));
        }
        if (EmpcolStatuspayment != null) {
            EmpcolStatuspayment.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmpStatus()));
        }
        if (EmpcolTutorUsername != null) {
            EmpcolTutorUsername.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTutorUsername()));
        }
        if (EmpcolYearOfEmployment != null) {
            EmpcolYearOfEmployment.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getYearOfEmployment()));
        }

        // Load data into the table view
        loadEmpData();
    }

    private void loadEmpData() {
        try {
            ObservableList<Tutorial> paymentData = FXCollections.observableArrayList(Tutorial.TutorialDAO.getPaymentData());
            EmpData.setItems(paymentData);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException appropriately, such as showing an error message
            alert.errorMessage("Failed to fetch payment data from the database.");
        }
    }

    @FXML
    private void handleEmpUpdateData(ActionEvent event) {
        try {
            // Fetch data from the database
            ObservableList<Tutorial> paymentData = FXCollections.observableArrayList(Tutorial.TutorialDAO.getPaymentData());

            // Populate the TableView with the fetched data
            EmpData.setItems(paymentData);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException appropriately, such as showing an error message
            alert.errorMessage("Failed to fetch payment data from the database.");
        }
    }

    private String formatTutorialDetails(Tutorial tutorial) {
        String studentPhoneNumber = null;
        String tutorPhoneNumber = null;
        try {
            // Retrieve phone numbers from the database based on the student username
            studentPhoneNumber = Tutorial.TutorialDAO.getStudentPhoneNumber(tutorial.getStudentUsername());

            // Retrieve tutor's phone number from the database based on the tutor who approved/rejected the tutorial
            tutorPhoneNumber = Tutorial.TutorialDAO.generateSouthAfricanPhoneNumber();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Format tutor's phone number to include the South African country code if it's not null
        if (tutorPhoneNumber != null) {
            tutorPhoneNumber = "+27" + tutorPhoneNumber.substring(1); // Assuming tutor's phone number already starts with '0'
        }

        return "ID: " + tutorial.getId() + "\n"
                + "Username: " + tutorial.getStudentUsername() + "\n"
                + "Module Name: " + tutorial.getModuleName() + "\n"
                + "Module Date: " + tutorial.getModuleDate() + "\n"
                + "Module Time: " + tutorial.getModuleTime() + "\n"
                + "Special Comments: " + tutorial.getSpecialComments() + "\n"
                + "Location: " + tutorial.getLocation() + "\n"
                + "Status: " + tutorial.getStatus() + "\n"
                + "Student Phone Number: " + studentPhoneNumber + "\n"
                + "Tutor Phone Number: " + tutorPhoneNumber;
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleSalaryBtn(ActionEvent event) {
        // Get user inputs
        String studentNumber = EmpStudentNumber.getText();
        LocalDate yearOfEmployment = EmpYearOfEmployment.getValue();
        String fullName = EmpFullName.getText();
        String paymentSection = EmpPaymentSection.getText();
        String status = EmpStatus.getValue(); // Assuming EmpStatus is a ComboBox<String>

        // Validate user inputs
        if (studentNumber.isEmpty() || yearOfEmployment == null || fullName.isEmpty() || paymentSection.isEmpty() || status == null) {
            // Show an error message if any of the fields are empty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }

        // Validate studentNumber format
        if (!studentNumber.matches("\\d{9}@spu\\.ac\\.za")) {
            alert.errorMessage("Invalid username format. Please enter your 9 uniquely identifiable valid student number followed by '@spu.ac.za");
            return;
        }

        // Validate paymentSection format
        if (!paymentSection.matches("^R\\d*")) {
            alert.errorMessage("Invalid payment section format. Please enter a section starting with 'R' followed by any amount.");
            return;
        }

        // Check if the student number already exists in the database
        if (isStudentNumberExists(studentNumber)) {
            alert.errorMessage("Student number already exists. Please enter a unique student number.");
            return;
        }

        // Check if the full name already exists in the database
        if (isFullNameExists(fullName)) {
            alert.errorMessage("Full name already exists. Please enter a unique full name.");
            return;
        }

        try {
            // Your SQL insert statement goes here
            String insertQuery = "INSERT INTO Employee (EmpStudentNumber, EmpYearOfEmployment, EmpFullName, EmpPaymentSection, EmpStatus) VALUES (?, ?, ?, ?, ?)";
            Connection connection = Database.connectDB();
            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.setString(1, studentNumber);
            statement.setDate(2, java.sql.Date.valueOf(yearOfEmployment)); // Convert LocalDate to java.sql.Date
            statement.setString(3, fullName);
            statement.setString(4, paymentSection);
            statement.setString(5, status);
            statement.executeUpdate();
            statement.close();
            connection.close();

            // Update TableView
            loadData(); // Reload data to refresh TableView

            // Update the budgeted amount
            updateBudgetedAmount(paymentSection);

            // Display a success message
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Transaction successful!");
            successAlert.showAndWait();
        } catch (NumberFormatException e) {
            // Handle NumberFormatException (e.g., show an error message for invalid input)
            e.printStackTrace();
        } catch (SQLException e) {
            // Handle SQLException (e.g., show an error message for database error)
            e.printStackTrace();
        }
    }

    private void updateBudgetedAmount(String paymentSection) {
        try {
            // Extract the payment amount from the paymentSection
            double paymentAmount = Double.parseDouble(paymentSection.substring(1));

            // Subtract the payment amount from the current budgeted amount
            currentBudgetedAmount -= paymentAmount;

            // Update the budgeted amount in the database
            updateBudgetedAmountInDatabase(currentBudgetedAmount);

            // Update the UpdatedAmount label
            UpdatedAmount.setText(String.format("R%.2f", currentBudgetedAmount));
        } catch (NumberFormatException e) {
            // Handle invalid payment section format
            alert.errorMessage("Invalid payment section format. Please enter a valid payment section.");
        } catch (SQLException e) {
            e.printStackTrace();
            alert.errorMessage("Failed to update the budgeted amount in the database.");
        }
    }

    private void updateBudgetedAmountInDatabase(double updatedBudgetedAmount) throws SQLException {
        // Your SQL update statement to update the latest budgeted amount in the database
        String updateQuery = "UPDATE budgetedamount SET BudgetAmount = ? WHERE BudgetID = (SELECT MAX(BudgetID) FROM budgetedamount)";
        Connection connection = Database.connectDB();
        PreparedStatement statement = connection.prepareStatement(updateQuery);
        statement.setDouble(1, updatedBudgetedAmount);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    @FXML
    private void handleAddButtonClick(ActionEvent event) {
        // Get the budgeted amount from the text field
        String budgetedAmountStr = BudgetedAmount.getText();

        // Validate if the text field is not empty
        if (budgetedAmountStr.isEmpty()) {
            alert.errorMessage("Please enter a budgeted amount.");
            return;
        }

        try {
            // Convert the budgeted amount to a double
            double budgetedAmount = Double.parseDouble(budgetedAmountStr);

            // Insert the budgeted amount into the database and get the inserted value
            double insertedBudgetedAmount = insertBudgetedAmount(budgetedAmount);

            // Update the current budgeted amount
            currentBudgetedAmount = insertedBudgetedAmount;

            // Update the UpdatedAmount label
            UpdatedAmount.setText(String.format("R%.2f", currentBudgetedAmount));

            // Check the budgeted amount and display the alert if necessary
            checkBudgetAmount(insertedBudgetedAmount);

            // Display a success message
            alert.successMessage("Budgeted amount added successfully.");
        } catch (NumberFormatException e) {
            alert.errorMessage("Invalid budgeted amount. Please enter a valid number.");
        } catch (SQLException e) {
            e.printStackTrace();
            alert.errorMessage("Failed to add the budgeted amount to the database.");
        }
    }

    private double insertBudgetedAmount(double budgetedAmount) throws SQLException {
        // Generate a unique primary key value
        int nextBudgetID = getNextBudgetID();

        // Your SQL insert statement
        String insertQuery = "INSERT INTO budgetedamount (BudgetID, BudgetAmount) VALUES (?, ?)";
        Connection connection = Database.connectDB();
        PreparedStatement statement = connection.prepareStatement(insertQuery);
        statement.setInt(1, nextBudgetID);
        statement.setDouble(2, budgetedAmount);
        statement.executeUpdate();
        statement.close();
        connection.close();

        // Return the inserted budgeted amount
        return budgetedAmount;
    }

    private int getNextBudgetID() throws SQLException {
        // Query the database to find the next available primary key value
        String query = "SELECT MAX(BudgetID) + 1 AS nextBudgetID FROM budgetedamount";
        Connection connection = Database.connectDB();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        int nextBudgetID = 1; // Default to 1 if the table is empty
        if (resultSet.next()) {
            nextBudgetID = resultSet.getInt("nextBudgetID");
        }
        statement.close();
        connection.close();
        return nextBudgetID;
    }

    private boolean isStudentNumberExists(String studentNumber) {
        try {
            Connection connection = Database.connectDB();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Employee WHERE EmpStudentNumber = ?");
            statement.setString(1, studentNumber);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Returns true if student number already exists in the database
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Error occurred, return false as safety measure
        }
    }

    private boolean isFullNameExists(String fullName) {
        try {
            Connection connection = Database.connectDB();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Employee WHERE EmpFullName = ?");
            statement.setString(1, fullName);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Returns true if full name already exists in the database
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Error occurred, return false as safety measure
        }
    }

    private void checkBudgetAmount(double budgetedAmount) {
        if (budgetedAmount < 100000) {
            alert.warningMessage("The budgeted amount is below R100,000. Please consider increasing the budget.");
        } else if (budgetedAmount > 200000) {
            alert.warningMessage("The budgeted amount is above R200,000. Please verify if this amount is appropriate.");
        }
    }

    @FXML
    public void switchForms(ActionEvent event) {
        // Hide all AnchorPanes first
        hideAllPanes();

        if (event.getSource() == dashBoardbtn) {
            // Activate Dashboard button and its corresponding AnchorPane
            dashBoardbtn.setDisable(true);
            if (AdministratorForm_Dashboard != null) {
                AdministratorForm_Dashboard.setVisible(true);
            }
        } else if (event.getSource() == Notifyingbtn) {
            // Activate Notifying button and its corresponding AnchorPane
            Notifyingbtn.setDisable(true);
            if (AdministratorForm_Notifying != null) {
                AdministratorForm_Notifying.setVisible(true);
            }
        } else if (event.getSource() == Paymentbtn) {
            // Activate Payment button and its corresponding AnchorPane
            Paymentbtn.setDisable(true);
            if (AdministratorForm_Payments != null) {
                AdministratorForm_Payments.setVisible(true);
            }
        } else if (event.getSource() == Salarybtn) {
            // Activate Salary button and its corresponding AnchorPane
            Salarybtn.setDisable(true);
            if (AdministratorForm_Salaries != null) {
                AdministratorForm_Salaries.setVisible(true);
            }
        }

        // Activate the corresponding button
        if (event.getSource() == dashBoardbtn) {
            dashBoardbtn.setDisable(true);
            Notifyingbtn.setDisable(false);
            Paymentbtn.setDisable(false);
            Salarybtn.setDisable(false);
        } else if (event.getSource() == Notifyingbtn) {
            dashBoardbtn.setDisable(false);
            Notifyingbtn.setDisable(true);
            Paymentbtn.setDisable(false);
            Salarybtn.setDisable(false);
        } else if (event.getSource() == Paymentbtn) {
            dashBoardbtn.setDisable(false);
            Notifyingbtn.setDisable(false);
            Paymentbtn.setDisable(true);
            Salarybtn.setDisable(false);
        } else if (event.getSource() == Salarybtn) {
            dashBoardbtn.setDisable(false);
            Notifyingbtn.setDisable(false);
            Paymentbtn.setDisable(false);
            Salarybtn.setDisable(true);
        }
    }

    private void hideAllPanes() {
        if (AdministratorForm_Dashboard != null) {
            AdministratorForm_Dashboard.setVisible(false);
        }
        if (AdministratorForm_Notifying != null) {
            AdministratorForm_Notifying.setVisible(false);
        }
        if (AdministratorForm_Payments != null) {
            AdministratorForm_Payments.setVisible(false);
        }
        if (AdministratorForm_Salaries != null) {
            AdministratorForm_Salaries.setVisible(false);
        }
    }

    @FXML
    private void handleClearButtonClick(ActionEvent event) {
        // Clear the text field
        BudgetedAmount.clear();

        // Update the UpdatedAmount label to an empty string
        UpdatedAmount.setText("");
    }
}

class BudgetData {

    private String month;
    private double amount;

    public BudgetData(String month, double amount) {
        this.month = month;
        this.amount = amount;
    }

    public String getMonth() {
        return month;
    }

    public double getAmount() {
        return amount;
    }
}

class StudentData {

    private String month;
    private int studentCount;

    public StudentData(String month, int studentCount) {
        this.month = month;
        this.studentCount = studentCount;
    }

    public String getMonth() {
        return month;
    }

    public int getStudentCount() {
        return studentCount;
    }
}

class TutorData {

    private String month;
    private int tutorCount;

    public TutorData(String month, int tutorCount) {
        this.month = month;
        this.tutorCount = tutorCount;
    }

    public String getMonth() {
        return month;
    }

    public int getTutorCount() {
        return tutorCount;
    }
}
