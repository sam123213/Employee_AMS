
package com.itp.AMS.controller;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itp.AMS.entity.AttendanceEntity;
import com.itp.AMS.entity.AttendanceStatus;
import com.itp.AMS.entity.AttendanceSummary;
import com.itp.AMS.entity.UsersEntity;
import com.itp.AMS.service.AMSService;
import com.itp.AMS.service.AMSServiceForAttendance;

@Controller
public class AMSController {
	@Autowired
	AMSService amsService;

	@Autowired
	AMSServiceForAttendance amsServiceForAttendance;

	@RequestMapping("/home")
	public String home() {
		return "Landingpg";
	}

	// Register a new user
	@RequestMapping("/Register")
	public String registerUser(Model model) {
		UsersEntity user = new UsersEntity();
		model.addAttribute("Ruser", user);
		return "Register";
	}

	@PostMapping("/addUser")
	public String addUser(@ModelAttribute UsersEntity u1) {
		try {
			UsersEntity user = amsService.addUser(u1);

			return "redirect:/Login";
		} catch (Exception e) {
			return "Unauthorized";
		}
	}

	// Login user
	@RequestMapping("/Login")
	public String loginUser(Model model) {
		UsersEntity user = new UsersEntity();
		model.addAttribute("Luser", user);
		return "Login";
	}

	@PostMapping("/getUser")
	public String getUser(@RequestParam String email, @RequestParam String password) {
		Optional<UsersEntity> userOpt = amsService.getUser(email, password);
		if (userOpt.isPresent()) {
			UsersEntity user = userOpt.get();
			if (user.getRole().name().equalsIgnoreCase("admin")) {
				return "redirect:/getAllEmp";
			} else {
				int id = user.getUserId();
				return "redirect:/getEmp/" + id;
			}
		}
		return "Unauthorized";
	}

	@RequestMapping("/getEmp/{id}")
	public String getEmp(@PathVariable int id, Model model) {
		Optional<UsersEntity> empOptional = amsService.getEmp(id);
		List<AttendanceEntity> attendanceOptional = amsServiceForAttendance.getAttendanceByUserId(id);

		if (empOptional.isPresent()) {
			model.addAttribute("Employee", empOptional.get()); // changed attribute name to "user"
		} else {
			model.addAttribute("Epmloyee", new UsersEntity()); // fallback
		}
		model.addAttribute("attendance", attendanceOptional); // changed attribute name to "attendance"
		return "EmployeeDashboard";
	}

	@RequestMapping("/admin/addEmployee")
	public String addEmployee(Model model) {
		UsersEntity user = new UsersEntity();
		model.addAttribute("addEmp", user);
		return "AddUser";
	}

	@RequestMapping("/getAllEmp")
	public String getAllEmp(Model model) {
		List<UsersEntity> emp = amsService.getAllEmp();
		model.addAttribute("Employee", emp);
		return "Admin";
	}

	@PostMapping("admin/addUser")
	public String adminAddUser(@ModelAttribute UsersEntity u1) {
		try {
			UsersEntity user = amsService.addUser(u1);

			return "redirect:/getAllEmp";
		} catch (Exception e) {
			return "Unauthorized";
		}
	}

	@RequestMapping("/admin/userdetails/{id}")
	public String adminGetEmpDetails(@PathVariable int id, Model model) {
		Optional<UsersEntity> empOptional = amsService.getEmp(id);

		if (empOptional.isPresent()) {
			model.addAttribute("Employee", empOptional.get()); // Pass the actual object
		} else {
			model.addAttribute("Employee", new UsersEntity()); // Avoid null errors
		}
		return "Details";
	}

	@RequestMapping("/admin/deleteUser/{id}")
	public String adminDeleteUser(@PathVariable int id, Model model) {
		Optional<UsersEntity> empOptional = amsService.getEmp(id);
		if (empOptional.isPresent()) {
			model.addAttribute("Employee", empOptional.get());
		} else {
			model.addAttribute("Employee", new UsersEntity()); // Avoid null errors
		}
		return "DeleteUser";
	}

	@RequestMapping("/deleteUser/{rno}")
	public String deleteEmployee(@PathVariable int rno) {
		amsService.deleteEmployee(rno);
		return "redirect:/getAllEmp";
	}

