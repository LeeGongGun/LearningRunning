package command;

import java.util.Date;

public class AttendancePersonCommand {
	int m_id, subject_id, attend_id;
	Date start_time, end_time, stop_time, restart_time;
	String attend_status, subject_name;
	Date subject_start, subject_end;
	String subject_state, subject_comment, m_email, m_name;
	int m_pass;
	String m_app_u_no;
	
	public AttendancePersonCommand(){}
	
	public AttendancePersonCommand(int m_id, int subject_id, int attend_id, Date start_time, Date end_time,
			Date stop_time, Date restart_time, String attend_status, String subject_name, Date subject_start,
			Date subject_end, String subject_state, String subject_comment, String m_email, String m_name, int m_pass,
			String m_app_u_no) {
		super();
		this.m_id = m_id;
		this.subject_id = subject_id;
		this.attend_id = attend_id;
		this.start_time = start_time;
		this.end_time = end_time;
		this.stop_time = stop_time;
		this.restart_time = restart_time;
		this.attend_status = attend_status;
		this.subject_name = subject_name;
		this.subject_start = subject_start;
		this.subject_end = subject_end;
		this.subject_state = subject_state;
		this.subject_comment = subject_comment;
		this.m_email = m_email;
		this.m_name = m_name;
		this.m_pass = m_pass;
		this.m_app_u_no = m_app_u_no;
	}


	public int getM_id() {
		return m_id;
	}


	public void setM_id(int m_id) {
		this.m_id = m_id;
	}


	public int getSubject_id() {
		return subject_id;
	}


	public void setSubject_id(int subject_id) {
		this.subject_id = subject_id;
	}


	public int getAttend_id() {
		return attend_id;
	}


	public void setAttend_id(int attend_id) {
		this.attend_id = attend_id;
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


	public String getAttend_status() {
		return attend_status;
	}


	public void setAttend_status(String attend_status) {
		this.attend_status = attend_status;
	}


	public String getSubject_name() {
		return subject_name;
	}


	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}


	public Date getSubject_start() {
		return subject_start;
	}


	public void setSubject_start(Date subject_start) {
		this.subject_start = subject_start;
	}


	public Date getSubject_end() {
		return subject_end;
	}


	public void setSubject_end(Date subject_end) {
		this.subject_end = subject_end;
	}


	public String getSubject_state() {
		return subject_state;
	}


	public void setSubject_state(String subject_state) {
		this.subject_state = subject_state;
	}


	public String getSubject_comment() {
		return subject_comment;
	}


	public void setSubject_comment(String subject_comment) {
		this.subject_comment = subject_comment;
	}


	public String getM_email() {
		return m_email;
	}


	public void setM_email(String m_email) {
		this.m_email = m_email;
	}


	public String getM_name() {
		return m_name;
	}


	public void setM_name(String m_name) {
		this.m_name = m_name;
	}


	public int getM_pass() {
		return m_pass;
	}


	public void setM_pass(int m_pass) {
		this.m_pass = m_pass;
	}


	public String getM_app_u_no() {
		return m_app_u_no;
	}


	public void setM_app_u_no(String m_app_u_no) {
		this.m_app_u_no = m_app_u_no;
	}
	
	


	


}



