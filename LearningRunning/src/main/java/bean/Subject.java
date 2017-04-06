package bean;

import org.springframework.beans.factory.annotation.Autowired;

public class Subject {
	int subject_id;
	String subject_title;
	String subject_comment;
	
	
	@Autowired
	public Subject() {
		super();
	}
	public Subject(int subject_id, String subject_title, String subject_comment) {
		super();
		this.subject_id = subject_id;
		this.subject_title = subject_title;
		this.subject_comment = subject_comment;
	}
	public int getSubject_id() {
		return subject_id;
	}
	public void setSubject_id(int subject_id) {
		this.subject_id = subject_id;
	}
	public String getSubject_title() {
		return subject_title;
	}
	public void setSubject_title(String subject_title) {
		this.subject_title = subject_title;
	}
	public String getSubject_comment() {
		return subject_comment;
	}
	public void setSubject_comment(String subject_comment) {
		this.subject_comment = subject_comment;
	}
	
	
}
