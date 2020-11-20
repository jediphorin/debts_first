package com.noname.springsecurity.demo.dao;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.noname.springsecurity.demo.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private EntityManager entityManager;
	
	private String bufferedUsername;

	@Override
	public User findByUsername(String theUsername) {
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		// now retrieve/read from database using username
		Query<User> theQuery = currentSession.createQuery("from User where username=:uName", User.class);
		theQuery.setParameter("uName", theUsername);
		
		User theUser = null;
		try {
			theUser = theQuery.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}

		return theUser;
	}

	@Override
	public boolean userExists(String first, String last) {

		System.out.println("FIRST NAME = " + first + ", SECOND NAME = " + last);
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<User> theQuery = currentSession.createQuery("from User where firstName=:first and lastName=:last", User.class);
		theQuery.setParameter("first", first);
		theQuery.setParameter("last", last);
		
		User user = theQuery.uniqueResult();
		
		if (user != null) {
			System.out.println("UserDaoImpl: пользователь существует");
			setBufferedUsername(user.getUsername());
			return true;
		}
		else
			return false;
	}

	@Override
	public String findByNames(String first, String last, boolean userExists) {
		
		if (userExists) {
			System.out.println("FIRST NAME = " + first + ", SECOND NAME = " + last);
			String username = getBufferedUsername();
			bufferedUsername = null;
			return username;
		}
		
		else {
			System.out.println("UserDaoImpl: участника долга нету в системе");
			return null;
		}	
	}
	

	@Override
	public void save(User theUser) {
		// get current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		// create the user ... finally LOL
		currentSession.saveOrUpdate(theUser);
	}

	public String getBufferedUsername() {return bufferedUsername;}
	public void setBufferedUsername(String bufferedUsername) {this.bufferedUsername = bufferedUsername;}
}
