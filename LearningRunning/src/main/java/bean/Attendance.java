package bean;

import java.sql.Date;

public class Attendance {
	int subject_id	;
	int m_id ;	
	Date start_time	;
	Date end_time	;
	Date stop_time	;
	Date restart	;
	String a_status	;
	
	
	
	public Attendance() {
		super();
	}
	public Attendance(int subject_id, int m_id, Date start_time, Date end_time, Date stop_time, Date restart,
			String a_status) {
		super();
		this.subject_id = subject_id;
		this.m_id = m_id;
		this.start_time = start_time;
		this.end_time = end_time;
		this.stop_time = stop_time;
		this.restart = restart;
		this.a_status = a_status;
	}
	public int getSubject_id() {
		return subject_id;
	}
	public void setSubject_id(int subject_id) {
		this.subject_id = subject_id;
	}
	public int getM_id() {
		return m_id;
	}
	public void setM_id(int m_id) {
		this.m_id = m_id;
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
	public Date getStop_time() {
		return stop_time;
	}
	public void setStop_time(Date stop_time) {
		this.stop_time = stop_time;
	}
	public Date getRestart() {
		return restart;
	}
	public void setRestart(Date restart) {
		this.restart = restart;
	}
	public String getA_status() {
		return a_status;
	}
	public void setA_status(String a_status) {
		this.a_status = a_status;
	}
	
	
	
}
