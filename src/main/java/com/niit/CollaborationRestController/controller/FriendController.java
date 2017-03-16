package com.niit.CollaborationRestController.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.CollaborationBackEnd.dao.FriendDAO;
import com.niit.CollaborationBackEnd.dao.UserDAO;
import com.niit.CollaborationBackEnd.model.Forum;
import com.niit.CollaborationBackEnd.model.Friend;
import com.niit.CollaborationBackEnd.model.User;

@RestController
public class FriendController {

	@Autowired
	Friend friend;

	@Autowired
	FriendDAO friendDAO;

	@Autowired
	HttpSession session;
	
	@Autowired
	UserDAO userDAO;
	
	/*@PostMapping("/addFriend/")
	public ResponseEntity<Friend> addFriend(@RequestBody Friend friend) {
		friendDAO.save(friend);
		friend.setErrorCode("200");
		friend.setErrorMsg("SUCCESS");
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
	}*/
	
	private boolean isUserExist(String id)
	{
		if(userDAO.getUser(id)==null)
			return false;
		else
			return true;
	}

	
	
	@GetMapping("/sendRequest-{id}")
	public ResponseEntity<Friend> addFriend(@PathVariable("id") String friendID)
	{
		if(session.getAttribute("id")==null)
		{
			//log.info("NOT LOGGED IN");
			System.out.println("Not logged in.....");
			friend = new Friend();
			friend.setErrorCode("100");
			friend.setErrorMsg("Please Login");
			return new ResponseEntity<Friend>(friend, HttpStatus.OK);
		}
		
		boolean check = isUserExist(friendID);
		if(check)
		{
			//log.info("Adding Friend");
			System.out.println("Addinf Friend.....");
			friend.setU_id(session.getAttribute("id").toString());
			friend.setF_id(friendID);
			friend.setStatus("P");
			boolean value = friendDAO.save(friend);
			if(value == true)
			{
				friend.setErrorCode("200");
				friend.setErrorMsg("Friend Request sent");
				System.out.println("Friend Request Sent.....");
				//log.info("Friend Request sent");
			}
			else
			{
				friend.setErrorCode("400");
				friend.setErrorMsg("Friend Request not sent");
				System.out.println("Friend Requset not sent");
				//log.error("Friend Requset not sent");
			}
			return new ResponseEntity<Friend>(friend, HttpStatus.OK);
		}
		
		else
		{
			System.out.println("FriendID not in database");
			//log.info("FriendID not in database");
			friend = new Friend();
			friend.setErrorCode("100");
			friend.setErrorMsg("Friend Not Found");
			return new ResponseEntity<Friend>(friend, HttpStatus.OK);
		}
	}
	

	@RequestMapping(value = "/myFriends", method = RequestMethod.GET)
	public ResponseEntity<List<String>> getMyFriends(HttpSession session) {
		List<String> myFriends = new ArrayList<String>();
		//String loggedInUserID = (String) ;
		
		if (session.getAttribute("id") == null) {
			friend.setErrorCode("404");
			friend.setErrorMsg("Please Login");
			//myFriends.add(friend);
			//return new ResponseEntity<List<String>>(myFriends, HttpStatus.OK);
			return null;
		}
		else
		{
			myFriends = friendDAO.getMyFriends(session.getAttribute("id").toString());
			System.out.println("List of Friends.....");
		if (myFriends.isEmpty() || myFriends == null) {
			friend.setErrorCode("404");
			friend.setErrorMsg("You do not have friends");
			//myFriends.add(friend);
			return null;
		}
		else
		return new ResponseEntity<List<String>>(myFriends, HttpStatus.OK);
	}
}
	
	
	
