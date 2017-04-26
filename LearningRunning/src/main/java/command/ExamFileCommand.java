package command;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class ExamFileCommand {
	Integer exam_id;
	List<Integer> m_id;
	List<MultipartFile> ex_file;
	
	@Autowired
	public ExamFileCommand() {
		super();
	}
	
	public Integer getExam_id() {
		return exam_id;
	}
	public void setExam_id(Integer exam_id) {
		this.exam_id = exam_id;
	}
	public List<Integer> getM_ids() {
		return m_id;
	}
	public void setM_ids(List<Integer> m_ids) {
		this.m_id = m_id;
	}
	public List<MultipartFile> getEx_file() {
		return ex_file;
	}
	public void setEx_file(List<MultipartFile> ex_file) {
		this.ex_file = ex_file;
	}
	
	
}
