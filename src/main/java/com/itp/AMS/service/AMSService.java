package com.itp.AMS.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itp.AMS.entity.UsersEntity;
import com.itp.AMS.repository.AMSRepository;

@Service
public class AMSService 
{
	@Autowired
	AMSRepository amsRepository;

	public Optional<UsersEntity> getUser(String email, String password)
	{
		return amsRepository.findByEmailAndPassword(email, password);
	}

	public UsersEntity addUser(UsersEntity u1) 
	{
		return amsRepository.save(u1);
	}

	public Optional<UsersEntity> getEmp(int id)
	{
		return amsRepository.findById(id);
	}

	public List<UsersEntity> getAllEmp() 
	{
		return amsRepository.findAllEmployee();
	}

	public void deleteEmployee(int id)
	{
		amsRepository.deleteById(id);;
		
	}

	public UsersEntity adminUpdateUser(int id, UsersEntity newDetails) 
	{
		UsersEntity empFromDB=getEmployee(id);
		
		empFromDB.setName(Objects.requireNonNullElse(newDetails.getName(),empFromDB.getName()));
		empFromDB.setEmail(Objects.requireNonNullElse(newDetails.getEmail(),empFromDB.getEmail()));
		empFromDB.setPassword(Objects.requireNonNullElse(newDetails.getPassword(),empFromDB.getPassword()));
		empFromDB.setRole(Objects.requireNonNullElse(newDetails.getRole(),empFromDB.getRole()));
		
		return addUser(empFromDB);
	}

	public UsersEntity getEmployee(int rno) 
	{
		return amsRepository.findById(rno).get();
	}

	public Optional<UsersEntity> getUserById(int id) 
	{
		return amsRepository.findById(id);
	}
//	public Optional<AttendanceEntity> findByUserIdAndDate(int userId, LocalDate today) 
//	{
//		return amsRepository.findByUserIdAndDate(userId, today);
//	}
}
