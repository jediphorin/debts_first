package com.noname.springsecurity.demo.crm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CrmDebt {

	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String debtorFirstName;

	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String debtorLastName;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String creditorFirstName;

	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String creditorLastName;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String info;

	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String value;

	public CrmDebt() {}

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

	@Override
	public String toString() {
		return "CrmDebt [debtorFirstName=" + debtorFirstName + ", debtorLastName=" + debtorLastName
				+ ", creditorFirstName=" + creditorFirstName + ", creditorLastName=" + creditorLastName + ", info="
				+ info + ", value=" + value + "]";
	}
}