/**
 * @(#)UserService.java
 *
 *
 * @author Maciej Maciaszek
 * @version 1.00 2018/07/21
 * @description: Interface with possible user actions
 */

package com.mm.wszib.services;

import java.util.ArrayList;

import org.springframework.security.core.Authentication;

import com.mm.wszib.entities.User;

public interface UserService {
	
	/**
	 * Checks if User with passed by 
	 * parameter username has an ADMIN role
	 * 
	 * @param userName
	 * @return
	 */
	boolean isAdmin(String userName);
	
	/**
	 * Checks if User with passed by 
	 * parameter username has an USER role
	 * 
	 * @param userName
	 * @return
	 */
	boolean isUser(String userName);
	
	/**
	 * Checks if already logged in User 
	 * can access the content depending on his role
	 * 
	 * @param auth
	 * @param userName
	 * @return
	 */
	boolean checkUserName(Authentication auth, String userName);
	
	/**
	 * Returns all users from the database
	 * 
	 * @return
	 */
	ArrayList<User> returnUsers();
	
	/**
	 * Returns specific User object 
	 * from the database
	 * defined by username parameter
	 * 
	 * @param userName
	 * @return
	 */
	User returnUser(String userName);
	
	/**
	 * Creates new User in the database
	 * 
	 * @param u
	 */
	void addUser(User u);
	
	/**
	 * Edits User in the database
	 * 
	 * @param u
	 * @return
	 */
	int editUser(User u);
	
	/**
	 * Deletes User in the 
	 * database by username parameter
	 * 
	 * @param userName
	 */
	void deleteUser(String userName);
	
	/**
	 * Deletes all Users in the database
	 */
	void deleteAllUsers();
	
}
