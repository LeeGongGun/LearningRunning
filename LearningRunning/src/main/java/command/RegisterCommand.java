package command;

public class RegisterCommand {
	String m_name,m_email,m_pass,m_repass;
	public boolean isPasswordEqualToConfirmPassword() {
		return m_pass.equals(m_repass);
	}

	public String getM_name() {
		return m_name;
	}

	public void setM_name(String m_name) {
		this.m_name = m_name;
	}

	public String getM_email() {
		return m_email;
	}

	public void setM_email(String m_email) {
		this.m_email = m_email;
	}

	public String getM_pass() {
		return m_pass;
	}

	public void setM_pass(String m_pass) {
		this.m_pass = m_pass;
	}

	public String getM_repass() {
		return m_repass;
	}

	public void setM_repass(String m_repass) {
		this.m_repass = m_repass;
	}
	
}
