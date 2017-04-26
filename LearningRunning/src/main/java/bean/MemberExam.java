package bean;

import org.springframework.beans.factory.annotation.Autowired;

public class MemberExam {
	int exam_id,m_id;
	String ex_img,ori_file_name;
	
	@Autowired
	public MemberExam() {
		super();
	}

	public MemberExam(int exam_id, int m_id, String ex_img, String ori_file_name) {
		super();
		this.exam_id = exam_id;
		this.m_id = m_id;
		this.ex_img = ex_img;
		this.ori_file_name = ori_file_name;
	}


	public int getExam_id() {
		return exam_id;
	}


	public void setExam_id(int exam_id) {
		this.exam_id = exam_id;
	}


	public int getM_id() {
		return m_id;
	}


	public void setM_id(int m_id) {
		this.m_id = m_id;
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

	@Override
	public String toString() {
		return super.toString();
	}
	
	
	
}
