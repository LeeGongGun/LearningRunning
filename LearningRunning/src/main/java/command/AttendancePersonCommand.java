package command;

import java.util.Date;

public class AttendancePersonCommand {
	int m_id;
	int subject_id;
	private String ss_status;
	Date ss_date;
	private String subject_name;
	Date subject_start;
	Date subject_end;
	private String subject_status;
	private String subject_comment;
	private String m_email;
	int m_pass;
	private String m_name;
	private String m_app_u_no;
	
	public AttendancePersonCommand(){}

	public AttendancePersonCommand(int m_id, int subject_id, String ss_status, Date ss_date, String subject_name,
			Date subject_start, Date subject_end, String subject_status, String subject_comment, String m_email,
			String m_name, int m_pass, String m_app_u_no) {
		this.m_id = m_id;
		this.subject_id = subject_id;
		this.ss_status = ss_status;
		this.ss_date = ss_date;
		this.subject_name = subject_name;
		this.subject_start = subject_start;
		this.subject_end = subject_end;
		this.subject_status = subject_status;
		this.subject_comment = subject_comment;
		this.m_email = m_email;
		this.m_pass = m_pass;
		this.m_name = m_name;
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

	public String getSs_status() {
		return ss_status;
	}

	public void setSs_status(String ss_status) {
		this.ss_status = ss_status;
	}

	public Date getSs_Date() {
		return ss_date;
	}

	public void setSs_Date(Date ss_date) {
		this.ss_date = ss_date;
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

	public String getSubject_status() {
		return subject_status;
	}

	public void setSubject_status(String subject_status) {
		this.subject_status = subject_status;
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

	public int getM_pass() {
		return m_pass;
	}

	public void setM_pass(int m_pass) {
		this.m_pass = m_pass;
	}

	public String getM_name() {
		return m_name;
	}

	public void setM_name(String m_name) {
		this.m_name = m_name;
	}

	public String getM_app_u_no() {
		return m_app_u_no;
	}

	public void setM_app_u_no(String m_app_u_no) {
		this.m_app_u_no = m_app_u_no;
	}
}