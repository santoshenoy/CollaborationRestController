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

import com.niit.CollaborationBackEnd.dao.ForumDAO;
import com.niit.CollaborationBackEnd.model.Forum;

@RestController
public class ForumController {

	@Autowired
	Forum forum;

	@Autowired
	ForumDAO forumDAO;

	@PostMapping("/addForum/")
	public ResponseEntity<Forum> addForum(@RequestBody Forum forum) {
		forumDAO.save(forum);
		forum.setErrorCode("200");
		forum.setErrorMsg("SUCCESS");
		return new ResponseEntity<Forum>(forum, HttpStatus.OK);
	}

	@GetMapping("/listForum")
	public ResponseEntity<List<Forum>> getList() {
		List<Forum> list = forumDAO.list();
		forum.setErrorCode("200");
		forum.setErrorMsg("Success.....");
		return new ResponseEntity<List<Forum>>(list, HttpStatus.OK);
	}

	@GetMapping("/delete_forum-{id}")
	public ResponseEntity<Forum> deleteForum(@PathVariable("id") String id) {
		forumDAO.delete(id);
		forum.setErrorCode("200");
		forum.setErrorMsg("Deleted Successfully........");
		return new ResponseEntity<Forum>(forum, HttpStatus.OK);
	}

	@PutMapping("/updateForum")
	public ResponseEntity<Forum> editForum(@RequestBody Forum forum) {
		forumDAO.update(forum);
		forum.setErrorCode("200");
		forum.setErrorMsg("Edited Successfully.....");
		return new ResponseEntity<Forum>(forum, HttpStatus.OK);
	}

}
