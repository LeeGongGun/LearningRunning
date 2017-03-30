package bean;

import org.springframework.beans.factory.annotation.Autowired;

public class ClassJoinMem {
	int class_id,m_id;
	String class_name,m_name;
	
	@Autowired
	public ClassJoinMem() {
		super();
	}
	public ClassJoinMem(int m_id, int class_id,String m_name,String class_name) {
		super();
		this.class_id = class_id;
		this.m_id = m_id;
		this.class_name = class_name;
		this.m_name = m_name;
	}
	
	public int getClass_id() {
		return class_id;
	}
	public void setClass_id(int class_id) {
		this.class_id = class_id;
	}
	public int getM_id() {
		return m_id;
	}
	public void setM_id(int m_id) {
		this.m_id = m_id;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public String getM_name() {
		return m_name;
	}
	public void setM_name(String m_name) {
		this.m_name = m_name;
	}
	
	
}
