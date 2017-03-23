package bean;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;

public class Teacher {
	int m_id,auth_manager;
	Date auth_end_date;
	String auth_ename,auth_kname,m_email,m_name;
	
	
	
	@Autowired
	public Teacher() {
		super();
	}
	
	
	
	public Teacher(int m_id, int auth_manager, Date auth_end_date, 
			String auth_ename, String auth_kname, String m_email,
			String m_name) {
		super();
		this.m_id = m_id;
		this.auth_manager = auth_manager;
		this.auth_end_date = auth_end_date;
		this.auth_ename = auth_ename;
		this.auth_kname = auth_kname;
		this.m_email = m_email;
		this.m_name = m_name;
	}



	public int getM_id() {
		return m_id;
	}
	public void setM_id(int m_id) {
		this.m_id = m_id;
	}
	public int getAuth_manager() {
		return auth_manager;
	}
	public void setAuth_manager(int auth_manager) {
		this.auth_manager = auth_manager;
	}
	public Date getAuth_end_date() {
		return auth_end_date;
	}
	public void setAuth_end_date(Date auth_end_date) {
		this.auth_end_date = auth_end_date;
	}
	public String getAuth_ename() {
		return auth_ename;
	}
	public void setAuth_ename(String auth_ename) {
		this.auth_ename = auth_ename;
	}
	public String getAuth_kname() {
		return auth_kname;
	}
	public void setAuth_kname(String auth_kname) {
		this.auth_kname = auth_kname;
	}
	public String getM_email() {
		return m_email;
	}
	public void setM_email(String m_email) {
		this.m_email = m_email;
	}
	public String getM_name() {
		return m_name;
	}
	public void setM_name(String m_name) {
		this.m_name = m_name;
	}
	
	
}
