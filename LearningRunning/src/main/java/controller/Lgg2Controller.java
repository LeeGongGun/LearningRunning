package controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartResolver;

import bean.authMember;
import bean.Subject;
import bean.Teacher;
import command.SubjectSearchCommand;
import command.TeacherSearchCommand;
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
	public String subjectList(SubjectSearchCommand command,Errors errors, Model model) {
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

	@RequestMapping(value = "/auth", method = RequestMethod.GET)
	public String authDefault(Model model) {
		return "/admin/authList";
	}
	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public String authList(@RequestParam(value="auth_ename",required=false) String auth_ename,Model model) {
		List<authMember> memberList = lggDao.authList(auth_ename);
		String json = "";
		try {
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			json = ow.writeValueAsString(memberList);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.addAttribute("json", "{\"data\": "+json+"}");
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/auth/insert", method = RequestMethod.POST)
	@ResponseBody
	public String authInsert(
			@RequestParam(value="m_id[]") List<Integer> m_ids,
			@RequestParam(value="auth_ename") String auth_ename,
			@RequestParam(value="auth_end_date",required=false) String auth_end_date,
			Errors errors,//command 객체에 null이 포함가능할경우 반드시 써줄것
			Model model) {
		int auth_manager_id = 1;//세션 대체
		int rs = lggDao.authInsert(m_ids,auth_ename,auth_manager_id);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/auth/delete")
	@ResponseBody
	public String authDelete(@RequestParam(value="m_id[]") List<Integer> m_ids,
			@RequestParam(value="auth_ename") String auth_ename,
			Errors errors,//command 객체에 null이 포함가능할경우 반드시 써줄것
			Model model) {
		int auth_manager_id = 1;//세션 대체
		int delOk = lggDao.authDelete(m_ids,auth_ename);
		model.addAttribute("json", "{\"data\": "+delOk+"}");
		return "/ajax/ajaxDefault";
	}
	
	
}
