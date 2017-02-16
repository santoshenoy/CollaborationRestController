package com.niit.CollaborationRestController.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.niit.CollaborationBackEnd.dao.Test_UserDAO;
import com.niit.CollaborationBackEnd.dao.UserDAO;
import com.niit.CollaborationBackEnd.model.Test_User;
import com.niit.CollaborationBackEnd.model.User;

@RestController
public class UserController {
	
	@Autowired
	User user;
	
	@Autowired
	UserDAO userDAO;
	
	
	@PostMapping("/addUser/")
	public ResponseEntity<User> addUser(@RequestBody User user) {
		
		userDAO.save(user);
		user.setErrorCode("200");
		user.setErrorMsg("SUCCESS");
		
		return new ResponseEntity<User>(user,HttpStatus.OK);
		
		
	}
	

}
