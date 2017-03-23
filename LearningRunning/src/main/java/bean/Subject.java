package bean;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;

public class Subject {
	int subject_id;
	String subject_name;
	Date subject_start;
	Date subject_end;
	String subject_state;
	String subject_comment;
	int student_count;
	
	
	@Autowired
	public Subject() {
		super();
	}


	public Subject(int subject_id, String subject_name, Date subject_start, Date subject_end, String subject_state,
			String subject_comment, int student_count) {
		super();
		this.subject_id = subject_id;
		this.subject_name = subject_name;
		this.subject_start = subject_start;
		this.subject_end = subject_end;
		this.subject_state = subject_state;
		this.subject_comment = subject_comment;
		this.student_count = student_count;
	}



	public Subject(int subject_id, String subject_name, Date subject_start, Date subject_end, String subject_state,
			String subject_comment) {
		super();
		this.subject_id = subject_id;
		this.subject_name = subject_name;
		this.subject_start = subject_start;
		this.subject_end = subject_end;
		this.subject_state = subject_state;
		this.subject_comment = subject_comment;
	}
	
	public int getSubject_id() {
		return subject_id;
	}
	public void setSubject_id(int subject_id) {
		this.subject_id = subject_id;
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

	public int getStudent_count() {
		return student_count;
	}

	public void setStudent_count(int student_count) {
		this.student_count = student_count;
	}
	
	
}