	@RequestMapping("/admin/editUser/{id}")
	public String adminEditUser(@PathVariable int id, Model model) {
		Optional<UsersEntity> empOptional = amsService.getEmp(id);
		if (empOptional.isPresent()) {
			model.addAttribute("Employee", empOptional.get());
		} else {
			model.addAttribute("Employee", new UsersEntity()); // Avoid null errors
		}
		return "EditUser";
	}

	@GetMapping("/getEmployee/{rno}")
	public ResponseEntity<?> getEmployee(@PathVariable int rno) {
		try {
			return new ResponseEntity<UsersEntity>(amsService.getEmployee(rno), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Error Generated: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/admin/updateUser/{id}")
	public String adminUpdateUser(@PathVariable int id, @ModelAttribute UsersEntity newDetails) {
		try {
			amsService.adminUpdateUser(id, newDetails);
			return "redirect:/getAllEmp";
		} catch (Exception e) {
			return "AccessDenied";
		}
	}

	@RequestMapping("/getAllAttendanceRecords/{id}")
	public String getAllAttendanceRecords(@PathVariable int id, Model model) {
		List<AttendanceEntity> records = amsServiceForAttendance.getAttendanceByUserId(id);
		model.addAttribute("Records", records);
		return "AttendanceRecords";
	}

	@RequestMapping("/admin/employeeAttendanceRecords/{id}")
	public String employeeAttendanceRecords(@PathVariable int id, Model model) {
		try {
			UsersEntity emp=amsService.getEmployee(id);
			return "redirect:/getAllAttendanceRecords/"+id;
		} catch (Exception e) {
			return "AccessDenied";
		}
	}

	@PostMapping("/markAttendance/{id}")
	public String markAttendance(@PathVariable int id, @RequestParam String actionType,
			RedirectAttributes redirectAttributes) {
		try {
			LocalDate today = LocalDate.now();
			Optional<AttendanceEntity> optionalAttendance = amsServiceForAttendance.findByUserIdAndDate(id, today);
			Optional<UsersEntity> userOpt = amsService.getEmp(id);

			if (userOpt.isEmpty()) {
				redirectAttributes.addFlashAttribute("error", "User not found.");
				return "redirect:/markAttendancePage/" + id;
			}

			UsersEntity user = userOpt.get();
			AttendanceEntity attendance;

			LocalTime currentTime = LocalTime.now();
			LocalTime lateThreshold = LocalTime.of(9, 15); // 9:15 AM

			if ("CheckIn".equalsIgnoreCase(actionType)) {
				if (optionalAttendance.isPresent()) {
					redirectAttributes.addFlashAttribute("message", "You have already checked in for today.");
					return "redirect:/markAttendancePage/" + id;
				}

				attendance = new AttendanceEntity();
				attendance.setUsers(user);
				attendance.setDate(today);
				attendance.setCheckInTime(currentTime);

				if (currentTime.isAfter(lateThreshold)) {
					attendance.setStatus(AttendanceStatus.LATE);
				} else {
					attendance.setStatus(AttendanceStatus.ON_TIME);
				}

				amsServiceForAttendance.saveAttendance(attendance);
				redirectAttributes.addFlashAttribute("message", "Check-in successful.");
			} else if ("CheckOut".equalsIgnoreCase(actionType)) {
				if (optionalAttendance.isEmpty()) {
					redirectAttributes.addFlashAttribute("message",
							"No check-in record found for today. Please check in first.");
					return "redirect:/markAttendancePage/" + id;
				}

				attendance = optionalAttendance.get();

				// ✅ Block checkout if check-in time is missing or invalid
				if (attendance.getCheckInTime() == null || attendance.getCheckInTime().equals(LocalTime.MIDNIGHT)) {
					redirectAttributes.addFlashAttribute("message", "You must check in before checking out.");
					return "redirect:/markAttendancePage/" + id;
				}

				if (attendance.getCheckOutTime() != null) {
					redirectAttributes.addFlashAttribute("message", "You have already checked out.");
					return "redirect:/markAttendancePage/" + id;
				}

				attendance.setCheckOutTime(currentTime);

				Duration duration = Duration.between(attendance.getCheckInTime(), currentTime);
				double hoursWorked = duration.toMinutes() / 60.0;
				attendance.setWorkingHours(hoursWorked);

				AttendanceStatus currentStatus = attendance.getStatus();

				if (hoursWorked >= 8) {
					if (currentStatus == AttendanceStatus.LATE) {
						attendance.setStatus(AttendanceStatus.Late_Full_Day);
					} else {
						attendance.setStatus(AttendanceStatus.PRESENT);
					}
				} else if (hoursWorked >= 4) {
					attendance.setStatus(AttendanceStatus.Half_Day);
				} else {
					attendance.setStatus(AttendanceStatus.ABSENT);
				}

				amsServiceForAttendance.saveAttendance(attendance);
				redirectAttributes.addFlashAttribute("message", "Check-out successful.");
			} else {
				redirectAttributes.addFlashAttribute("message", "Invalid action type.");
			}

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "An error occurred while marking attendance.");
		}

		return "redirect:/markAttendancePage/" + id;
	}

	@RequestMapping("/markAttendancePage/{id}")
	public String markAttendances(@PathVariable int id, Model model) {
		try {
			UsersEntity emp = amsService.getEmployee(id);
			model.addAttribute("Employee", emp);
			return "MarkAttendance";
		} catch (Exception e) {
			return "AccessDenied";
		}
	}

	// controller/AdminController.java
	@GetMapping("/viewReport")
	public String getAttendanceReport(Model model) {
	    List<AttendanceSummary> summary = amsServiceForAttendance.getAttendanceSummary();
//	    Optional<UsersEntity> emp = amsService.getEmp(id);
//		model.addAttribute("data", emp);
	    model.addAttribute("Employee", summary);
	    return "ViewReport"; // matches attendanceReport.html
	}


	@RequestMapping("/viewAttendance")
	public String viewAttendance() {
		try {
			return "ViewAttendance";
		} catch (Exception e) {
			return "AccessDenied";
		}
	}

//	@RequestMapping("/getAttendance/{id}")
//	public String getAttendance(@PathVariable int id, Model model) {
//	    Optional<AttendanceEntity> history = amsServiceForAttendance.getAttendance(id);
//
//	    model.addAttribute("attendanceHistory", history);
//
//	    return "EmployeeDashboard";
//	}

//	@GetMapping("/Register")
//    public String showRegisterPage()
//	{
//        return "Register"; 
//    }
//	
//	@GetMapping("/AddUser")
//    public String showAddUserPage()
//	{
//        return "AddUser"; 
//    }
//
//	@GetMapping("/DeleteUser")
//	public String showDeleteUserPage()
//	{
//		return "Deleteuser";
//	}
//	
//	@GetMapping("/Details")
//	public String showDetailsPage()
//	{
//		return "Details";
//	}
//	
//	@GetMapping("/EditUser")
//	public String showEditUserPage()
//	{
//		return "EditUser";
//	}
//	
//	@GetMapping("/Admin")
//	public String showIndexPage()
//	{
//		return "Admin";
//	}
//	
//	@GetMapping("/Unauthorized")
//	public String showUnauthorizedPage()
//	{
//		return "Unauthorized";
//	}
//	
//	@GetMapping("/ViewReport")
//	public String showViewReportPage()
//	{
//		return "ViewReport";
//	}
//	
//	@GetMapping("/AttendanceRecords")
//	public String showAttendanceRecordsPage()
//	{
//		return "AttendanceRecords";
//	}
//	
//	@GetMapping("/MarkAttendance")
//	public String showMarkAttendancePage()
//	{
//		return "MarkAttendance";
//	}
//	
//	@GetMapping("/ViewAttendance")
//	public String showViewAttendancePage()
//	{
//		return "ViewAttendance";
//	}
//	
//	@GetMapping("/AccessDenied")
//	public String showAccessDeniedPage()
//	{
//		return "AccessDenied";
//	}
//	
//	@GetMapping("/EmployeeDashboard")
//	public String showEmployeeDashboardPage()
//	{
//		return "EmployeeDashboard";
//	}
}
