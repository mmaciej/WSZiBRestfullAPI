/**
 * @(#)UserRestConstants.java
 *
 *
 * @author Maciej Maciaszek
 * @version 1.00 2018/07/21
 * @description: Class which contains all constants used by app
 */

package com.mm.wszib.other;

public class UserRestConstants {
	
	public static final String LOGIN = "/login";
	public static final String LOGOUT = "/logout";
	public static final String REGISTER = "/register";				//POST
	public static final String GET_API = "/users";					//GET
	public static final String ADD_NEW_USER = "/users";				//POST
	public static final String DELETE_ALL_USERS = "/users";			//DELETE
	public static final String GET_USER = "/users/{username}";		//GET
	public static final String EDIT_USER = "/users/{username}";		//PATCH
	public static final String DELETE_USER = "/users/{username}";	//DELETE
	
	public static final String ADMIN_ROLE = "ADMIN";
	public static final String USER_ROLE = "USER";
	
	public static final CustomUri[] ADMIN_URIs = {new CustomUri(LOGIN,"login"), 
			new CustomUri(LOGOUT,"logout"), new CustomUri(REGISTER,"register"), 
			new CustomUri(GET_API,"getapi"), new CustomUri(DELETE_ALL_USERS,"deleteallusers"), 
			new CustomUri(GET_USER,"getuser"), new CustomUri(EDIT_USER,"edituser"), 
			new CustomUri(DELETE_USER,"deleteuser")};
	
	public static final CustomUri[] BASIC_USER_URIs = {new CustomUri(LOGIN,"login"), 
			new CustomUri(REGISTER,"register"), new CustomUri(ADD_NEW_USER,"addnewuser")};
	
	public static final CustomUri[] USER_URIs = {new CustomUri(LOGIN,"login"), 
			new CustomUri(LOGOUT,"logout"), new CustomUri(REGISTER,"register"), 
			new CustomUri(GET_API,"getapi"), new CustomUri(ADD_NEW_USER,"addnewuser"), 
			new CustomUri(GET_USER,"getuser"), new CustomUri(EDIT_USER,"edituser")};
	
}