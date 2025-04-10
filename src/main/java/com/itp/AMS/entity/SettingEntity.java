package com.itp.AMS.entity;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "settings")
public class SettingEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="settingid")
    private int settingId; // Primary Key
    
    @NotNull
    @Column(nullable = false)
    private LocalTime startTime; // Start of working hours

    @NotNull
    @Column(nullable = false)
    private LocalTime endTime; // End of working hours
    
}
