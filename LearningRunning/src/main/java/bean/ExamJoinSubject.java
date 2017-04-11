package bean;

import org.springframework.beans.factory.annotation.Autowired;

public class ExamJoinSubject {
	int exam_id,subject_id;
	String subject_title;
	
	@Autowired
	public ExamJoinSubject() {
		super();
	}
	
	

	public ExamJoinSubject(int exam_id, int subject_id, String subject_title) {
		super();
		this.exam_id = exam_id;
		this.subject_id = subject_id;
		this.subject_title = subject_title;
	}



	public int getExam_id() {
		return exam_id;
	}

	public void setExam_id(int exam_id) {
		this.exam_id = exam_id;
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
