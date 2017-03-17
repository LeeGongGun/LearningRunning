package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartResolver;

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
	
	@RequestMapping(value="attendance/person", method = RequestMethod.GET)
	public String attendPersonGet(AttendancePersonCommand attendancePersonCommand, Model model) {
		attendancePersonCommand=jshDao.selectAllPerson();
		System.out.println(attendancePersonCommand);
		System.out.println(jshDao.selectAllPerson());
		model.addAttribute("attendancePersonCommand", attendancePersonCommand);
		System.out.println(model);
		int result = jshDao.count(attendancePersonCommand);
		System.out.println(result);
		return "attendance/attendancPerson";
	}

	
}
