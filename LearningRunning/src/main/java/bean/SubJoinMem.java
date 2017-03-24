package bean;

import org.springframework.beans.factory.annotation.Autowired;

public class SubJoinMem {
	int subject_id,m_id;
	String subject_name,m_name;
	
	@Autowired
	public SubJoinMem() {
		super();
	}
	public SubJoinMem(int m_id, int subject_id,String m_name,String subject_name) {
		super();
		this.subject_id = subject_id;
		this.m_id = m_id;
		this.subject_name = subject_name;
		this.m_name = m_name;
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
	public String getSubject_name() {
		return subject_name;
	}
	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}
	public String getM_name() {
		return m_name;
	}
	public void setM_name(String m_name) {
		this.m_name = m_name;
	}
	
	
}
