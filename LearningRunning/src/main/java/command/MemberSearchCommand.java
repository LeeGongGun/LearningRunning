package command;

import org.springframework.beans.factory.annotation.Autowired;

public class MemberSearchCommand {
	int class_id;
	String searchText;

	@Autowired
	public MemberSearchCommand() {
		super();
		this.class_id = 0;
		this.searchText = "";
	}

	public int getClass_id() {
		return class_id;
	}

	public void setClass_id(int class_id) {
		this.class_id = class_id;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

}
