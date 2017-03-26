package controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartResolver;

import bean.PersonSearch;
import command.AttendancePersonCommand;
import dao.JshDao;

@Controller
@RequestMapping
public class JshController {
	private JshDao jshDao;
	private MultipartResolver multipartResolver;
	public void setJshDao(JshDao jshDao) {
		this.jshDao = jshDao;
	}
	
	@RequestMapping(value="/attendance/person", method = RequestMethod.GET)
	public String attendPersonSubListGet(Model model) {
		int id = 5;
		List<AttendancePersonCommand> attendancePersonCommand = jshDao.selectAllPerson(id);
		model.addAttribute("attendancePersonCommand", attendancePersonCommand);
		return "attendance/attendancePersonSubList";
	}
	
	@RequestMapping(value="/attendance/person/{id}", method = RequestMethod.GET)
	public String attendPersonGet(@PathVariable("id") int studentId, 
			Model model , 
			PersonSearch personsearch) {
		
		String student =  jshDao.getStudentName(studentId);
		String studentEmail =  jshDao.getStudentEmail(studentId);
		double attendRate = jshDao.getAttendRate(studentId);
		List<AttendancePersonCommand> attendancePersonCommand = jshDao.selectAllPerson(studentId);
		String stuSubject = jshDao.getSubjectName(studentId);
		
		model.addAttribute("PersonSearch", personsearch);
		model.addAttribute("student", student);
		model.addAttribute("studentEmail", studentEmail);
		model.addAttribute("attendRate", attendRate);
		model.addAttribute("stuSubject", stuSubject);
		model.addAttribute("attendancePersonCommand", attendancePersonCommand);
		return "attendance/attendancePerson";
	}
	
	@RequestMapping(value="/attendance/person/{id}", method = RequestMethod.POST)
	public String attendPersonPost(@PathVariable("id") int studentId, 
			@RequestParam(value="from", required=false) String strFrom,
			@RequestParam(value="to", required=false) String strTo,
			Model model) {

		String student =  jshDao.getStudentName(studentId);
		String studentEmail =  jshDao.getStudentEmail(studentId);
		double attendRate = jshDao.getAttendRate(studentId);
		

		List<AttendancePersonCommand> attendancePersonCommand = 
				jshDao.searchPersonPeriod(studentId, strFrom, strTo);
		
		String stuSubject = jshDao.getSubjectName(studentId);
				
		model.addAttribute("from", strFrom);
		model.addAttribute("to", strTo);
		model.addAttribute("student", student);
		model.addAttribute("studentEmail", studentEmail);
		model.addAttribute("attendRate", attendRate);
		model.addAttribute("stuSubject", stuSubject);
		model.addAttribute("attendancePersonCommand", attendancePersonCommand);
		return "attendance/attendancePerson";
	}
	
	public Date transDate(String d){
		Date date = null;
		if(d != null){
			SimpleDateFormat transFormat; 
			transFormat = new SimpleDateFormat("yyMMdd");
			try { date = transFormat.parse(d);
			} catch (Exception e) { e.printStackTrace(); }
		}
		return date;
	}
	
	//지각
	@RequestMapping(value="/attendance/beLate/{id}", method = RequestMethod.GET)
	public String belatePost(
			@PathVariable("id") int studentId, Model model , PersonSearch personsearch) {
		String student =  jshDao.getStudentName(studentId);
		String studentEmail =  jshDao.getStudentEmail(studentId);
		String stuSubject = jshDao.getSubjectName(studentId);
		double attendRate = jshDao.getAttendRate(studentId);
		List<AttendancePersonCommand> attendancePersonCommand = jshDao.beLate(studentId);
		model.addAttribute("PersonSearch", personsearch);
		model.addAttribute("student", student);
		model.addAttribute("studentEmail", studentEmail);
		model.addAttribute("attendRate", attendRate);
		model.addAttribute("stuSubject", stuSubject);
		model.addAttribute("attendancePersonCommand", attendancePersonCommand);
		return "attendance/attendancePerson";
	}
	
	//외출
	@RequestMapping(value="/attendance/goOut/{id}", method = RequestMethod.GET)
	public String goOutPost(
			@PathVariable("id") int studentId, Model model,
			@RequestParam(value="from", required=false) String strFrom,
			@RequestParam(value="to", required=false) String strTo) {
		
		String student =  jshDao.getStudentName(studentId);
		String studentEmail =  jshDao.getStudentEmail(studentId);
		String stuSubject = jshDao.getSubjectName(studentId);
		double attendRate = jshDao.getAttendRate(studentId);
		List<AttendancePersonCommand> attendancePersonCommand = jshDao.goOut(studentId);
		
		model.addAttribute("from", strFrom);
		model.addAttribute("to", strTo);
		model.addAttribute("student", student);
		model.addAttribute("studentEmail", studentEmail);
		model.addAttribute("attendRate", attendRate);
		model.addAttribute("stuSubject", stuSubject);
		model.addAttribute("attendancePersonCommand", attendancePersonCommand);
		return "attendance/attendancePerson";
	}
	
	//조퇴
	@RequestMapping(value="/attendance/leaveEarly/{id}", method = RequestMethod.GET)
	public String attendPersonPost(
			@PathVariable("id") int studentId, Model model , PersonSearch personsearch,
			@RequestParam(value="from", required=false) String strFrom,
			@RequestParam(value="to", required=false) String strTo) {
		
		String student =  jshDao.getStudentName(studentId);
		String studentEmail =  jshDao.getStudentEmail(studentId);
		String stuSubject = jshDao.getSubjectName(studentId);
		double attendRate = jshDao.getAttendRate(studentId);
		List<AttendancePersonCommand> attendancePersonCommand = jshDao.leaveEarly(studentId);
		model.addAttribute("from", strFrom);
		model.addAttribute("to", strTo);
		model.addAttribute("PersonSearch", personsearch);
		model.addAttribute("student", student);
		model.addAttribute("studentEmail", studentEmail);
		model.addAttribute("attendRate", attendRate);
		model.addAttribute("stuSubject", stuSubject);
		model.addAttribute("attendancePersonCommand", attendancePersonCommand);
		return "attendance/attendancePerson";
	}
	
	//결석
	@RequestMapping(value="/attendance/absent/{id}", method = RequestMethod.GET)
	public String absentPost(
			@PathVariable("id") int studentId, Model model , PersonSearch personsearch) {
		
		String student =  jshDao.getStudentName(studentId);
		String studentEmail =  jshDao.getStudentEmail(studentId);
		String stuSubject = jshDao.getSubjectName(studentId);
		double attendRate = jshDao.getAttendRate(studentId);
		List<AttendancePersonCommand> attendancePersonCommand = jshDao.absent(studentId);
		
		model.addAttribute("PersonSearch", personsearch);
		model.addAttribute("student", student);
		model.addAttribute("studentEmail", studentEmail);
		model.addAttribute("attendRate", attendRate);
		model.addAttribute("stuSubject", stuSubject);
		model.addAttribute("attendancePersonCommand", attendancePersonCommand);
		return "attendance/attendancePerson";
	}
	

}
