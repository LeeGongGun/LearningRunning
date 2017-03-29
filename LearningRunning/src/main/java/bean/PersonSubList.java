package bean;

public class PersonSubList {
	int m_id, subject_id;
	private String subject_name, subject_state, m_email, m_name;
	
	public PersonSubList(){}

	public PersonSubList(int m_id, int subject_id, String subject_name, String subject_state, String m_email,
			String m_name) {
		super();
		this.m_id = m_id;
		this.subject_id = subject_id;
		this.subject_name = subject_name;
		this.subject_state = subject_state;
		this.m_email = m_email;
		this.m_name = m_name;
	}

	public int getM_id() {
		return m_id;
	}

	public void setM_id(int m_id) {
		this.m_id = m_id;
	}

	public int getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(int subject_id) {
		this.subject_id = subject_id;
	}

	public String getSubject_name() {
		return subject_name;
	}

	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}

	public String getSubject_state() {
		return subject_state;
	}

	public void setSubject_state(String subject_state) {
		this.subject_state = subject_state;
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

