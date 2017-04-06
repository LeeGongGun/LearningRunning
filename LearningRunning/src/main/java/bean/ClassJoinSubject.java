package bean;

import org.springframework.beans.factory.annotation.Autowired;

public class ClassJoinSubject {
	int class_id,subject_id;
	String subject_title;
	
	@Autowired
	public ClassJoinSubject() {
		super();
	}

	public ClassJoinSubject(int class_id, int subject_id, String subject_title) {
		super();
		this.class_id = class_id;
		this.subject_id = subject_id;
		this.subject_title = subject_title;
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

	public String getSubject_title() {
		return subject_title;
	}

	public void setSubject_title(String subject_title) {
		this.subject_title = subject_title;
	}



	
}
