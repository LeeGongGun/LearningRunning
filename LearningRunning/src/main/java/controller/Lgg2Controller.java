package controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartResolver;

import bean.Subject;
import command.SubjectCommand;
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
	@RequestMapping(value = "/course")
	public String subjectList(SubjectSearchCommand command, Model model) {
		List<Subject> subjectList = lggDao.subjectList(command);
		model.addAttribute("subjectList", subjectList );
		return "/course/subList";
	}
	@RequestMapping(value = "/course/insert", method = RequestMethod.POST)
	public String subjectInsert(SubjectCommand command,
			Model model) {
		int rs = lggDao.subjectInsert1(command);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		return "/ajax/ajaxDefault";
	}
//	@RequestMapping(value = "/course/insert", method = RequestMethod.POST)
//	public String subjectInsert(
//			@RequestParam(value="subject_name") String subject_name,
//			@RequestParam(value="subject_start",required=false)@DateTimeFormat(pattern="yyyy-MM-dd") Date subject_start,
//			@RequestParam(value="subject_end",required=false)@DateTimeFormat(pattern="yyyy-MM-dd") Date subject_end,
//			@RequestParam(value="subject_state",required=false) String subject_state,
//			@RequestParam(value="subject_comment",required=false) String subject_comment,
//			Model model) {
//		Subject command = new Subject();
//		command.setSubject_name(subject_name);
//		command.setSubject_start(subject_start);
//		command.setSubject_end(subject_end);
//		command.setSubject_state(subject_state);
//		command.setSubject_comment(subject_comment);
//		int rs = lggDao.subjectInsert(command);
//		model.addAttribute("json", "{\"data\": "+rs+"}");
//		return "/ajax/ajaxDefault";
//	}
	@RequestMapping(value = "/course/edit", method = RequestMethod.POST)
	public String subjectEdit(
			@RequestParam(value="subject_id") int subject_id,
			@RequestParam(value="subject_name") String subject_name,
			@RequestParam(value="subject_start",required=false)@DateTimeFormat(pattern="yyyy-MM-dd") Date subject_start,
			@RequestParam(value="subject_end",required=false)@DateTimeFormat(pattern="yyyy-MM-dd") Date subject_end,
			@RequestParam(value="subject_state",required=false) String subject_state,
			@RequestParam(value="subject_comment",required=false) String subject_comment,
			Model model) {
		Subject command = new Subject();
		command.setSubject_id(subject_id);
		command.setSubject_name(subject_name);
		command.setSubject_start(subject_start);
		command.setSubject_end(subject_end);
		command.setSubject_state(subject_state);
		command.setSubject_comment(subject_name);
		int rs = lggDao.subjectEdit(command);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		System.out.println(rs);
		return "/ajax/ajaxDefault";
	}

}
