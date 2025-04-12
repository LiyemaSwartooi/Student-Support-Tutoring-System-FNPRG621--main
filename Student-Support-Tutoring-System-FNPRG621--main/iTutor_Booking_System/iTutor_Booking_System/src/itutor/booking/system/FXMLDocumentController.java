/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package itutor.booking.system;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {
    
    @FXML
    private PasswordField Administrator_confirmPassword;

    @FXML
    private AnchorPane Administrator_form;

    @FXML
    private PasswordField Administrator_password;

    @FXML
    private Button Administrator_signUp;

    @FXML
    private Hyperlink Administrator_signinhere;

    @FXML
    private TextField Administrator_username;

    @FXML
    private AnchorPane login_form;

    @FXML
    private PasswordField login_password;

    @FXML
    private ComboBox<String> login_role;

    @FXML
    private Button login_signUp;

    @FXML
    private TextField login_username;

    @FXML
    private PasswordField student_confirmPassword;

    @FXML
    private AnchorPane student_form;

    @FXML
    private PasswordField student_password;

    @FXML
    private TextField student_phoneNumber;

    @FXML
    private Button student_signUp;

    @FXML
    private Hyperlink student_signinhere;

    @FXML
    private TextField student_username;

    @FXML
    private PasswordField tutor_confirmPassword;

    @FXML
    private AnchorPane tutor_form;

    @FXML
    private TextField tutor_id;

    @FXML
    private PasswordField tutor_password;

    @FXML
    private TextField tutor_phoneNumber;

    @FXML
    private Button tutor_signUp;

    @FXML
    private Hyperlink tutor_signinhere;

    @FXML
    private TextField tutor_username;

    private static final String ALGORITHM = "AES";
    private static final String ENCRYPTION_KEY = "your_encryption_key_here"; // Replace with your actual encryption key

    private AlertMessage alert = new AlertMessage();

    public void roleList() {
        List<String> listR = new ArrayList<>();
        listR.add("Administrator");
        listR.add("Student");
        listR.add("Tutor");

        ObservableList<String> listData = FXCollections.observableArrayList(listR);
        login_role.setItems(listData);
    }

    public void signInForm() {
        login_form.setVisible(true);
        student_form.setVisible(false);
        tutor_form.setVisible(false);
        Administrator_form.setVisible(false);
    }

    public void switchForm(ActionEvent event) {
        switch (login_role.getSelectionModel().getSelectedItem()) {
            case "Student":
                login_form.setVisible(false);
                student_form.setVisible(true);
                tutor_form.setVisible(false);
                Administrator_form.setVisible(false);
                break;
            case "Tutor":
                login_form.setVisible(false);
                student_form.setVisible(false);
                tutor_form.setVisible(true);
                Administrator_form.setVisible(false);
                break;
            case "Administrator":
                login_form.setVisible(false);
                student_form.setVisible(false);
                tutor_form.setVisible(false);
                Administrator_form.setVisible(true);
                break;
            default:
                break;
        }
    }

    public void registerStudent() {
        String username = student_username.getText().trim();
        String password = student_password.getText();
        String confirmPassword = student_confirmPassword.getText();
        String phoneNumber = student_phoneNumber.getText().trim();

        // Encrypt the username, password, and phone number
        String encryptedUsername = encrypt(username);
        String encryptedPassword = encrypt(password);
        String encryptedPhoneNumber = encrypt(phoneNumber);

        if (username.isEmpty() || encryptedPassword.isEmpty() || confirmPassword.isEmpty() || encryptedPhoneNumber.isEmpty()) {
            alert.errorMessage("Please fill in all fields as they are mandatory");
            return;
        }

        if (!username.matches("\\d{9}@spu\\.ac\\.za")) {
            alert.errorMessage("Invalid username format. Please enter your 9 uniquely identifiable valid student number followed by '@spu.ac.za'");
            return;
        }

        if (!phoneNumber.matches("\\+27\\d{9}")) {
            alert.errorMessage("Invalid phone number. Phone number must start with +27 and be 9 digits long.");
            return;
        }

        if (password.length() < 8 || !password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") || !password.matches(".*\\d.*")) {
            alert.errorMessage("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one digit.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            alert.errorMessage("Password does not match.");
            return;
        }

        Connection connect = Database.connectDB();

        try {
            if (!isUniqueField("student", "phone", encryptedPhoneNumber)) {
                alert.errorMessage("Phone number already exists");
                return;
            }

            if (!isUniqueField("student", "username", encryptedUsername)) {
                alert.errorMessage("Username already exists");
                return;
            }

            String insertData = "INSERT INTO student (username, password, date, phone) "
                    + "VALUES(?,?,?,?)";

            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            PreparedStatement prepare = connect.prepareStatement(insertData);
            prepare.setString(1, encryptedUsername);
            prepare.setString(2, encryptedPassword);
            prepare.setString(3, String.valueOf(sqlDate));
            prepare.setString(4, encryptedPhoneNumber);

            prepare.executeUpdate();

            alert.successMessage("Registered successfully!");

            login_form.setVisible(true);
            student_form.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connect.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void registerTutor() {
        String username = tutor_username.getText().trim();
        String password = tutor_password.getText();
        String confirmPassword = tutor_confirmPassword.getText();
        String phoneNumber = tutor_phoneNumber.getText().trim();

        // Encrypt the username, password, and phone number
        String encryptedUsername = encrypt(username);
        String encryptedPassword = encrypt(password);
        String encryptedPhoneNumber = encrypt(phoneNumber);

        if (username.isEmpty() || encryptedPassword.isEmpty() || confirmPassword.isEmpty() || encryptedPhoneNumber.isEmpty()) {
            alert.errorMessage("Please fill all mandatory fields");
            return;
        }

        Connection connect = Database.connectDB();

        if (!phoneNumber.matches("\\+27\\d{9}")) {
            alert.errorMessage("Invalid phone number. Phone number must start with +27 and be 9 digits long.");
            return;
        }

        if (password.length() < 8 || !password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") || !password.matches(".*\\d.*")) {
            alert.errorMessage("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one digit.");
            return;
        }

        if (!username.matches("\\d{9}@spu\\.ac\\.za")) {
            alert.errorMessage("Invalid username format. Please enter your 9 uniquely identifiable valid student number followed by '@spu.ac.za'");
            return;
        }

        if (!password.equals(confirmPassword)) {
            alert.errorMessage("Password does not match.");
            return;
        }

        try {
            if (!isUniqueField("tutor", "phone", encryptedPhoneNumber)) {
                alert.errorMessage("Phone number already exists");
                return;
            }

            Random random = new Random();
            int tutorID = random.nextInt(90000) + 10000;
            tutor_id.setText(String.valueOf(tutorID));

            if (!isUniqueField("tutor", "username", encryptedUsername)) {
                alert.errorMessage("Username already exists");
                return;
            }

            if (!isUniqueField("tutor", "password", encryptedPassword)) {
                alert.errorMessage("This password has been used before. Please choose a different one.");
                return;
            }

            String insertData = "INSERT INTO tutor (username, password, date, phone, tutor_id) "
                    + "VALUES(?,?,?,?,?)";

            Date date = new Date(System.currentTimeMillis());

            try (PreparedStatement prepare = connect.prepareStatement(insertData)) {
                prepare.setString(1, encryptedUsername);
                prepare.setString(2, encryptedPassword);
                prepare.setDate(3, new java.sql.Date(date.getTime()));
                prepare.setString(4, encryptedPhoneNumber);
                prepare.setInt(5, tutorID);

                prepare.executeUpdate();

                alert.successMessage("Tutor registered successfully! Tutor ID: " + tutorID);
                login_form.setVisible(true);
                tutor_form.setVisible(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void registerAdministrator() {
        String username = Administrator_username.getText().trim();
        String password = Administrator_password.getText();
        String confirmPassword = Administrator_confirmPassword.getText();

        // Encrypt the username and password
        String encryptedUsername = encrypt(username);
        String encryptedPassword = encrypt(password);

        if (username.isEmpty() || encryptedPassword.isEmpty() || confirmPassword.isEmpty()) {
            alert.errorMessage("Please fill all blank fields");
            return;
        }

        Connection connect = Database.connectDB();

        try {
            if (!isUniqueField("administrator", "username", encryptedUsername)) {
                alert.errorMessage(username + " is already exist");
                return;
            }

            if (!isUniqueField("administrator", "password", encryptedPassword)) {
                alert.errorMessage("This password has been used before. Please choose a different one.");
                return;
            }

            if (!username.matches("\\d{5}")) {
                alert.errorMessage("Invalid username format. Please enter your 5 uniquely identifiable valid Employee number");
                return;
            }

            if (!password.equals(confirmPassword)) {
                alert.errorMessage("Password does not match.");
                return;
            }

            if (password.length() < 8) {
                alert.errorMessage("Invalid password, at least 8 characters needed");
                return;
            }

            String insertData = "INSERT INTO administrator (username, password, date) "
                    + "VALUES(?,?,?)";

            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            PreparedStatement prepare = connect.prepareStatement(insertData);
            prepare.setString(1, encryptedUsername);
            prepare.setString(2, encryptedPassword);
            prepare.setString(3, String.valueOf(sqlDate));

            prepare.executeUpdate();

            alert.successMessage("Administrator registered successfully!");

            login_form.setVisible(true);
            Administrator_form.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connect.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isUniqueField(String table, String field, String value) {
        try (Connection connection = Database.connectDB(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table + " WHERE " + field + " = ?")) {
            statement.setString(1, value);
            try (ResultSet resultSet = statement.executeQuery()) {
                return !resultSet.next(); // Returns true if resultSet is empty, meaning the field is unique
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Error occurred, return false as safety measure
        }
    }

    public void loginAccount() {
        if (login_username.getText().isEmpty() || login_password.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields");
            return;
        }

        String selectedRole = login_role.getSelectionModel().getSelectedItem();
        if (selectedRole == null) {
            alert.errorMessage("Please select your role and Press Sign in here if Already registered, if not please register with your selected role");
            return;
        }

        String selectData = "SELECT * FROM " + selectedRole + " WHERE username = ?";
        Connection connect = Database.connectDB();
        String role = "";
        try {
            PreparedStatement prepare = connect.prepareStatement(selectData);
            prepare.setString(1, encrypt(login_username.getText())); // Encrypt username for comparison
            ResultSet result = prepare.executeQuery();
            if (result.next()) {
                role = result.getString("role");
                String encryptedPassword = result.getString("password");
                String decryptedPassword = decrypt(encryptedPassword);

                if (decryptedPassword.equals(login_password.getText())) {
                    System.out.println(role); // You can remove this line if not needed
                    Thread.sleep(1000);
                    if (role.equals("Administrator")) {
                        showAdminForm();
                    } else if (role.equals("Student")) {
                        showStudentForm();
                    } else if (role.equals("Tutor")) {
                        showTutorForm();
                    } else {
                        alert.errorMessage("Invalid Role");
                    }
                } else {
                    alert.errorMessage("Incorrect Username/Password");
                }
            } else {
                alert.errorMessage("Incorrect Username/Password");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String encrypt(String input) {
        try {
            SecretKey secretKey = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(input.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String decrypt(String input) {
        try {
            SecretKey secretKey = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(input));
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void showAdminForm() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdministratorForm.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("iTutor Booking System | Admin Portal");
        stage.setScene(new Scene(root));
        stage.setOnCloseRequest(event -> {
            event.consume();
            showLoginForm();
            stage.close();
        });
        stage.show();
        closeLoginForm();
    }

    private void showStudentForm() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentForm.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("iTutor Booking System | Student Portal");
        stage.setScene(new Scene(root));
        stage.setOnCloseRequest(event -> {
            event.consume();
            showLoginForm();
            stage.close();
        });
        stage.show();
        closeLoginForm();
    }

    private void showTutorForm() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TutorForm.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("iTutor Booking System | Tutor Portal");
        stage.setScene(new Scene(root));
        stage.setOnCloseRequest(event -> {
            event.consume();
            showLoginForm();
            stage.close();
        });
        stage.show();
        closeLoginForm();
    }

    private void showLoginForm() {
        login_form.setVisible(true);
    }

    private void closeLoginForm() {
        login_form.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        roleList();
    }
}
