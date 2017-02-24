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

import com.niit.CollaborationBackEnd.dao.BlogDAO;
import com.niit.CollaborationBackEnd.model.Blog;

@RestController
public class BlogController {

	@Autowired
	Blog blog;

	@Autowired
	BlogDAO blogDAO;

	@PostMapping("/addBlog/")
	public ResponseEntity<Blog> addBlog(@RequestBody Blog blog) {
		blogDAO.save(blog);
		blog.setErrorCode("200");
		blog.setErrorMsg("SUCCESS");
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}

	@GetMapping("/listBlog")
	public ResponseEntity<List<Blog>> getList() {
		List<Blog> list = blogDAO.list();
		blog.setErrorCode("200");
		blog.setErrorMsg("Success.....");
		return new ResponseEntity<List<Blog>>(list, HttpStatus.OK);
	}

	@GetMapping("/delete_blog-{id}")
	public ResponseEntity<Blog> deleteBlog(@PathVariable("id") String id) {
		blogDAO.delete(id);
		blog.setErrorCode("200");
		blog.setErrorMsg("Deleted Successfully........");
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}

	@PutMapping("/updateBlog")
	public ResponseEntity<Blog> editBlog(@RequestBody Blog blog) {
		blogDAO.update(blog);
		blog.setErrorCode("200");
		blog.setErrorMsg("Edited Successfully.....");
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}

}
