package bean;

import java.sql.Date;

public class TempAttendance {
	int temp_id,class_id,m_id;
	Date check_time;
	String status;
	
	
	public TempAttendance(int temp_id, int class_id, int m_id, Date check_time, String status) {
		super();
		this.temp_id = temp_id;
		this.class_id = class_id;
		this.m_id = m_id;
		this.check_time = check_time;
		this.status = status;
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
	public Date getCheck_time() {
		return check_time;
	}
	public void setCheck_time(Date check_time) {
		this.check_time = check_time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
