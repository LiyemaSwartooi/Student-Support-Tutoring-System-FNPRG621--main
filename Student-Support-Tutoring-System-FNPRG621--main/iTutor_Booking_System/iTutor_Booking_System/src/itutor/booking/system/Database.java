/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itutor.booking.system;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * A utility class for establishing a connection to a MySQL database.
 */
public class Database {
//6
    /**
     * Establishes a connection to the MySQL database.
     *
     * @return A Connection object representing the database connection, or null
     * if connection fails.
     */
    public static Connection connectDB() {

        try {
            // Load the MySQL JDBC driver class
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the MySQL database
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/itutor_booking_system", "root", "");
            return connect; // Return the Connection object if connection is successful
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace if an exception occurs during connection
        }
        return null; // Return null if connection fails
    }
}
