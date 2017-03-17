package bean;

import java.sql.Date;

public class Attendance {
	int subject_id	;
	int m_id ;	
	String m_name ;	
	Date start_time	;
	Date end_time	;
	Date stop_time	;
	Date restart_time	;
	String attendance_status	;
	
	
	
	public Attendance() {
		super();
	}
	public Attendance(int subject_id, int m_id, String m_name, Date start_time, Date end_time, Date stop_time,
			Date restart, String attendance_status) {
		super();
		this.subject_id = subject_id;
		this.m_id = m_id;
		this.m_name = m_name;
		this.start_time = start_time;
		this.end_time = end_time;
		this.stop_time = stop_time;
		this.restart_time = restart_time;
		this.attendance_status = attendance_status;
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
	public Date getRestart_time() {
		return restart_time;
	}
	public void setRestart_time(Date restart_time) {
		this.restart_time = restart_time;
	}
	public String getAttendance_status() {
		return attendance_status;
	}
	public void setAttendance_status(String attendance_status) {
		this.attendance_status = attendance_status;
	}

	
	
	
}
