package bean;

import java.sql.Timestamp;

public class Attendance {
	int subject_id	;
	int m_id ;	
	String m_name ;	
	Timestamp start_time	;
	Timestamp end_time	;
	Timestamp stop_time	;
	Timestamp restart_time	;
	String attend_status;
	public Attendance() {
		super();
	}
	public Attendance(int subject_id, int m_id, String m_name, Timestamp start_time, Timestamp end_time,
			Timestamp stop_time, Timestamp restart_time, String attend_status) {
		super();
		this.subject_id = subject_id;
		this.m_id = m_id;
		this.m_name = m_name;
		this.start_time = start_time;
		this.end_time = end_time;
		this.stop_time = stop_time;
		this.restart_time = restart_time;
		this.attend_status = attend_status;
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
	public String getM_name() {
		return m_name;
	}
	public void setM_name(String m_name) {
		this.m_name = m_name;
	}
	public Timestamp getStart_time() {
		return start_time;
	}
	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}
	public Timestamp getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}
	public Timestamp getStop_time() {
		return stop_time;
	}
	public void setStop_time(Timestamp stop_time) {
		this.stop_time = stop_time;
	}
	public Timestamp getRestart_time() {
		return restart_time;
	}
	public void setRestart_time(Timestamp restart_time) {
		this.restart_time = restart_time;
	}
	public String getAttend_status() {
		return attend_status;
	}
	public void setAttend_status(String attend_status) {
		this.attend_status = attend_status;
	}
	
}
