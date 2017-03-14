package command;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ListCommand {
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private Date from,to;
	private int page=1;
	private int pageCnt=10;
	

	public int getPageCnt() {
		return pageCnt;
	}

	public void setPageCnt(int pageCnt) {
		this.pageCnt = pageCnt;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
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
