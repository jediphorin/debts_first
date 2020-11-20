package com.noname.springsecurity.demo.dao;

import com.noname.springsecurity.demo.entity.Role;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);
	
}
