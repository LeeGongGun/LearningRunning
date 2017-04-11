package bean;

public class Counsel {
	int counsel_id;
	String counsel_title;
	String counsel_condent;
	int counselor;
	int m_id;
	String counsel_date;
	
	
	
	public Counsel() {
		super();
	}
	public Counsel(int counsel_id, String counsel_title, String counsel_condent, int counselor, int m_id,
			String counsel_date) {
		super();
		this.counsel_id = counsel_id;
		this.counsel_title = counsel_title;
		this.counsel_condent = counsel_condent;
		this.counselor = counselor;
		this.m_id = m_id;
		this.counsel_date = counsel_date;
	}
	public int getCounsel_id() {
		return counsel_id;
	}
	public void setCounsel_id(int counsel_id) {
		this.counsel_id = counsel_id;
	}
	public String getCounsel_title() {
		return counsel_title;
	}
	public void setCounsel_title(String counsel_title) {
		this.counsel_title = counsel_title;
	}
	public String getCounsel_condent() {
		return counsel_condent;
	}
	public void setCounsel_condent(String counsel_condent) {
		this.counsel_condent = counsel_condent;
	}
	public int getCounselor() {
		return counselor;
	}
	public void setCounselor(int counselor) {
		this.counselor = counselor;
	}
	public int getM_id() {
		return m_id;
	}
	public void setM_id(int m_id) {
		this.m_id = m_id;
	}
	public String getCounsel_date() {
		return counsel_date;
	}
	public void setCounsel_date(String counsel_date) {
		this.counsel_date = counsel_date;
	}
	
	
	
}
