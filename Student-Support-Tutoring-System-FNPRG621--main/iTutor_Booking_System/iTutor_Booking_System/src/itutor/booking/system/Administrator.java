/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itutor.booking.system;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Admin
 */
public class Administrator {
    public static List<Tutorial> getAllTutorials() throws SQLException {
        return Tutorial.TutorialDAO.getAllTutorials();
    }
    
    public static void updateTutorialStatus(Tutorial tutorial) throws SQLException {
        Tutorial.TutorialDAO.updateTutorial(tutorial);
    }
   
}