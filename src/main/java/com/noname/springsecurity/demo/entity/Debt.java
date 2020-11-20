package com.noname.springsecurity.demo.entity;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "debt")
public class Debt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_of_debt")
	//private Integer idOfDebt;
	//private Long idOfDebt;
	private int idOfDebt;

	@Column(name = "debtor_first_name")
	private String debtorFirstName;
	
	@Column(name = "debtor_last_name")
	private String debtorLastName;
	
	@Column(name = "debtor_username")
	private String debtorUsername;
	
	@Column(name = "creditor_first_name")
	private String creditorFirstName;
	
	@Column(name = "creditor_last_name")
	private String creditorLastName;
	
	@Column(name = "creditor_username")
	private String creditorUsername;

	@Column(name = "info")
	private String info;

	@Column(name = "value")
	private String value;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "users_debts",
	joinColumns = @JoinColumn(name = "debt_id"),
	inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Collection<User> users;

	public Debt() {}

	public Debt(Long idOfDebt, String debtorFirstName, String debtorLastName, String debtorUsername,
			String creditorFirstName, String creditorLastName, String creditorUsername, String info, String value,
			Collection<User> users) {
		//this.idOfDebt = idOfDebt;
		this.debtorFirstName = debtorFirstName;
		this.debtorLastName = debtorLastName;
		this.debtorUsername = debtorUsername;
		this.creditorFirstName = creditorFirstName;
		this.creditorLastName = creditorLastName;
		this.creditorUsername = creditorUsername;
		this.info = info;
		this.value = value;
		this.users = users;
	}

	public int getIdOfDebt() {return idOfDebt;}
	public void setIdOfDebt(int idOfDebt) {this.idOfDebt = idOfDebt;}
	
	public String getDebtorFirstName() {return debtorFirstName;}
	public void setDebtorFirstName(String debtorFirstName) {this.debtorFirstName = debtorFirstName;}
	public String getDebtorLastName() {return debtorLastName;}
	public void setDebtorLastName(String debtorLastName) {this.debtorLastName = debtorLastName;}
	public String getCreditorFirstName() {return creditorFirstName;}
	public void setCreditorFirstName(String creditorFirstName) {this.creditorFirstName = creditorFirstName;}
	public String getCreditorLastName() {return creditorLastName;}
	public void setCreditorLastName(String creditorLastName) {this.creditorLastName = creditorLastName;}
	public String getInfo() {return info;}
	public void setInfo(String info) {this.info = info;}
	public String getValue() {return value;}
	public void setValue(String value) {this.value = value;}
	public String getDebtorUsername() {return debtorUsername;}
	public void setDebtorUsername(String debtorUsername) {this.debtorUsername = debtorUsername;}
	public String getCreditorUsername() {return creditorUsername;}
	public void setCreditorUsername(String creditorUsername) {this.creditorUsername = creditorUsername;}

	public Collection<User> getUsers() {return users;}
	public void setUsers(Collection<User> users) {this.users = users;}

	@Override
	public String toString() {return "Долг №" + idOfDebt + 
									"\nдолжник " + debtorFirstName + " " + debtorLastName + " (" + debtorUsername +")" + 
									"\nкредитор " + creditorFirstName + " " + creditorLastName + " (" + creditorUsername + ")" + 
									"\nописание: " + info + 
									"\nсумма = " + value + "\n";}

}