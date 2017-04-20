package bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class AuthMember {
	private int m_id;
	private String m_email;
	private String m_name;
	private String m_pass;
	private String auth_ename;
	private String m_image;
	private MultipartFile m_file; 
	private boolean m_app_u_no;
	
	
	@Autowired
	public AuthMember() {
		super();
	}
	public AuthMember(int m_id, String m_email, String m_name,String m_pass, String auth_ename,String m_image, String m_app_u_no) {
		super();
		this.m_id = m_id;
		this.m_email = m_email;
		this.m_name = m_name;
		this.m_pass = m_pass;
		this.auth_ename = auth_ename;
		this.m_image = (m_image==null)?"":m_image;
		this.m_app_u_no = (m_app_u_no==null || m_app_u_no.equals(""))?false:true;
	}
	public AuthMember(int m_id, String m_email, String m_name,String m_pass,String m_image, String m_app_u_no) {
		super();
		this.m_id = m_id;
		this.m_email = m_email;
		this.m_name = m_name;
		this.m_pass = m_pass;
		this.auth_ename = "";
		this.m_image = (m_image==null)?"":m_image;
		this.m_app_u_no = (m_app_u_no==null || m_app_u_no.equals(""))?false:true;
	}
	public int getM_id() {
		return m_id;
	}
	public void setM_id(int m_id) {
		this.m_id = m_id;
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
	public String getM_pass() {
		return m_pass;
	}
	public void setM_pass(String m_pass) {
		this.m_pass = m_pass;
	}
	public String getAuth_ename() {
		return auth_ename;
	}
	public void setAuth_ename(String auth_ename) {
		this.auth_ename = auth_ename;
	}
	public String getM_image() {
		return m_image;
	}
	public void setM_image(String m_image) {
		this.m_image = m_image;
	}
	public MultipartFile getM_file() {
		return m_file;
	}
	public void setM_file(MultipartFile m_file) {
		this.m_file = m_file;
	}
	public boolean isM_app_u_no() {
		return m_app_u_no;
	}
	public void setM_app_u_no(boolean m_app_u_no) {
		this.m_app_u_no = m_app_u_no;
	}
	

	
}
