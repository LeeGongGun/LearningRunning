package bean;

import java.util.Date;

public class PersonSearch {
	Date from;
	Date to;
	
	public PersonSearch(){}
	
	private PersonSearch(Date from, Date to) {
		super();
		this.from = from;
		this.to = to;
	}
	public Date getFrom() {
		return from;
	}
	public void setFrom(Date from) {
		this.from = from;
	}
	public Date getTo() {
		return to;
	}
	public void setTo(Date to) {
		this.to = to;
	}
}

 
