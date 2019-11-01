package com.goduke.model;

import java.nio.charset.Charset;
import java.util.Random;

import com.google.gson.Gson;

public class Candidate {
	private Integer id;
	private String username;
	private String code;
	
	public Candidate() {
		// TODO Auto-generated constructor stub
	}
	public Candidate(String json) {
		Gson gson = new Gson();
		Candidate request = gson.fromJson(json, Candidate.class);
        this.id = request.getId();
        this.username = request.getUsername();
        this.code = generateRandomCode();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	private String generateRandomCode() {
	    byte[] array = new byte[4];
	    new Random().nextBytes(array);
	    return new String(array, Charset.forName("UTF-8"));
	}
	

}
