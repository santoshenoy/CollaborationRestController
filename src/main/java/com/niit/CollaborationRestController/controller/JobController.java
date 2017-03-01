package com.niit.CollaborationRestController.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.niit.CollaborationBackEnd.dao.JobDAO;
import com.niit.CollaborationBackEnd.model.Job;

@RestController
public class JobController {

	@Autowired
	Job job;

	@Autowired
	JobDAO jobDAO;

	@PostMapping("/addJob/")
	public ResponseEntity<Job> addJob(@RequestBody Job job) {
		jobDAO.save(job);
		job.setErrorCode("200");
		job.setErrorMsg("SUCCESS");
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}

	@GetMapping("/listJob")
	public ResponseEntity<List<Job>> getList() {
		List<Job> list = jobDAO.list();
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		for (Job j : list) {
			String d = df.format(j.getDateTime());
			j.setDate1(d);
		}
		job.setErrorCode("200");
		job.setErrorMsg("Success.....");
		return new ResponseEntity<List<Job>>(list, HttpStatus.OK);
	}

	@GetMapping("/delete_job-{id}")
	public ResponseEntity<Job> deleteJob(@PathVariable("id") int id) {
		jobDAO.delete(id);
		job.setErrorCode("200");
		job.setErrorMsg("Deleted Successfully........");
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}

	@PutMapping("/updateJob")
	public ResponseEntity<Job> editJob(@RequestBody Job job) {
		jobDAO.update(job);
		job.setErrorCode("200");
		job.setErrorMsg("Edited Successfully.....");
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}

}
