package com.ids.database.model;

import java.util.Date;

public class StudySession {
	
	private Integer study_session_id;
	private Integer course_id;
	private Date study_date;
	private Integer num_sessions_completed;
	
	public StudySession(Date study_date, Integer num_sessions_completed) {
		super();
		this.study_date = study_date;
		this.num_sessions_completed = num_sessions_completed;
	}
	
	public StudySession() {
		// TODO Auto-generated constructor stub
	}

	public Integer getStudy_session_id() {
		return study_session_id;
	}

	public void setStudy_session_id(Integer study_session_id) {
		this.study_session_id = study_session_id;
	}

	public Integer getCourse_id() {
		return course_id;
	}

	public void setCourse_id(Integer course_id) {
		this.course_id = course_id;
	}

	public Date getStudy_date() {
		return study_date;
	}

	public void setStudy_date(Date study_date) {
		this.study_date = study_date;
	}

	public Integer getNum_sessions_completed() {
		return num_sessions_completed;
	}

	public void setNum_sessions_completed(Integer num_sessions_completed) {
		this.num_sessions_completed = num_sessions_completed;
	}
	
	
	
}
