package com.itp.AMS.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
//@ToString(exclude = "attendanceRecords")
@Entity
@Table(name = "users")
public class UsersEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userid")
    private int userId; // Primary Key

    @NotBlank
    @Column(length = 100, nullable = false)
    private String name;

    @NotBlank
    @Email
    @Column(length = 150, nullable = false, unique = true)
    private String email; // Unique constraint

    @NotBlank
    @Column(nullable = false)
    private String password; // Store hashed passwords

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role; // Enum for Admin, Employee, or SuperAdmin

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AttendanceEntity> attendanceEntities;


}  


