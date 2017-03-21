package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartResolver;

import bean.Subject;
import command.SubjectSearchCommand;
import dao.LggDao;

@Controller
public class Lgg2Controller {
	private LggDao lggDao;
	private MultipartResolver multipartResolver;
	@Autowired
	public void setLggDao(LggDao lggDao) {
		this.lggDao = lggDao;
	}
	@RequestMapping(value = "/course", method = RequestMethod.POST)
	public String subjectList(SubjectSearchCommand command, Model model) {
		int adminId = 1;
		List<Subject> subjectList = lggDao.subjectList(command);
		model.addAttribute("subjectList", subjectList );
		return "/course/subList";
	}

}
