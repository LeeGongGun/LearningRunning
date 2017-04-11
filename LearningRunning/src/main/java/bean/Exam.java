package bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

public class Exam {
	int exam_id;
	int class_id;
	String exam_title;
	String class_name;
	String exam_date;
	
	@Autowired
	public Exam() {
		super();
		this.exam_id = 0;
		this.class_id = 0;
		this.exam_title = "";
		this.class_name = "";
		this.exam_date = (new SimpleDateFormat("yy/MM/dd")).format(new Date());
	}
	public Exam(int exam_id, int class_id, String exam_title, String class_name, String exam_date) {
		super();
		this.exam_id = exam_id;
		this.class_id = class_id;
		this.exam_title = exam_title;
		this.class_name = class_name;
		this.exam_date = exam_date;
	}
	public int getExam_id() {
		return exam_id;
	}
	public void setExam_id(int exam_id) {
		this.exam_id = exam_id;
	}
	public int getClass_id() {
		return class_id;
	}
	public void setClass_id(int class_id) {
		this.class_id = class_id;
	}
	public String getExam_title() {
		return exam_title;
	}
	public void setExam_title(String exam_title) {
		this.exam_title = exam_title;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public String getExam_date() {
		return exam_date;
	}
	public void setExam_date(String exam_date) {
		this.exam_date = exam_date;
	}
	
	
}
