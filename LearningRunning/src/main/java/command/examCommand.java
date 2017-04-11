package command;

import org.springframework.beans.factory.annotation.Autowired;

public class examCommand {
	int class_id;
	int subject_id;
	int m_id;
	
	
	@Autowired
	public examCommand() {
		super();
		this.class_id = 0;
		this.subject_id = 0;
		this.m_id = 0;
	}
	public int getClass_id() {
		return class_id;
	}
	public void setClass_id(int class_id) {
		this.class_id = class_id;
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
	
	
}
