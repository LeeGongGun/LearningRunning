package bean;

import org.springframework.beans.factory.annotation.Autowired;

public class Score {
	int exam_id,subject_id,	m_id,score;

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
	
	
}
