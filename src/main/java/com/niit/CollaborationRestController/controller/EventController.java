package com.niit.CollaborationRestController.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import com.niit.CollaborationBackEnd.dao.EventDAO;
import com.niit.CollaborationBackEnd.model.Event;

@RestController
public class EventController {

	@Autowired
	Event event;

	@Autowired
	EventDAO eventDAO;

	@PostMapping("/addEvent/")
	public ResponseEntity<Event> addEvent(@RequestBody Event event) {
		eventDAO.save(event);
		event.setErrorCode("200");
		event.setErrorMsg("SUCCESS");
		return new ResponseEntity<Event>(event, HttpStatus.OK);
	}

	@GetMapping("/listEvent")
	public ResponseEntity<List<Event>> getList() {
		List<Event> list = eventDAO.list();
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		for (Event e : list) {
			String d = df.format(e.getDate_time());
			e.setDate4(d);
		}
		event.setErrorCode("200");
		event.setErrorMsg("Success.....");
		return new ResponseEntity<List<Event>>(list, HttpStatus.OK);
	}

	@GetMapping("/delete_event-{id}")
	public ResponseEntity<Event> deleteEvent(@PathVariable("id") String id) {
		eventDAO.delete(id);
		event.setErrorCode("200");
		event.setErrorMsg("Deleted Successfully........");
		return new ResponseEntity<Event>(event, HttpStatus.OK);
	}

	@PutMapping("/updateEvent")
	public ResponseEntity<Event> editEvent(@RequestBody Event event) {
		eventDAO.update(event);
		event.setErrorCode("200");
		event.setErrorMsg("Edited Successfully.....");
		return new ResponseEntity<Event>(event, HttpStatus.OK);
	}

}
