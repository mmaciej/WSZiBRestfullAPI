/**
 * @(#)UserRestController.java
 *
 *
 * @author Maciej Maciaszek
 * @version 1.00 2018/07/21
 * @description: Class which describes how the controllers works
 */

package com.mm.wszib.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mm.wszib.entities.User;
import com.mm.wszib.other.CustomUri;
import com.mm.wszib.other.CustomValidation;
import com.mm.wszib.other.UserRestConstants;
import com.mm.wszib.services.UserServiceImpl;

@RestController
@CrossOrigin
public class UserRestController {
	
	@Autowired
	private UserServiceImpl usi;
	
	//LIST URIs
	@RequestMapping(value = "/", 
			method = RequestMethod.GET)
	@ResponseBody
    public CustomUri[] getURIs() {
		
        return UserRestConstants.BASIC_USER_URIs;
    }
	
	//LIST URIs or URIs and USERS
	@RequestMapping(value = UserRestConstants.GET_API, 
			method = RequestMethod.GET)
	@ResponseBody
    public ArrayList<Object[]> getAfterLogin(Authentication auth) {
    	
		ArrayList<Object[]> response = new ArrayList<Object[]>();
		
		if(usi.isAdmin(auth.getName())) {
			response.add(UserRestConstants.ADMIN_URIs);
			response.add(usi.returnUsers().toArray());
		} else if(usi.isUser(auth.getName())) {
			response.add(UserRestConstants.USER_URIs);
		}
		
        return response;
    }
	
	//ADD USER
	@RequestMapping(value = {UserRestConstants.REGISTER, UserRestConstants.ADD_NEW_USER}, 
			method = RequestMethod.POST)	
	@ResponseBody
    public ResponseEntity<String> registerUser(@RequestBody User u) {
		
		if(usi.returnUser(u.getUserName()) == null) {	
			if(CustomValidation.validateUsername(u.getUserName()) && 
					CustomValidation.validatePassword(u.getPassword()) && 
					CustomValidation.validateEmail(u.getEmail())) {
				usi.addUser(u);
				
				return new ResponseEntity<String>("resource created successfully", new HttpHeaders(), HttpStatus.CREATED);
			}
		}
		
		return new ResponseEntity<String>("resource creation error", new HttpHeaders(), HttpStatus.CONFLICT);
    }
	
	//DELETE ALL USERS
	@RequestMapping(value = UserRestConstants.DELETE_ALL_USERS, 
			method = RequestMethod.DELETE)	
	@ResponseBody
    public ResponseEntity<String> deleteAllUsers() {
		usi.deleteAllUsers();
		return new ResponseEntity<String>("resource deleted successfully", new HttpHeaders(), HttpStatus.OK);
    }
	
	//GET USER
	@RequestMapping(value = UserRestConstants.GET_USER, 
			method = RequestMethod.GET)	
	@ResponseBody
    public User getUser(@PathVariable("username") String userName) {
        return usi.returnUser(userName);
    }
	
	//EDIT USER
	@RequestMapping(value = UserRestConstants.EDIT_USER, 
			method = RequestMethod.PATCH)	
	@ResponseBody
    public ResponseEntity<String> editUser(@RequestBody User u) {
		
		int returnCode = 0;
		
		if(u != null && u.getUserName() != null) {
			returnCode = usi.editUser(u);
		}
		
		if(returnCode > 0) {
			return new ResponseEntity<String>("resource edited successfully", new HttpHeaders(), HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("resource editing error", new HttpHeaders(), HttpStatus.CONFLICT);
    }
	
	//DELETE USER
	@RequestMapping(value = UserRestConstants.DELETE_USER, 
			method = RequestMethod.DELETE)	
	@ResponseBody
    public ResponseEntity<String> deleteUser(@PathVariable("name") String userName) {
		usi.deleteUser(userName);
		return new ResponseEntity<String>("resource deleted successfully", new HttpHeaders(), HttpStatus.OK);
    }
	
}