	@GetMapping("/viewPendingRequest")
	public ResponseEntity<List<Friend>> getPendingRequest()
	{
		List<Friend> list = new ArrayList<Friend>();
		if(session.getAttribute("id")==null)
		{
			//log.info("NOT LOGGED IN");
			System.out.println("NOT LOGGED IN");
			/*friend = new Friend();
			friend.setErrorCode("404");
			friend.setErrorMsg("You should LogIn");
			list.add(friend);*/
			return null;
		}
		else
		{
			//log.info("Getting Pending Request for "+session.getAttribute("username").toString());
			System.out.println("Getting Pending Request for "+session.getAttribute("id").toString());
			String userID = session.getAttribute("id").toString();
			list = friendDAO.getNewFriendRequests(userID);
			//log.info("Pending Requests recieved");
			System.out.println("Pending Requests recieved");
			if(list.isEmpty() || list==null)
			{
				/*friend.setU_id(userID);
				friend.setErrorMsg("200");
				friend.setErrorMsg("You have no Pending Request");
				list.add(friend);*/
				return null;
			}
		}
		return new ResponseEntity<List<Friend>>(list, HttpStatus.OK);
	}


	
	
	

	@RequestMapping(value = "/addFriend/{f_id}", method = RequestMethod.POST)
	public ResponseEntity<Friend> sendFriendRequest(@PathVariable("f_id") String f_id, HttpSession session) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		friend.setU_id(loggedInUser.getId());
		friend.setF_id(f_id);
		friend.setStatus("N");
		friend.setIsOnline("Y");
		friendDAO.save(friend);
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);
	}

	
	@GetMapping("/acceptRequest-{u_id}")
	public ResponseEntity<Friend> acceptFriend(@PathVariable("u_id") String friendID)
	{
		if(session.getAttribute("id")==null)
		{
			//log.info("NOT LOGGED IN");
			System.out.println("NOT LOGGED IN");
			friend = new Friend();
			friend.setErrorCode("100");
			friend.setErrorMsg("Please Login");
			return new ResponseEntity<Friend>(friend, HttpStatus.OK);
		}
		else
		{
			//log.info("Accepting Request");
			System.out.println("Accepting Request");
			String userID = session.getAttribute("id").toString();
			boolean value = friendDAO.accept(userID, friendID);
			if(value)
			{
				friend.setU_id(userID);
				friend.setF_id(friendID);
				friend.setErrorCode("200");
				friend.setErrorMsg("Friend Request Accepted");
				//log.info("Request Accepted");
				System.out.println("Request Accepted");
			}
			else
			{
				friend.setErrorCode("404");
				friend.setErrorMsg("Friend Request Not Accepted");				
				//log.error("Request not accepted");
				System.out.println("Request Not Accepted");
			}
			return new ResponseEntity<Friend> (friend, HttpStatus.OK);
		}
	}	
	
	
	
	@GetMapping("/rejectRequest-{u_id}")
	public ResponseEntity<Friend> rejectFriend(@PathVariable("u_id") String friendID)
	{
		if(session.getAttribute("id")==null)
		{
			//log.info("NOT LOGGED IN");
			System.out.println("NOT LOGGED IN");
			friend = new Friend();
			friend.setErrorCode("100");
			friend.setErrorMsg("Please Login");
			return new ResponseEntity<Friend>(friend, HttpStatus.OK);
		}
		else
		{
			//log.info("Accepting Request");
			System.out.println("Rejecting Request");
			String userID = session.getAttribute("id").toString();
			boolean value = friendDAO.reject(userID, friendID);
			if(value)
			{
				friend.setU_id(userID);
				friend.setF_id(friendID);
				friend.setErrorCode("200");
				friend.setErrorMsg("Friend Request Rejected");
				//log.info("Request Accepted");
				System.out.println("Request Rejected");
			}
			else
			{
				friend.setErrorCode("404");
				friend.setErrorMsg("Friend Request Not Rejected");				
				//log.error("Request not accepted");
				System.out.println("Request Not Rejected");
			}
			return new ResponseEntity<Friend> (friend, HttpStatus.OK);
		}
	}	
	
	
	@GetMapping("/listFriend")
	public ResponseEntity<List<String>> getList() 
	{
		//String loggedInUser = session.getAttribute("id").toString();
		//User loggedInUser = (User)session.getAttribute("loggedInUser");
		List<String> list = new ArrayList<String>();
		list = friendDAO.getMyFriends(session.getAttribute("id").toString());
		friend.setErrorCode("200");
		friend.setErrorMsg("Success.....");
		return new ResponseEntity<List<String>>(list, HttpStatus.OK);
	}

}
