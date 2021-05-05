package com.solar.www.model;

public class User {

	private int userId;
	private String userName;
	private String passWord;
	private String firstName;
	private String lastName;
	private String email;
	private int roleId;

	public User(int userId, String userName, String passWord, String firstName, String lastName, String email,
			int roleId) {
		this.userId = userId;
		this.userName = userName;
		this.passWord = passWord;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.roleId = roleId;
	}

	public int getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public int getRoleId() {
		return roleId;
	}

}
