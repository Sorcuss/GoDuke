package com.goduke.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Recruiter {
	
	private Integer id;
	private String username;
	private String firstname;
	private String lastname;
	private String password;
	private String email;
	
	public Recruiter() {
		// TODO Auto-generated constructor stub
	}
	public Recruiter(String json) {
		Gson gson = new Gson();
		Recruiter request = gson.fromJson(json, Recruiter.class);
        this.id = request.getId();
        this.username = request.getUsername();
        this.firstname = request.getFirstname();
        this.lastname = request.getLastname();
        this.password = request.getPassword();
        this.email = request.getEmail();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return username;
	}

	public void setName(String name) {
		this.username = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
    public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
	

}
