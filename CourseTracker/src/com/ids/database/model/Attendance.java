package com.ids.database.model;

import java.util.Date;

public class Attendance {

	
	private Integer attendance_id;
	private Integer course_id;
	private String is_attended;
	private String is_cancelled;
	private Date attendance_date;
	
	public Attendance() {
		// TODO Auto-generated constructor stub
	}
	
	public Attendance(
			String is_attended, String is_cancelled, Date attendance_date) {
		super();

		this.is_attended = is_attended;
		this.is_cancelled = is_cancelled;
		this.attendance_date = attendance_date;
	}

	public Integer getAttendance_id() {
		return attendance_id;
	}

	public void setAttendance_id(Integer attendance_id) {
		this.attendance_id = attendance_id;
	}

	public Integer getCourse_id() {
		return course_id;
	}

	public void setCourse_id(Integer course_id) {
		this.course_id = course_id;
	}

	public String getIs_attended() {
		return is_attended;
	}

	public void setIs_attended(String is_attended) {
		this.is_attended = is_attended;
	}

	public String getIs_cancelled() {
		return is_cancelled;
	}

	public void setIs_cancelled(String is_cancelled) {
		this.is_cancelled = is_cancelled;
	}

	public Date getAttendance_date() {
		return attendance_date;
	}

	public void setAttendance_date(Date attendance_date) {
		this.attendance_date = attendance_date;
	}
	
	
}
