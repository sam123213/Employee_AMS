package com.itp.AMS.entity;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Data
//@ToString(exclude = "users")
@Entity
@Table(name = "attendance")
public class AttendanceEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="attendanceid")
    private int attendanceId; // Primary Key

    @NotNull
    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "userid", nullable = false)
    private UsersEntity users; // Foreign Key referencing User

    @NotNull
    @Column(nullable = false)
    private LocalDate date; // Date of attendance

    @Column
    private LocalTime checkInTime; // Nullable to handle missing check-ins

    @Column
    private LocalTime checkOutTime; // Nullable to handle missing check-outs

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private AttendanceStatus status; // Enum for Present, Absent, or Late
    
 // Duration in hours (optional)
    private Double workingHours;
    
    

}
