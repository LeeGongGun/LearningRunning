package command;

import java.util.Date;

public class ClassListCommand {
	
	private String subject_name, subject_state, subject_comment;
	private Date subject_start, subject_end;

	public ClassListCommand(){}
	public ClassListCommand(String subject_name, Date subject_start, Date subject_end, String subject_state, String subject_comment) {
		
		this.subject_name = subject_name;
		this.subject_start = subject_start;
		this.subject_end = subject_end;
		this.subject_state = subject_state;
		this.subject_comment = subject_comment;
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
	
}
