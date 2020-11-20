package com.noname.springsecurity.demo.dao;

import com.noname.springsecurity.demo.entity.User;

public interface UserDao {

    public User findByUsername(String username);
    
    public void save(User user);
    
    public String findByNames(String first, String last, boolean userExists);
    
    //	ПОЛЬЗОВАТЕЛЬ СУЩЕСТВУЕТ!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public boolean userExists(String first, String last);
    
}
