package itutor.booking.system;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Admin
 */
public class Claim {
 private int id;
    private String username;
    private String fromTime;
    private String toTime;
    private double ratePerUnit;
    private double totalHoursWorked;
    private double valueOfClaim;

    public Claim(int id, String username, String fromTime, String toTime, double ratePerUnit, double totalHoursWorked, double valueOfClaim) {
        this.id = id;
        this.username = username;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.ratePerUnit = ratePerUnit;
        this.totalHoursWorked = totalHoursWorked;
        this.valueOfClaim = valueOfClaim;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFromTime() {
        return fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public double getRatePerUnit() {
        return ratePerUnit;
    }

    public double getTotalHoursWorked() {
        return totalHoursWorked;
    }

    public double getValueOfClaim() {
        return valueOfClaim;
    }
    
}
