package com.itp.AMS.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itp.AMS.entity.AttendanceEntity;
import com.itp.AMS.entity.AttendanceSummary;
import com.itp.AMS.repository.AMSRepository;
import com.itp.AMS.repository.AMSRepositoryForAttendance;

@Service
public class AMSServiceForAttendance 
{
	@Autowired
	AMSRepositoryForAttendance amsRepositoryForAttendance;
	
	@Autowired
	AMSRepository amsRepository;
	
	public Optional<AttendanceEntity> findByUserIdAndDate(int Id,LocalDate today) 
	{
		return amsRepositoryForAttendance.findByUsersUserIdAndDate(Id,today);
	}
	
	public AttendanceEntity saveAttendance(AttendanceEntity attendance) 
	{
		return amsRepositoryForAttendance.save(attendance);
	}

	public List<AttendanceEntity> getAllAttendanceRecords()
	{
		return amsRepositoryForAttendance.findAll();
	}

//	public Optional<AttendanceEntity> getAttendance(int id) 
//	{
//		return amsRepositoryForAttendance.findById(id);
//	}

	public AttendanceEntity getEmp(int id)
	{
		return amsRepositoryForAttendance.findById(id).get();
	}

	public List<AttendanceEntity> getAttendanceByUserId(int id)
	{
		return amsRepositoryForAttendance.getAttendanceByUserId(id);
	}

	public void save(AttendanceEntity attendance) 
	{
		amsRepositoryForAttendance.save(attendance);
	}

//	public Optional<AttendanceEntity> findByUserId(int userId) 
//	{
//		return amsRepositoryForAttendance.findById(userId);
//	}
	
	// service/AmsServiceForAttendance.java
	public List<AttendanceSummary> getAttendanceSummary() {
	    return amsRepositoryForAttendance.fetchAttendanceSummary();
	}

}
