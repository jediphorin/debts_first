package com.noname.springsecurity.demo.crm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CrmDebtUpdate {

	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String value;

	private int id;

	public CrmDebtUpdate() {}

	public String getValue() {return value;}
	public void setValue(String value) {this.value = value;}
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}

	@Override
	public String toString() {
		return "CrmDebtUpdate [value=" + value + "]";
	}

}