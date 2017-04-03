package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;	
import org.springframework.web.multipart.MultipartResolver;

import bean.Attendance;
import bean.AuthMember;
import bean.ClassAttend;
import command.AttendancePersonCommand;
import command.PersonSearch;
import dao.LggDao;

@Controller
public class LggController {
	private LggDao lggDao;
	private MultipartResolver multipartResolver;
	@Autowired
	public void setLggDao(LggDao lggDao) {
		this.lggDao = lggDao;
	}

	
	@RequestMapping(value = "/attendance/person", method = RequestMethod.GET)
	public String attendPersonSubListGet(Model model) {
		int id = 1;
		List<ClassAttend> ClassAttends = lggDao.memberClassAttendList(id);
		model.addAttribute("ClassAttends", ClassAttends);
		return "attendance/attendancePersonSubList";
	}

	@RequestMapping(value = "/attendance/person/{class_id}/{m_id}", method = RequestMethod.GET)
	public String attendPersonGet(@PathVariable("class_id") int class_id,@PathVariable("m_id") int m_id, Model model ) {
		ClassAttend ClassAttend = lggDao.memberClassAttendList(class_id,m_id);
		AuthMember authMmember = lggDao.selectMember(m_id);
		model.addAttribute("ClassAttend", ClassAttend);
		model.addAttribute("authMember", authMmember);
		return "attendance/attendancePerson";
	}

	@RequestMapping(value = "/memberAttendList", method = RequestMethod.POST)
	public String attendPersonPost(
			PersonSearch command,
			Errors errors,
			Model model) {
	List<Attendance> rs = lggDao.memberAttendList(command); 
		model.addAttribute("json", "{\"data\": "+rs+"}");
		return "/ajax/ajaxDefault";
	}




	
}
