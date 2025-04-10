package com.itp.AMS.entity;

import java.time.LocalDate;

public class AttendanceClassViewModel {
    // Properties from User
    private int userId;
    private String name;
    private String email;
    private UserRole role;
    private String password;

    // Properties from Attendance
    private int attendanceId;
    private LocalDate date;
    private String formattedCheckInTime;
    private String formattedCheckOutTime;
    private AttendanceStatus status;

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getFormattedCheckInTime() {
        return formattedCheckInTime;
    }

    public void setFormattedCheckInTime(String formattedCheckInTime) {
        this.formattedCheckInTime = formattedCheckInTime;
    }

    public String getFormattedCheckOutTime() {
        return formattedCheckOutTime;
    }

    public void setFormattedCheckOutTime(String formattedCheckOutTime) {
        this.formattedCheckOutTime = formattedCheckOutTime;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }
}
