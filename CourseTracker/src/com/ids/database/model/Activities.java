package com.ids.database.model;

import java.util.Date;

public class Activities {
	
	private Integer activity_id;
	private Integer course_id;
	private String activity_name;
	private String activity_type;
	private Date due_date;
	private String description;
	private String is_completed;
	private Double grade;
	private Double max_grade;
	public Integer getActivity_id() {
		return activity_id;
	}
	public void setActivity_id(Integer activity_id) {
		this.activity_id = activity_id;
	}
	public Integer getCourse_id() {
		return course_id;
	}
	public void setCourse_id(Integer course_id) {
		this.course_id = course_id;
	}
	public String getActivity_name() {
		return activity_name;
	}
	public void setActivity_name(String activity_name) {
		this.activity_name = activity_name;
	}
	public String getActivity_type() {
		return activity_type;
	}
	public void setActivity_type(String activity_type) {
		this.activity_type = activity_type;
	}
	public Date getDue_date() {
		return due_date;
	}
	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIs_completed() {
		return is_completed;
	}
	public void setIs_completed(String is_completed) {
		this.is_completed = is_completed;
	}
	public Double getGrade() {
		return grade;
	}
	public void setGrade(Double grade) {
		this.grade = grade;
	}
	public Double getMax_grade() {
		return max_grade;
	}
	public void setMax_grade(Double max_grade) {
		this.max_grade = max_grade;
	}
	
	public Activities(
			String activity_name, String activity_type, Date due_date,
			String description, String is_completed, Double grade,
			Double max_grade) {
		super();
		this.activity_name = activity_name;
		this.activity_type = activity_type;
		this.due_date = due_date;
		this.description = description;
		this.is_completed = is_completed;
		this.grade = grade;
		this.max_grade = max_grade;
	}
	
	public Activities() {

	}
	
}
