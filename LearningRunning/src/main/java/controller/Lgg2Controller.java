package controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import bean.AuthMember;
import bean.SubJoinMem;
import bean.Subject;
import bean.Teacher;
import command.MemberSearchCommand;
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
	@RequestMapping(value = "/member" , method = RequestMethod.GET)
	public String memberDefault(MemberSearchCommand command,Errors errors, Model model) {
		return "/admin/memberList";
	}
	@RequestMapping(value = "/member", method = RequestMethod.POST)
	public String memberList(MemberSearchCommand command,Errors errors, Model model) {
		List<AuthMember> subjectList = lggDao.memberList(command);
		String json = "";
		try {
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			json = ow.writeValueAsString(subjectList);
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
	@RequestMapping(value = "/member/insert", method = RequestMethod.POST)
	public String memberInsert(AuthMember command,
			Errors errors,//command 객체에 null이 포함가능할경우 반드시 써줄것
			Model model) {
		int rs = lggDao.memberInsert(command);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/member/edit", method = RequestMethod.POST)
	public String memberEdit(AuthMember command,
			Errors errors,//command 객체에 null이 포함가능할경우 반드시 써줄것
			Model model) {
		int rs = lggDao.memberEdit(command);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		System.out.println(rs);
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/member/delete")
	public String memberDelete(int m_id, Model model) {
		System.out.println(m_id);
		int delOk = lggDao.memberDelete(m_id);
		model.addAttribute("json", "{\"data\": "+delOk+"}");
		return "/ajax/ajaxDefault";
	}
	
	
}
