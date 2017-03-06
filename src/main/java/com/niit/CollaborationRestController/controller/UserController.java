package com.niit.CollaborationRestController.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.niit.CollaborationBackEnd.dao.UserDAO;
import com.niit.CollaborationBackEnd.model.Forum;
import com.niit.CollaborationBackEnd.model.User;

@RestController
public class UserController {

	@Autowired
	User user;

	@Autowired
	UserDAO userDAO;

	@Autowired
	HttpSession session;

	@PostMapping("/addUser/")
	public ResponseEntity<User> addUser(@RequestBody User user) {
		user.setRole("Student");
		user.setIsOnline('N');
		user.setStatus('P');
		userDAO.save(user);
		user.setErrorCode("200");
		user.setErrorMsg("SUCCESS");
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@GetMapping("/listUser")
	public ResponseEntity<List<User>> getList() {
		List<User> list = userDAO.list();
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		for (User u : list) {
			String d = df.format(u.getDob());
			u.setDate4(d);
		}
		user.setErrorCode("200");
		user.setErrorMsg("Success.....");
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}

	@GetMapping("/delete_user-{id}")
	public ResponseEntity<User> deleteUser(@PathVariable("id") String id) {
		userDAO.delete(id);
		user.setErrorCode("200");
		user.setErrorMsg("Deleted Successfully........");
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@PutMapping("/updateUser")
	public ResponseEntity<User> editUser(@RequestBody User user) {
		userDAO.update(user);
		user.setErrorCode("200");
		user.setErrorMsg("Edited Successfully.....");
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<User> validateUser(@RequestBody User user) {
		System.out.println("Name: " + user.getId());
		System.out.println("Password: " + user.getPsswrd());
		boolean value = userDAO.validateUser(user.getId(), user.getPsswrd());
		System.out.println(value);
		if (value == false) {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMsg("Wrong username and/or password");
		} else {
			if (user.getStatus() == 'R') {
				user = new User();
				user.setErrorCode("404");
				user.setErrorMsg("Registration rejected");
			}
			if (user.getStatus() == 'N') {
				user = new User();
				user.setErrorCode("404");
				user.setErrorMsg("Registration approval is pending");
			} else {
				user = userDAO.getUser(user.getId());
				System.out.println(user.getId());
				user.setIsOnline('Y');
				userDAO.save(user);
				session.setAttribute("id", user.getId());
				session.setAttribute("role", user.getRole());
				user.setErrorCode("200");
				user.setErrorMsg("Success");
			}
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@GetMapping("/logout")
	public ResponseEntity<User> logout() {
		user = userDAO.getUser(session.getAttribute("id").toString());
		user.setIsOnline('N');
		userDAO.save(user);
		user.setErrorCode("200");
		user.setErrorMsg("You have successfully logged out");
		session.invalidate();
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

}
