package command;

import org.springframework.beans.factory.annotation.Autowired;

public class CounselSearchCommand {
	int counselor,m_id;

	@Autowired
	public CounselSearchCommand() {
		super();
	}

	public CounselSearchCommand(int counselor, int m_id) {
		super();
		this.counselor = counselor;
		this.m_id = m_id;
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
	
	
}
