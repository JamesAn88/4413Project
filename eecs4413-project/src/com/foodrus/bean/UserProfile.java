package com.foodrus.bean;

public class UserProfile implements DomainBean{
	// *** Serial number
	private static final long serialVersionUID = -1274934290428726643L;
	
	private String userName;
	private String account;
	
	public UserProfile() {
		super();
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
