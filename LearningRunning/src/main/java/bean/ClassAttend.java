package bean;

import java.sql.Date;

public class ClassAttend {
	int class_id,m_id;
	String class_name;
	String class_state;
	String attendings,noAttendings,outings,lateings,outComings;
	
	
	public ClassAttend() {
		super();
	}
	public ClassAttend(int class_id, int m_id, String class_name, String class_state, String attendings,
			String noAttendings, String outings, String lateings, String outComings) {
		super();
		this.class_id = class_id;
		this.m_id = m_id;
		this.class_name = class_name;
		this.class_state = class_state;
		this.attendings = (attendings==null)?"0":attendings;
		this.noAttendings = (noAttendings==null)?"0":noAttendings;
		this.outings = (outings==null)?"0":outings;
		this.lateings = (lateings==null)?"0":lateings;
		this.outComings = (outComings==null)?"0":outComings;
	}
	public ClassAttend(int class_id, String class_name, String class_state, String attendings,
			String noAttendings, String outings, String lateings, String outComings) {
		super();
		this.class_id = class_id;
		this.class_name = class_name;
		this.class_state = class_state;
		this.attendings = (attendings==null)?"0":attendings;
		this.noAttendings = (noAttendings==null)?"0":noAttendings;
		this.outings = (outings==null)?"0":outings;
		this.lateings = (lateings==null)?"0":lateings;
		this.outComings = (outComings==null)?"0":outComings;
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
	public String getClass_state() {
		return class_state;
	}
	public void setClass_state(String class_state) {
		this.class_state = class_state;
	}
	public String getAttendings() {
		return attendings;
	}
	public void setAttendings(String attendings) {
		this.attendings = attendings;
	}
	public String getNoAttendings() {
		return noAttendings;
	}
	public void setNoAttendings(String noAttendings) {
		this.noAttendings = noAttendings;
	}
	public String getOutings() {
		return outings;
	}
	public void setOutings(String outings) {
		this.outings = outings;
	}
	public String getLateings() {
		return lateings;
	}
	public void setLateings(String lateings) {
		this.lateings = lateings;
	}
	public String getOutComings() {
		return outComings;
	}
	public void setOutComings(String outComings) {
		this.outComings = outComings;
	}
	
	

}
