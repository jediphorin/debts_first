package com.noname.springsecurity.demo.dao;

import java.util.List;

import com.noname.springsecurity.demo.entity.Debt;

public interface DebtDao {
	
	//	метод для админа
	public List<Debt> findDebtsByNames(String rstName, String ndName);
	
	public void save(Debt theDebt);
	
	public List<Debt> findAllDebtsToMeByUsername(String username);

	public List<Debt> findMyDebtsToPeopleByUsername(String username);
}