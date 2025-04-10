package com.itp.AMS.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.itp.AMS.entity.UsersEntity;


@Repository
public interface AMSRepository extends JpaRepository<UsersEntity, Integer>
{

	Optional<UsersEntity> findByEmailAndPassword(String email, String password);

	@Query(nativeQuery = true, value="select * from users where role = 'employee'")
	List<UsersEntity> findAllEmployee();

	//Optional<UsersEntity> findById(int id);

//	UsersEntity save(Optional<UsersEntity> empFromDB);
	
//	Optional<AttendanceEntity> findByUserIdAndDate(int userId, LocalDate date);

}
