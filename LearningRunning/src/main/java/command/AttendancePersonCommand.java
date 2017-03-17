package command;

import java.util.Date;

public class AttendancePersonCommand {
	int m_id;
	private String m_name;
	private String subject_name;
	Date subject_start;
	Date subject_end;
	private String subject_comment;
	private String subject_status;
	int attendRate;
	
	public AttendancePersonCommand(){}
	
	public AttendancePersonCommand(int m_id, String m_name, String subject_name, Date subject_start, 
			Date subject_end, String subject_comment, String subject_status, int attendRate) {
		
		this.m_id = m_id;
		this.m_name = m_name;
		this.subject_name = subject_name;
		this.subject_start = subject_start;
		this.subject_end = subject_end;
		this.subject_comment = subject_comment;
		this.subject_status = subject_status;
		this.attendRate = attendRate;
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

	public String getSubject_comment() {
		return subject_comment;
	}

	public void setSubject_comment(String subject_comment) {
		this.subject_comment = subject_comment;
	}

	public String getSubject_status() {
		return subject_status;
	}

	public void setSubject_status(String subject_status) {
		this.subject_status = subject_status;
	}

	public int getAttendRate() {
		return attendRate;
	}

	public void setAttendRate(int attendRate) {
		this.attendRate = attendRate;
	}

	
	
}
