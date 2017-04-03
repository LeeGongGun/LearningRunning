package bean;

import java.util.Date;

public class CounselManager {
	int student_id, m_id;
	private String class_name, subject_title, counsel_content, manager, student_name;
	Date counsel_date;
	
	public CounselManager(int student_id, int m_id, String class_name, String subject_title, String counsel_content,
			String manager, String student_name, Date counsel_date) {
		super();
		this.student_id = student_id;
		this.m_id = m_id;
		this.class_name = class_name;
		this.subject_title = subject_title;
		this.counsel_content = counsel_content;
		this.manager = manager;
		this.student_name = student_name;
		this.counsel_date = counsel_date;
	}

	public int getStudent_id() {
		return student_id;
	}

	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}

	public int getM_id() {
		return m_id;
	}

	public void setM_id(int m_id) {
		this.m_id = m_id;
	}

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public String getSubject_title() {
		return subject_title;
	}

	public void setSubject_title(String subject_title) {
		this.subject_title = subject_title;
	}

	public String getCounsel_content() {
		return counsel_content;
	}

	public void setCounsel_content(String counsel_content) {
		this.counsel_content = counsel_content;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getStudent_name() {
		return student_name;
	}

	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}

	public Date getCounsel_date() {
		return counsel_date;
	}

	public void setCounsel_date(Date counsel_date) {
		this.counsel_date = counsel_date;
	}
	
	
	

}
