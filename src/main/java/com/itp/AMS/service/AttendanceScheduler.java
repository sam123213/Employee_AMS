package com.itp.AMS.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.itp.AMS.entity.AttendanceEntity;
import com.itp.AMS.entity.AttendanceStatus;
import com.itp.AMS.entity.UsersEntity;

@Service
public class AttendanceScheduler {

    @Autowired
    AMSService amsService;

    @Autowired
    AMSServiceForAttendance amsServiceForAttendance;

    @Scheduled(cron = "0 5 18 * * ?") // Scheduled daily at 6:05 PM
    public void markAbsentEmployees() {
        runAbsenceCheck(); // Common logic extracted
    }

    // ✅ Trigger this when the app starts (e.g., after 6:05 PM)
    @EventListener(ApplicationReadyEvent.class)
    public void runOnStartup() {
        if (LocalTime.now().isAfter(LocalTime.of(18, 5))) {
            System.out.println("⚠️ App started after 6:05 PM, running missed absence check...");
            runAbsenceCheck();
        }
    }

    // ✅ Common logic
    public void runAbsenceCheck() {
        LocalDate today = LocalDate.now();
        System.out.println("🔁 Running absence check at: " + LocalDateTime.now());

        List<UsersEntity> allEmployees = amsService.getAllEmp();

        for (UsersEntity employee : allEmployees) {
            Optional<AttendanceEntity> optionalAttendance =
                amsServiceForAttendance.findByUserIdAndDate(employee.getUserId(), today);

            if (optionalAttendance.isEmpty()) {
                AttendanceEntity attendance = new AttendanceEntity();
                attendance.setUsers(employee);
                attendance.setDate(today);
                attendance.setStatus(AttendanceStatus.ABSENT);
                amsServiceForAttendance.saveAttendance(attendance);
                System.out.println("❌ Marked Absent (no record): " + employee.getName());
            } else {
                AttendanceEntity attendance = optionalAttendance.get();
                if (attendance.getCheckInTime() == null) {
                    attendance.setStatus(AttendanceStatus.ABSENT);
                    attendance.setCheckOutTime(null);
                    amsServiceForAttendance.saveAttendance(attendance);
                    System.out.println("❌ Marked Absent: " + employee.getName());
                } else {
                    System.out.println("✅ Valid Attendance: " + employee.getName());
                }
            }
        }
    }
}