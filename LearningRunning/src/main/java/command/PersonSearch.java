package command;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class PersonSearch {
	@DateTimeFormat(pattern="yyyy-MM-dd")
	Date from,to;
	
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

 
