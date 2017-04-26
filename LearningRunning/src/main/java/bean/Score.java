package bean;

import org.springframework.beans.factory.annotation.Autowired;

public class Score {
	int exam_id,subject_id,	m_id,score;
	String m_image,ex_img,ori_file_name;
	@Autowired
	public Score() {
		super();
	}

	public Score(int exam_id, int subject_id, int m_id, int score) {
		super();
		this.exam_id = exam_id;
		this.subject_id = subject_id;
		this.m_id = m_id;
		this.score = score;
	}

	public Score(int exam_id, int subject_id, int m_id, int score, String m_image, String ex_img,
			String ori_file_name) {
		super();
		this.exam_id = exam_id;
		this.subject_id = subject_id;
		this.m_id = m_id;
		this.score = score;
		this.m_image = m_image;
		this.ex_img = ex_img;
		this.ori_file_name = ori_file_name;
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

	public int getM_id() {
		return m_id;
	}

	public void setM_id(int m_id) {
		this.m_id = m_id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getM_image() {
		return m_image;
	}

	public void setM_image(String m_image) {
		this.m_image = m_image;
	}

	public String getEx_img() {
		return ex_img;
	}

	public void setEx_img(String ex_img) {
		this.ex_img = ex_img;
	}

	public String getOri_file_name() {
		return ori_file_name;
	}

	public void setOri_file_name(String ori_file_name) {
		this.ori_file_name = ori_file_name;
	}
	
	
}
