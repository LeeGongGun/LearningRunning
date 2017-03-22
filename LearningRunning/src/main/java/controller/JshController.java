package controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartResolver;

import bean.Subject;
import command.AttendancePersonCommand;
import dao.JshDao;
import dao.LggDao;

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
		int id = 1;
		List<AttendancePersonCommand> attendancePersonCommand = jshDao.selectAllPerson(id);
		model.addAttribute("attendancePersonCommand", attendancePersonCommand);
		return "attendance/attendancePersonSubList";
	}
	
	@RequestMapping(value="/attendance/person/{id}", method = RequestMethod.GET)
	public String attendPersonGet(@PathVariable("id") int studentId,Model model) {
		List<AttendancePersonCommand> attendancePersonCommand = jshDao.selectAllPerson(studentId);
		model.addAttribute("attendancePersonCommand", attendancePersonCommand);
		return "attendance/attendancePerson";
	}

	
}
