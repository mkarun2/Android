package com.ids.database.model;

import java.util.Date;

public class Classes {
	
	private Integer course_id;
	private String course_number;
	private String course_name;
	private String description;
	private String location;
	private Date start_date;
	private Date end_date;
	private Integer num_target_week_sessions;
	
	
	public Classes(String course_number, String course_name,
			String description, String location, Date start_date,
			Date end_date, Integer num_target_week_sessions) {
		super();
		this.course_number = course_number;
		this.course_name = course_name;
		this.description = description;
		this.location = location;
		this.start_date = start_date;
		this.end_date = end_date;
		this.num_target_week_sessions = num_target_week_sessions;
	}

	
	public Classes() {
		// TODO Auto-generated constructor stub
	}

	public Integer getCourse_id() {
		return course_id;
	}


	public void setCourse_id(Integer course_id) {
		this.course_id = course_id;
	}


	public String getCourse_number() {
		return course_number;
	}


	public void setCourse_number(String course_number) {
		this.course_number = course_number;
	}


	public String getCourse_name() {
		return course_name;
	}


	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public Date getStart_date() {
		return start_date;
	}


	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}


	public Date getEnd_date() {
		return end_date;
	}


	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}


	public Integer getNum_target_week_sessions() {
		return num_target_week_sessions;
	}


	public void setNum_target_week_sessions(Integer num_target_week_sessions) {
		this.num_target_week_sessions = num_target_week_sessions;
	}
	
	
}
