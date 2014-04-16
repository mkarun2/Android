package com.ids.database.model;

public class Instructors {
	
	private Integer instructor_id;
	private String name;
	private String phone;
	private String email;
	private String type;
	
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getInstructor_id() {
		return instructor_id;
	}
	public void setInstructor_id(Integer instructor_id) {
		this.instructor_id = instructor_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Instructors() {
	}
	
	public Instructors(Integer instructor_id, String name, String phone,String email, String type) {
		super();
		this.instructor_id = instructor_id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.type = type;
	}
	
	

}
