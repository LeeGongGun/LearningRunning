package bean;

import java.sql.Date;
import java.sql.Time;

public class TempAttendance {
	int temp_id,class_id,m_id;
	Time check_time;
	//String status;
	String temp_date;
	
	
	public TempAttendance(int temp_id, int class_id, int m_id, Time check_time, String temp_date) {
		super();
		this.temp_id = temp_id;
		this.class_id = class_id;
		this.m_id = m_id;
		this.check_time = check_time;
	//	this.status = status;
		this.temp_date = temp_date;
	}
	public int getTemp_id() {
		return temp_id;
	}
	public void setTemp_id(int temp_id) {
		this.temp_id = temp_id;
	}
	public int getClass_id() {
		return class_id;
	}
	public void setClass_id(int class_id) {
		this.class_id = class_id;
	}
	public int getM_id() {
		return m_id;
	}
	public void setM_id(int m_id) {
		this.m_id = m_id;
	}
	public Time getCheck_time() {
		return check_time;
	}
	public void setCheck_time(Time check_time) {
		this.check_time = check_time;
	}
//	public String getStatus() {
//		return status;
//	}
//	public void setStatus(String status) {
//		this.status = status;
//	}
	public String getTemp_date() {
		return temp_date;
	}
	public void setTemp_date(String temp_date) {
		this.temp_date = temp_date;
	}
	
}
