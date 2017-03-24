package controller;

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
	public String attendPersonGet(@PathVariable("id") int studentId,Model model , PersonSearch personserch) {
		String student =  jshDao.getStudentName(studentId);
		String studentEmail =  jshDao.getStudentEmail(studentId);
		double attendRate = jshDao.getAttendRate(studentId);
		List<AttendancePersonCommand> attendancePersonCommand = jshDao.selectAllPerson(studentId);
		String stuSubject = jshDao.getSubjectName(studentId);
		model.addAttribute("personserch", personserch);
		model.addAttribute("student", student);
		model.addAttribute("studentEmail", studentEmail);
		model.addAttribute("attendRate", attendRate);
		model.addAttribute("stuSubject", stuSubject);
		model.addAttribute("attendancePersonCommand", attendancePersonCommand);
		return "attendance/attendancePerson";
	}
	
	@RequestMapping(value="/attendance/person/{id}", method = RequestMethod.POST)
	public String attendPersonPost(
			@PathVariable("id") int studentId, Model model , PersonSearch personSerch,
			@RequestParam(value="from", required=false) String strFrom,
			@RequestParam(value="to", required=false) String strTo) {
		
		String student =  jshDao.getStudentName(studentId);
		String studentEmail =  jshDao.getStudentEmail(studentId);
		double attendRate = jshDao.getAttendRate(studentId);
		List<AttendancePersonCommand> attendancePersonCommand = jshDao.selectAllPerson(studentId);
		String stuSubject = jshDao.getSubjectName(studentId);
		model.addAttribute("personserch", personSerch);
		model.addAttribute("student", student);
		model.addAttribute("studentEmail", studentEmail);
		model.addAttribute("attendRate", attendRate);
		model.addAttribute("stuSubject", stuSubject);
		model.addAttribute("attendancePersonCommand", attendancePersonCommand);
		return "attendance/attendancePerson";
	}
}
