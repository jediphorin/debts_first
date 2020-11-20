package com.noname.springsecurity.demo.service;

import com.noname.springsecurity.demo.crm.CrmDebt;
import com.noname.springsecurity.demo.crm.CrmDebtUpdate;
import com.noname.springsecurity.demo.crm.CrmUser;
import com.noname.springsecurity.demo.dao.DebtDao;
import com.noname.springsecurity.demo.dao.DebtRepository;
import com.noname.springsecurity.demo.dao.RoleDao;
import com.noname.springsecurity.demo.dao.UserDao;
import com.noname.springsecurity.demo.entity.Debt;
import com.noname.springsecurity.demo.entity.Role;
import com.noname.springsecurity.demo.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private DebtRepository debtRepository;
	
	private String login;
	private int id;

	// need to inject user dao
	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private DebtDao debtDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	@Transactional
	public void deleteById(int theId) {

		debtRepository.deleteById(theId);
	}

	@Override
	@Transactional
	public Debt findById(int theId) {
		
		System.out.println("HA4AJIO");

		Optional<Debt> result = debtRepository.findById(theId);
		
		System.out.println("PRODOLZHENIE");
		
		Debt theDebt = null;
		
		if (result.isPresent()) {
			theDebt = result.get();
			System.out.println("DEBT: " + theDebt);
		}
				
		else 
				throw new RuntimeException("Did not find debt id - " + theId);
		System.out.println("KOHELI");
		return theDebt;
	}
	
	@Override
	@Transactional
	public User findByUsername(String username) {
		// check the database if the user already exists
		return userDao.findByUsername(username);
	}

	@Override
	@Transactional
	public void save(CrmUser crmUser) {
		User user = new User();
		 // assign user details to the user object
		user.setUsername(crmUser.getUsername());
		user.setPassword(passwordEncoder.encode(crmUser.getPassword()));
		user.setFirstName(crmUser.getFirstName());
		user.setLastName(crmUser.getLastName());
		user.setEmail(crmUser.getEmail());

		// give user default role of "employee"
		user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_EMPLOYEE")));

		 // save user in the database
		userDao.save(user);
	}
	
	@Override
	@Transactional
	public String findByNames(String first, String last, boolean userExists) {
		
		return userDao.findByNames(first, last, userExists);
	}
	
	@Override
	@Transactional
	public boolean userExists(String first, String last) {

		return userDao.userExists(first, last);
	}
	
	//	метод админа
	@Override
	@Transactional
	public List<Debt> findAllDebts(String first, String last) {
		return debtDao.findDebtsByNames(first, last);
	}
	
	//	долги мне
	@Override
	@Transactional
	public List<Debt> findAllDebtsToMeByUsername() {
		return debtDao.findAllDebtsToMeByUsername(getLogin());
	}
	
	//	мои долги другим
	@Override
	@Transactional
	public List<Debt> findMyDebtsToPeopleByUsername() {
		return debtDao.findMyDebtsToPeopleByUsername(getLogin());
	}	

	@Override
	@Transactional
	public void createTheDebt(CrmDebt crmDebt) {
		
		//	обнуляющийся флаг существования участника долга в системе
		boolean debtorOrCreditorExists = false;
		
		//	необнуляющийся флаг существования хотя бы одного участника долга в системе
		boolean existsForFinal = false;

		Debt debt = new Debt();
		
		String debtorFirstName = crmDebt.getDebtorFirstName();
		String debtorLastName = crmDebt.getDebtorLastName();
		
		debt.setDebtorFirstName(debtorFirstName);
		debt.setDebtorLastName(debtorLastName);
		
		System.out.println("имя должника: " + debt.getDebtorFirstName());
		System.out.println("фамилия должника: " + debt.getDebtorLastName());
		
		if (userExists(debtorFirstName, debtorLastName)) {
			debtorOrCreditorExists = true;
			existsForFinal = true;
		}
			
		String debtorUsername = findByNames(debt.getDebtorFirstName(), debt.getDebtorLastName(), debtorOrCreditorExists);
		debt.setDebtorUsername(debtorUsername);
		System.out.println("юзернейм должника: " + debt.getDebtorUsername());
		
		String creditorFirstName = crmDebt.getCreditorFirstName();
		String creditorLastName = crmDebt.getCreditorLastName();
		
		debt.setCreditorFirstName(creditorFirstName);
		debt.setCreditorLastName(creditorLastName);
		
		System.out.println("имя кредитора: " + debt.getCreditorFirstName());
		System.out.println("фамилия кредитора: " + debt.getCreditorLastName());
		
		debtorOrCreditorExists = false;
		
		if (userExists(creditorFirstName, creditorLastName)) {
			debtorOrCreditorExists = true;
			existsForFinal = true;
		}
		
		String creditorUsername = findByNames(debt.getCreditorFirstName(), debt.getCreditorLastName(), debtorOrCreditorExists);
		debt.setCreditorUsername(creditorUsername);
		System.out.println("юзернейм кредитора: " + debt.getCreditorUsername());
		
		debt.setInfo(crmDebt.getInfo());
		debt.setValue(crmDebt.getValue());
		
		System.out.println("описание долга: " + debt.getInfo());
		System.out.println("величина долга: " + debt.getValue());
		
		ArrayList<User> userList = new ArrayList<>();
		userList.add(findByUsername(debtorUsername));
		userList.add(findByUsername(creditorUsername));
		
		if (existsForFinal) {
			System.out.println("UserServiceImpl: идёт создание долга");
			debtDao.save(debt);
		}
		else {
			System.out.println("ни одного из участников долга не зарегистрировано в системе!");
			debt = null;
		}
	}
	
	
	@Override
	@Transactional
	public void updateTheDebt(CrmDebtUpdate crmDebtUpdate, int id) {
		
		Debt theDebt = this.findById(id);
		theDebt.setValue(crmDebtUpdate.getValue());
		
		System.out.println("UserServiceImpl: ОБНОВЛЕНИЕ ДОЛГА");
		debtDao.save(theDebt);
	}
	

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}
	
	@Override
	public String getLogin() {return login;}
	@Override
	public void setLogin(String login) {this.login = login;}
	@Override
	public DebtRepository getDebtRepository() {return debtRepository;}

	@Override
	public int getId() {return id;}
	@Override
	public void setId(int id) {this.id = id;}
}
