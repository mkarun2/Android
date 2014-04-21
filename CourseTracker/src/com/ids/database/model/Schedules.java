package com.ids.database.model;

import java.util.Date;


/**
 * This class represents the entity object for the
 * table Schedules
 * @author mohan
 *
 */
public class Schedules {
	
	private Integer schedule_id;
	private Integer course_id;
	private String class_day;
	private Date start_time;
	private Date end_time;
	
	
	public Integer getSchedule_id() {
		return schedule_id;
	}
	public void setSchedule_id(Integer schedule_id) {
		this.schedule_id = schedule_id;
	}
	public Integer getCourse_id() {
		return course_id;
	}
	public void setCourse_id(Integer course_id) {
		this.course_id = course_id;
	}
	public String getClass_day() {
		return class_day;
	}
	public void setClass_day(String class_day) {
		this.class_day = class_day;
	}
	public Date getStart_time() {
		return start_time;
	}
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	
	public Schedules(){
		
	}
	
	public Schedules(String class_day,	Date start_time, Date end_time) {
		super();
		this.class_day = class_day;
		this.start_time = start_time;
		this.end_time = end_time;
	}
	
	
}
