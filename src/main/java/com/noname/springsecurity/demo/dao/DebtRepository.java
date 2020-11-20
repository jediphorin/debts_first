package com.noname.springsecurity.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noname.springsecurity.demo.entity.Debt;



public interface DebtRepository extends JpaRepository<Debt, Integer> {


}