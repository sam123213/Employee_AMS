package com.itp.AMS.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AttendanceSummary 
{
	private int userId;
	private String name;
	private String email;
    private long totalPresent;
    private long totalAbsent;
    private long totalLate;
}
