package bean;

import java.sql.Date;
import java.sql.Timestamp;

public class Attendance {
	int attend_id,class_id,m_id;
	Date attend_date;
	String attend_status,start_time,end_time,stop_time,restart_time;
	
	
	
	public Attendance(int attend_id, int class_id, int m_id, Date attend_date, String attend_status, String start_time,
			String end_time, String stop_time, String restart_time) {
		super();
		this.attend_id = attend_id;
		this.class_id = class_id;
		this.m_id = m_id;
		this.attend_date = attend_date;
		this.attend_status = attend_status;
		this.start_time = start_time;
		this.end_time = end_time;
		this.stop_time = stop_time;
		this.restart_time = restart_time;
	}
	
	public int getAttend_id() {
		return attend_id;
	}
	public void setAttend_id(int attend_id) {
		this.attend_id = attend_id;
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
	public Date getAttend_date() {
		return attend_date;
	}
	public void setAttend_date(Date attend_date) {
		this.attend_date = attend_date;
	}
	public String getAttend_status() {
		return attend_status;
	}
	public void setAttend_status(String attend_status) {
		this.attend_status = attend_status;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getStop_time() {
		return stop_time;
	}
	public void setStop_time(String stop_time) {
		this.stop_time = stop_time;
	}
	public String getRestart_time() {
		return restart_time;
	}
	public void setRestart_time(String restart_time) {
		this.restart_time = restart_time;
	}
	
}
