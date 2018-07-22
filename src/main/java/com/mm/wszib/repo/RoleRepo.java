/**
 * @(#)RoleRepo.java
 *
 *
 * @author Maciej Maciaszek
 * @version 1.00 2018/07/21
 * @description: Interface with access to role table repository
 */

package com.mm.wszib.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mm.wszib.entities.Role;

@Repository("roleRepo")
public interface RoleRepo extends JpaRepository<Role, Long> {
	
	/**
	 * @param role
	 * @return
	 */
	@Query("select r from Role r where r.role = ?1")
	Role findByRoleName(String role);
	
}
