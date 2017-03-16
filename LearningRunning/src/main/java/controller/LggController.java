package controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartResolver;

import bean.Attendance;
import bean.Subject;
import dao.LggDao;
@Controller
public class LggController {
	private LggDao lggDao;
	private MultipartResolver multipartResolver;
	@Autowired
	public void setLggDao(LggDao lggDao) {
		this.lggDao = lggDao;
	}
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "home";
	}
	@RequestMapping(value = "/attendance/subList", method = RequestMethod.GET)
	public String attendanceSubjectList(Model model) {
		int teacherId = 2;
		List<Subject> subjectList = lggDao.teachersSubject(teacherId);
		model.addAttribute("subjectList", subjectList );
		return "/attendance/attendanceSubList";
	}
	@RequestMapping(value = "/attendance/insert/{id}", method = RequestMethod.GET)
	public String attendanceInsert(@PathVariable("id") int subjectId, Model model) {
		List<Attendance> aList = lggDao.tempAttendanceList(subjectId);
		model.addAttribute("aList", aList );
		return "/attendance/attendanceInsert";
	}

	
}
