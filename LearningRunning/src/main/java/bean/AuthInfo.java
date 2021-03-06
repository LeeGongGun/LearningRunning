package bean;

public class AuthInfo {
	private int m_id;
	private String m_email,m_name,m_image;
	boolean admin,teacher,student;
	
	public AuthInfo(int m_id, String m_email, String m_name, String m_image, boolean admin, boolean teacher,
			boolean student) {
		super();
		this.m_id = m_id;
		this.m_email = m_email;
		this.m_name = m_name;
		this.m_image = m_image;
		this.admin = admin;
		this.teacher = teacher;
		this.student = student;
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
	public String getM_image() {
		return m_image;
	}
	public void setM_image(String m_image) {
		this.m_image = m_image;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public boolean isTeacher() {
		return teacher;
	}
	public void setTeacher(boolean teacher) {
		this.teacher = teacher;
	}
	public boolean isStudent() {
		return student;
	}
	public void setStudent(boolean student) {
		this.student = student;
	}
	
	
}
