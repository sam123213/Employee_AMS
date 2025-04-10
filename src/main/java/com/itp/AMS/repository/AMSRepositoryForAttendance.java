package com.itp.AMS.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.itp.AMS.entity.AttendanceEntity;
import com.itp.AMS.entity.AttendanceSummary;

@Repository
public interface AMSRepositoryForAttendance extends JpaRepository<AttendanceEntity, Integer> 
{
	Optional<AttendanceEntity> findByUsersUserIdAndDate(int userId, LocalDate date);

	@Query(nativeQuery = true, value="select * from attendance where userid=?1")
	List<AttendanceEntity> getAttendanceByUserId(int id);
	
	//Optional<AttendanceEntity> findByUsersUserIdAndDate(int userId, LocalDate date);
	
	//Optional<AttendanceEntity> findByUserIdAndDate(int userId, LocalDate date);

	//Optional<AttendanceEntity> findByIdAndDate(int userId, LocalDate today);

	// repository/AttendanceRepo.java
	@Query("SELECT new com.itp.AMS.entity.AttendanceSummary(a.users.userId, a.users.name, a.users.email, " +
		       "SUM(CASE WHEN a.status = 'Present' OR a.status = 'Late_Full_Day' THEN 1 ELSE 0 END), " +
		       "SUM(CASE WHEN a.status = 'Absent' THEN 1 ELSE 0 END), " +
		       "SUM(CASE WHEN a.status = 'LATE' THEN 1 ELSE 0 END)) " +
		       "FROM AttendanceEntity a GROUP BY a.users.userId, a.users.name, a.users.email")
		List<AttendanceSummary> fetchAttendanceSummary();

}
