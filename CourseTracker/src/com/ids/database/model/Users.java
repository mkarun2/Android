package com.ids.database.model;

public class Users {

	String first_name;
	String last_name;
	Integer student_id;
	String school_name;
	String description;
	String other;
	
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public Integer getStudent_id() {
		return student_id;
	}
	public void setStudent_id(Integer student_id) {
		this.student_id = student_id;
	}
	public String getSchool_name() {
		return school_name;
	}
	public void setSchool_name(String school_name) {
		this.school_name = school_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	
	public Users(){
		
	}
	
	public Users(String first_name, String last_name, String school_name, String description, String other) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.school_name = school_name;
		this.description = description;
		this.other = other;
	}
	
	
}
