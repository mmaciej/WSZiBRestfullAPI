/**
 * @(#)UserServiceImpl.java
 *
 *
 * @author Maciej Maciaszek
 * @version 1.00 2018/07/21
 * @description: Class which describes how the controllers works
 */

package com.mm.wszib.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mm.wszib.entities.User;
import com.mm.wszib.other.CustomValidation;
import com.mm.wszib.other.UserRestConstants;
import com.mm.wszib.repo.RoleRepo;
import com.mm.wszib.repo.UserRepo;

@Service("userServiceImpl")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo uRepo;
	
	@Autowired
	private RoleRepo rRepo;
	
	@Autowired
	private BCryptPasswordEncoder bCrypt;
	
	/* (non-Javadoc)
	 * @see com.mm.wszib.services.UserService#isAdmin(java.lang.String)
	 */
	@Override
	public boolean isAdmin(String userName) {
		User u = returnUser(userName);
		return u != null && u.getRole().getRole().equals(UserRestConstants.ADMIN_ROLE);
	}
	
	/* (non-Javadoc)
	 * @see com.mm.wszib.services.UserService#isUser(java.lang.String)
	 */
	@Override
	public boolean isUser(String userName) {
		User u = returnUser(userName);
		return u != null && u.getRole().getRole().equals(UserRestConstants.USER_ROLE);
	}
	
	/* (non-Javadoc)
	 * @see com.mm.wszib.services.UserService#checkUserName(org.springframework.security.core.Authentication, java.lang.String)
	 */
	@Override
	public boolean checkUserName(Authentication auth, String userName) {
		User u = returnUser(auth.getName());
		return u != null && (
						u.getUserName().equals(userName) && 
						u.getRole().getRole().equals(UserRestConstants.USER_ROLE) || 
						u.getRole().getRole().equals(UserRestConstants.ADMIN_ROLE));
	}
	
	/* (non-Javadoc)
	 * @see com.mm.wszib.services.UserService#returnUsers()
	 */
	@Override
	public ArrayList<User> returnUsers() {
		return uRepo.getUsers();
	}

	/* (non-Javadoc)
	 * @see com.mm.wszib.services.UserService#returnUser(java.lang.String)
	 */
	@Override
	public User returnUser(String userName) {
		return uRepo.findByUserName(userName);
	}

	/* (non-Javadoc)
	 * @see com.mm.wszib.services.UserService#addUser(com.mm.wszib.entities.User)
	 */
	@Override
	public void addUser(User u) {
		u.setRole(rRepo.findByRoleName(UserRestConstants.USER_ROLE));
		u.setPassword(bCrypt.encode(u.getPassword()));
		uRepo.save(u);
	}

	/* (non-Javadoc)
	 * @see com.mm.wszib.services.UserService#editUser(com.mm.wszib.entities.User)
	 */
	@Override
	public int editUser(User u) {
		User userToSave = new User();
		userToSave = uRepo.findByUserName(u.getUserName());
		
		if(userToSave != null) {
			
			if(u.getPhoneNumber() != null && u.getEmail() != null) {
				userToSave.setEmail(u.getEmail());
				userToSave.setPhoneNumber(u.getPhoneNumber());
				uRepo.save(userToSave);
				return 1;
			}
			
			if(u.getEmail() != null && CustomValidation.validateEmail(u.getEmail())) {
				userToSave.setEmail(u.getEmail());
				uRepo.save(userToSave);
				return 2;
			} 
			
			if(u.getPhoneNumber() != null) {
				userToSave.setPhoneNumber(u.getPhoneNumber());
				uRepo.save(userToSave);
				return 3;
			}
		}
		
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.mm.wszib.services.UserService#deleteUser(java.lang.String)
	 */
	@Override
	public void deleteUser(String userName) {
		uRepo.deleteByUserName(userName);
	}

	/* (non-Javadoc)
	 * @see com.mm.wszib.services.UserService#deleteAllUsers()
	 */
	@Override
	public void deleteAllUsers() {
		uRepo.deleteAllInBatch();
	}
	
}
