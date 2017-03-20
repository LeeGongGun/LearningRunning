package command;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class AttendanceInsertCommand {
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date today;
	@DateTimeFormat(pattern="HH:mm")
	private Date time;
	String state;
	String[] attendanceCheck;
	public Date getToday() {
		return today;
	}
	public void setToday(Date today) {
		this.today = today;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String[] getAttendanceCheck() {
		return attendanceCheck;
	}
	public void setAttendanceCheck(String[] attendanceCheck) {
		this.attendanceCheck = attendanceCheck;
	}
	
 
}
