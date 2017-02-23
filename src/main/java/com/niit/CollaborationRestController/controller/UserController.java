package com.niit.CollaborationRestController.controller;

import java.util.List;

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
import com.niit.CollaborationBackEnd.model.User;

@RestController
public class UserController {

	@Autowired
	User user;

	@Autowired
	UserDAO userDAO;

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

}
