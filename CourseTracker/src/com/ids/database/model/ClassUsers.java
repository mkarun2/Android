package com.ids.database.model;

public class ClassUsers {
	
	private Integer course_id;
	private Integer student_id;
	
	public ClassUsers() {
		// TODO Auto-generated constructor stub
	}

	public ClassUsers(Integer course_id, Integer student_id) {
		super();
		this.course_id = course_id;
		this.student_id = student_id;
	}

	public Integer getCourse_id() {
		return course_id;
	}

	public void setCourse_id(Integer course_id) {
		this.course_id = course_id;
	}

	public Integer getStudent_id() {
		return student_id;
	}

	public void setStudent_id(Integer student_id) {
		this.student_id = student_id;
	}
	
	
}
