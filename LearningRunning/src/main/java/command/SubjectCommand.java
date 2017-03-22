package command;

import java.sql.Date;

import oracle.sql.DATE;

public class SubjectCommand {
	int subject_id;
	String subject_name;
	Date subject_start;
	Date subject_end;
	String subject_state;
	String subject_comment;
	
	
	
	public SubjectCommand() {
		super();
		this.subject_id = 0;
		this.subject_name = "";
		this.subject_start = new Date(new java.util.Date().getTime());
		this.subject_end = new Date(new java.util.Date().getTime());
		this.subject_state = "";
		this.subject_comment = "";
		
	}
	public int getSubject_id() {
		return subject_id;
	}
	public void setSubject_id(int subject_id) {
		this.subject_id = ((Integer)subject_id==null)?0:subject_id;
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
		if (subject_start==null) {
			java.util.Date today = new java.util.Date();
			this.subject_start = new Date(today.getTime());
		}else{
			this.subject_start = subject_start;
		}
	}
	public Date getSubject_end() {
		return subject_end;
	}
	public void setSubject_end(Date subject_end) {
		if (subject_end==null) {
			java.util.Date today = new java.util.Date();
			this.subject_end = new Date(today.getTime());
		}else{
			this.subject_end = subject_end;
		}
	}
	public String getSubject_state() {
		return subject_state;
	}
	public void setSubject_state(String subject_state) {
		this.subject_state = (subject_state==null)?"":subject_state;
	}
	public String getSubject_comment() {
		return subject_comment;
	}
	public void setSubject_comment(String subject_comment) {
		this.subject_comment = (subject_comment==null)?"":subject_comment;
	}

	
}
