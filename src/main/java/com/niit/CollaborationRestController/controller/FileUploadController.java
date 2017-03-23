package com.niit.CollaborationRestController.controller;

import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.niit.CollaborationBackEnd.dao.FileUploadDAO;
import com.niit.CollaborationBackEnd.dao.UserDAO;
import com.niit.CollaborationBackEnd.model.FileUpload;
import com.niit.CollaborationBackEnd.model.User;

@RestController
public class FileUploadController {

	@Autowired
	private FileUploadDAO fileUploadDAO;

	@Autowired
	User user;

	@Autowired
	UserDAO userDAO;
	
	@RequestMapping(value = "/doUpload", method = RequestMethod.POST)
	public String handleFileUpload(HttpServletRequest request, HttpSession session,
			@RequestParam("uploadFile") CommonsMultipartFile uploadFile) throws Exception {
	
		User user = userDAO.getUser(session.getAttribute("id").toString());
		//User user = (User)session.getAttribute("id");
		if (user == null)
			throw new RuntimeException("Not logged in");
		System.out.println("USER is " + user.getId());
		if (uploadFile != null) {
			CommonsMultipartFile aFile = uploadFile;
			System.out.println("Saving file: " + aFile.getOriginalFilename());
			FileUpload fileUpload = new FileUpload();
			
			fileUpload.setUsername(user.getNam());
			fileUpload.setFileName(aFile.getOriginalFilename());
			fileUpload.setData(aFile.getBytes());
			
			fileUploadDAO.save(fileUpload);
			FileUpload getFileUpload = fileUploadDAO.getFile(user.getNam());
			String name = getFileUpload.getFileName();
			System.out.println(getFileUpload.getData());
			byte[] imagefiles = getFileUpload.getData();
			try {
				String path = "E:/workspace1/CollaborationFrontEnd/WebContent/Images/" + user.getNam();
				File file = new File(path);
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(imagefiles);
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "<h1>Successfully uploaded the Profile Picture</h1>";
	}
}