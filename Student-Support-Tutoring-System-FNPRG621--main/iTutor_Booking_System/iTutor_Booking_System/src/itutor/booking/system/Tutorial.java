/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itutor.booking.system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.beans.property.*;

public class Tutorial {

    private final IntegerProperty id;
    private final StringProperty studentUsername;
    private final StringProperty moduleName;
    private final ObjectProperty<LocalDate> moduleDate;
    private final ObjectProperty<LocalTime> moduleTime;
    private final StringProperty specialComments;
    private final StringProperty location;
    private final StringProperty status;
    private final StringProperty fullName;
    private final StringProperty paymentSection;
    private final StringProperty tutorUsername;
    private final StringProperty yearOfEmployment;
    private final StringProperty empStatus;

    public Tutorial(int id, String studentUsername, String moduleName, LocalDate moduleDate, LocalTime moduleTime, String specialComments, String location, String status, String fullName, String paymentSection, String tutorUsername, String yearOfEmployment, String empStatus) {
        this.id = new SimpleIntegerProperty(id);
        this.studentUsername = new SimpleStringProperty(studentUsername);
        this.moduleName = new SimpleStringProperty(moduleName);
        this.moduleDate = new SimpleObjectProperty<>(moduleDate);
        this.moduleTime = new SimpleObjectProperty<>(moduleTime);
        this.specialComments = new SimpleStringProperty(specialComments);
        this.location = new SimpleStringProperty(location);
        this.status = new SimpleStringProperty(status);
        this.fullName = new SimpleStringProperty(fullName);
        this.paymentSection = new SimpleStringProperty(paymentSection);
        this.tutorUsername = new SimpleStringProperty(tutorUsername);
        this.yearOfEmployment = new SimpleStringProperty(yearOfEmployment);
        this.empStatus = new SimpleStringProperty(empStatus);
    }

    public int getId() {
        return id.get();
    }

    public String getStudentUsername() {
        return studentUsername.get();
    }

    public String getModuleName() {
        return moduleName.get();
    }

    public LocalDate getModuleDate() {
        return moduleDate.get();
    }

    public LocalTime getModuleTime() {
        return moduleTime.get();
    }

    public String getSpecialComments() {
        return specialComments.get();
    }

    public String getLocation() {
        return location.get();
    }

    public String getStatus() {
        return status.get();
    }

    public String getFullName() {
        return fullName.get();
    }

    public String getPaymentSection() {
        return paymentSection.get();
    }

    public String getTutorUsername() {
        return tutorUsername.get();
    }

    public String getYearOfEmployment() {
        return yearOfEmployment.get();
    }

    public String getEmpStatus() {
        return empStatus.get();
    }

    public void setEmpStatus(String empStatus) {
        this.empStatus.set(empStatus);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty studentUsernameProperty() {
        return studentUsername;
    }

    public StringProperty moduleNameProperty() {
        return moduleName;
    }

    public ObjectProperty<LocalDate> moduleDateProperty() {
        return moduleDate;
    }

    public ObjectProperty<LocalTime> moduleTimeProperty() {
        return moduleTime;
    }

    public StringProperty specialCommentsProperty() {
        return specialComments;
    }

    public StringProperty locationProperty() {
        return location;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public StringProperty fullNameProperty() {
        return fullName;
    }

    public StringProperty paymentSectionProperty() {
        return paymentSection;
    }

    public StringProperty tutorUsernameProperty() {
        return tutorUsername;
    }

    public StringProperty yearOfEmploymentProperty() {
        return yearOfEmployment;
    }

    public StringProperty empStatusProperty() {
        return empStatus;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public static class TutorialDAO {

        public static List<Tutorial> getAllTutorials() throws SQLException {
            List<Tutorial> tutorials = new ArrayList<>();
            String query = "SELECT * FROM student_module_schedule";
            Connection connection = Database.connectDB();
            try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    String studentUsername = resultSet.getString("Student_Username");
                    String moduleName = resultSet.getString("Module_Name");
                    LocalDate moduleDate = resultSet.getDate("Module_Date").toLocalDate();
                    LocalTime moduleTime = resultSet.getTime("Module_Time").toLocalTime();
                    String specialComments = resultSet.getString("Special_Comments");
                    String location = resultSet.getString("Location");
                    String status = resultSet.getString("Status");

                    Tutorial tutorial = new Tutorial(id, studentUsername, moduleName, moduleDate, moduleTime, specialComments, location, status, "", "", "", "", "");
                    tutorials.add(tutorial);
                }
            }
            return tutorials;
        }

        public static void updateTutorial(Tutorial tutorial) throws SQLException {
            String query = "UPDATE student_module_schedule SET Status = ? WHERE ID = ?";
            try (Connection connection = Database.connectDB(); PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, tutorial.getStatus());
                statement.setInt(2, tutorial.getId());
                statement.executeUpdate();
            }
        }

        public static String getStudentPhoneNumber(String studentUsername) throws SQLException {
            String phoneNumber = null;
            String query = "SELECT phone FROM student WHERE username = ?";
            try (Connection connection = Database.connectDB(); PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, studentUsername);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        phoneNumber = resultSet.getString("phone");
                    }
                }
            }
            return phoneNumber;
        }

        public static String getTutorPhoneNumber(int tutorId) throws SQLException {
            String phoneNumber = null;
            String query = "SELECT phone FROM tutor WHERE tutor_id = ?";
            try (Connection connection = Database.connectDB(); PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, tutorId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        phoneNumber = resultSet.getString("phone");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception appropriately, such as logging or throwing a custom exception
            }
            return phoneNumber;
        }

        public static String generateSouthAfricanPhoneNumber() {
            Random random = new Random();

            // South African phone numbers typically start with '0' followed by 9 digits
            StringBuilder phoneNumber = new StringBuilder("0");

            // Generate the next 9 digits randomly
            for (int i = 0; i < 9; i++) {
                phoneNumber.append(random.nextInt(10)); // Append a random digit (0-9)
            }

            return phoneNumber.toString();
        }

        public static void main(String[] args) {
            try {
                // Example usage: Get a random South African phone number and a tutor's phone number
                String randomPhoneNumber = generateSouthAfricanPhoneNumber();
                System.out.println("Random South African Phone Number: " + randomPhoneNumber);

                int tutorId = 123; // Replace with the actual tutor ID
                String tutorPhoneNumber = getTutorPhoneNumber(tutorId);
                System.out.println("Tutor's Phone Number: " + tutorPhoneNumber);
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception appropriately
            }
        }

        public static List<Tutorial> getPaymentData() throws SQLException {
            List<Tutorial> paymentData = new ArrayList<>();
            String query = "SELECT id, EmpStudentNumber, EmpYearOfEmployment, EmpFullName, EmpPaymentSection, EmpStatus FROM Employee";
            try (Connection connection = Database.connectDB(); PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String studentNumber = resultSet.getString("EmpStudentNumber");
                    LocalDate yearOfEmployment = resultSet.getDate("EmpYearOfEmployment").toLocalDate();
                    String fullName = resultSet.getString("EmpFullName");
                    String paymentSection = resultSet.getString("EmpPaymentSection");
                    String status = resultSet.getString("EmpStatus");

                    Tutorial payment = new Tutorial(id, studentNumber, "", null, null, status, studentNumber, yearOfEmployment.toString(), fullName, paymentSection, studentNumber, yearOfEmployment.toString(), status);
                    paymentData.add(payment);
                }
            }
            return paymentData;
        }
    }
}
