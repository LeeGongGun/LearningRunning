package bean;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;

public class Classes {
	int class_id;
	String class_name;
	Date class_start;
	Date class_end;
	String class_state;
	String class_comment;
	int student_count;
	
	
	@Autowired
	public Classes() {
		super();
	}


	public Classes(int class_id, String class_name, Date class_start, Date class_end, String class_state,
			String class_comment, int student_count) {
		super();
		this.class_id = class_id;
		this.class_name = class_name;
		this.class_start = class_start;
		this.class_end = class_end;
		this.class_state = class_state;
		this.class_comment = class_comment;
		this.student_count = student_count;
	}



	public Classes(int class_id, String class_name, Date class_start, Date class_end, String class_state,
			String class_comment) {
		super();
		this.class_id = class_id;
		this.class_name = class_name;
		this.class_start = class_start;
		this.class_end = class_end;
		this.class_state = class_state;
		this.class_comment = class_comment;
	}


	public int getClass_id() {
		return class_id;
	}


	public void setClass_id(int class_id) {
		this.class_id = class_id;
	}


	public String getClass_name() {
		return class_name;
	}


	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}


	public Date getClass_start() {
		return class_start;
	}


	public void setClass_start(Date class_start) {
		this.class_start = class_start;
	}


	public Date getClass_end() {
		return class_end;
	}


	public void setClass_end(Date class_end) {
		this.class_end = class_end;
	}


	public String getClass_state() {
		return class_state;
	}


	public void setClass_state(String class_state) {
		this.class_state = class_state;
	}


	public String getClass_comment() {
		return class_comment;
	}


	public void setClass_comment(String class_comment) {
		this.class_comment = class_comment;
	}


	public int getStudent_count() {
		return student_count;
	}


	public void setStudent_count(int student_count) {
		this.student_count = student_count;
	}

	
}
