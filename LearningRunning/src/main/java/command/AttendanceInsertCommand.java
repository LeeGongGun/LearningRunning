package command;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class AttendanceInsertCommand {
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private Date time;
	String state;
	String[] attendanceCheck;
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
