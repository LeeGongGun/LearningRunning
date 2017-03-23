package controller;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartResolver;

import bean.Subject;
import bean.Teacher;
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
		return "/admin/subList";
	}
	@RequestMapping(value = "/course/insert", method = RequestMethod.POST)
	public String subjectInsert(Subject command,
			Errors errors,//command 객체에 null이 포함가능할경우 반드시 써줄것
			Model model) {
		int rs = lggDao.subjectInsert(command);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/course/edit", method = RequestMethod.POST)
	public String subjectEdit(Subject command,
			Errors errors,//command 객체에 null이 포함가능할경우 반드시 써줄것
			Model model) {
		int rs = lggDao.subjectEdit(command);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		System.out.println(rs);
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/course/delete")
	public String subjectDelete(int subject_id, Model model) {
		System.out.println(subject_id);
		int delOk = lggDao.subjectDelete(subject_id);
		model.addAttribute("json", "{\"data\": "+delOk+"}");
		return "/ajax/ajaxDefault";
	}

	@RequestMapping(value = "/teacher")
	public String teacherList(TeacherSearchCommand command, Model model) {
		List<Teacher> teacherList = lggDao.teacherList(command);
		model.addAttribute("teacherList", teacherList );
		return "/admin/teacherList";
	}
	@RequestMapping(value = "/teacher/insert", method = RequestMethod.POST)
	public String teacherInsert(Teacher command,
			Errors errors,//command 객체에 null이 포함가능할경우 반드시 써줄것
			Model model) {
		int rs = lggDao.teacherInsert(command);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/teacher/edit", method = RequestMethod.POST)
	public String teacherEdit(Teacher command,
			Errors errors,//command 객체에 null이 포함가능할경우 반드시 써줄것
			Model model) {
		int rs = lggDao.teacherEdit(command);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		System.out.println(rs);
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/teacher/delete")
	public String teacherDelete(int teacher_id, Model model) {
		System.out.println(teacher_id);
		int delOk = lggDao.teacherDelete(teacher_id);
		model.addAttribute("json", "{\"data\": "+delOk+"}");
		return "/ajax/ajaxDefault";
	}
	
	
}
