/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itutor.booking.system;

import java.util.Date;

/**
 *
 * @author Admin
 */
//3 List The Data that will be used in the login_role combox
public class ListData {

    public static String Administrator_username;

    public static String tutor_username;

    public static String student_username;

    public static String[] role = {"Student", "Tutor", "Administrator"};

    public static String[] DesiredLocations = {
        "Online on Teams",
        "Aud 1",
        "Aud 2",
        "Development Lab",
        "Library"
    };

    public static String[] Student = {
        "NADF511: APPLICATIONS DEVELOPMENT FOUNDATIONS",
        "NBUP512: BUSINESS PRACTICE",
        "NCNF512: COMMUNICATION NETWORKS FOUNDATIONS",
        "NINS512: INFORMATION SYSTEMS I",
        "NITF513: ICT FUNDAMENTALS",
        "NPRG512: PROGRAMMING 1",
        "SCOR511: CORE CURRICULUM 1",
        "SCOR612: CORE CURRICULUM 2"
    };
    public static String[] ModuleTimes = {
        "08:00", // 8:00 AM
        "09:30", // 9:30 AM
        "11:00", // 11:00 AM
        "13:00", // 1:00 PM
        "14:30", // 2:30 PM
        "16:00", // 4:00 PM
        "17:30", // 5:30 PM
        "19:00" // 7:00 PM
    };
    public static String[] Status = {
        "Payment successful",
        "Payment unccessful"

    };

}
