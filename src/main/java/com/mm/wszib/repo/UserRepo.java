/**
 * @(#)UserRepo.java
 *
 *
 * @author Maciej Maciaszek
 * @version 1.00 2018/07/21
 * @description: Interface with access to user table repository
 */

package com.mm.wszib.repo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mm.wszib.entities.User;

@Repository("userRepo")
public interface UserRepo extends JpaRepository<User, Long> {
	
	/**
	 * @param id
	 * @return
	 */
	User findById(int id);
	
	/**
	 * @param userName
	 * @return
	 */
	@Query("select u from User u where u.userName = ?1")
	User findByUserName(String userName);
	
	/**
	 * @return
	 */
	@Query("from User")
	ArrayList<User> getUsers();

	/**
	 * @param userName
	 * @param email
	 * @param phoneNumber
	 * @return
	 */
	@Modifying
	@Query("update User u set u.email = ?2, u.phoneNumber = ?3 where u.userName = ?1")
	int editUser(String userName, String email, String phoneNumber);

	/**
	 * @param id
	 * @return
	 */
	int deleteById(int id);
	
	/**
	 * @param userName
	 */
	@Modifying
	@Query("delete from User u where u.userName = ?1")
	void deleteByUserName(String userName);
	
}
