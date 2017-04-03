package controller;

import java.io.IOException;
import java.util.List;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartResolver;
import bean.AuthMember;
import bean.ClassJoinMem;
import bean.Classes;
import command.ClassesSearchCommand;
import command.MemberSearchCommand;
import dao.AdminDao;

@Controller
public class AdminController {
	private AdminDao dao;
	private MultipartResolver multipartResolver;
	@Autowired
	public void setAdminDao(AdminDao dao) {
		this.dao = dao;
	}
	//class
	@RequestMapping(value = "/admin/class/insert", method = RequestMethod.POST)
	public String classInsert(Classes command,
			Errors errors,
			Model model) {
		int rs = dao.classInsert(command);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/class/edit", method = RequestMethod.POST)
	public String classEdit(Classes command,
			Errors errors,
			Model model) {
		int rs = dao.classEdit(command);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		System.out.println(rs);
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/class/delete")
	public String classDelete(int class_id, Model model) {
		System.out.println(class_id);
		int delOk = dao.classDelete(class_id);
		model.addAttribute("json", "{\"data\": "+delOk+"}");
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/class" , method = RequestMethod.GET)
	public String classListDefault(ClassesSearchCommand command,Errors errors, Model model) {
		return "/admin/classList";
	}
	@RequestMapping(value = "/admin/class", method = RequestMethod.POST)
	public String classList(ClassesSearchCommand command,Errors errors, Model model) {
		List<Classes> classList = dao.classList(command);
		String json = "";
		try {
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			json = ow.writeValueAsString(classList);
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
	
	
	//auth
	@RequestMapping(value = "/admin/auth", method = RequestMethod.GET)
	public String authDefault(Model model) {
		return "/admin/authList";
	}
	@RequestMapping(value = "/admin/auth", method = RequestMethod.POST)
	public String authList(@RequestParam(value="auth_ename") String auth_ename,Model model) {
		List<AuthMember> memberList = dao.authList(auth_ename);
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
	@RequestMapping(value = "/admin/auth/insert", method = RequestMethod.POST)
	public String authInsert(
			@RequestParam(value="m_id[]") List<Integer> m_ids,
			@RequestParam(value="auth_ename") String auth_ename,
			@RequestParam(value="auth_end_date",required=false) String auth_end_date,
			Model model) {
		int auth_manager_id = 1;
		int rs = dao.authInsert(m_ids,auth_ename,auth_manager_id);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/auth/delete", method = RequestMethod.POST)
	public String authDelete(
			@RequestParam(value="m_id[]") List<Integer> m_ids,
			@RequestParam(value="auth_ename") String auth_ename,
			Model model) {
		int auth_manager_id = 1;
		int delOk = dao.authDelete(m_ids,auth_ename);
		model.addAttribute("json", "{\"data\": "+delOk+"}");
		return "/ajax/ajaxDefault";
	}
	
	@RequestMapping(value = "/admin/classJoinMem", method = RequestMethod.GET)
	public String classJoinMemDefault(Model model) {
		List<Classes> classList = dao.simpleClassList();
		model.addAttribute("classList",classList);
		return "/admin/classJoinMem";
	}
	
	@RequestMapping(value = "/admin/classJoinMem", method = RequestMethod.POST)
	public String classJoinMemList(
			@RequestParam(value="class_id") Integer class_id,
			@RequestParam(value="auth_ename") String auth_ename,
			Model model) {
		List<ClassJoinMem> memberList = dao.classJoinMemList(class_id,auth_ename);
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
	@RequestMapping(value = "/admin/classJoinMem/insert", method = RequestMethod.POST)
	public String classJoinMemInsert(
			@RequestParam(value="m_id[]") List<Integer> m_ids,
			@RequestParam(value="class_id") String class_id,
			@RequestParam(value="auth_ename") String auth_ename,
			Model model) {
		int auth_manager_id = 1;
		int rs = dao.classJoinMemInsert(m_ids,class_id,auth_ename);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/classJoinMem/delete", method = RequestMethod.POST)
	public String classJoinMemDelete(
			@RequestParam(value="m_id[]") List<Integer> m_ids,
			@RequestParam(value="class_id") String class_id,
			@RequestParam(value="auth_ename") String auth_ename,
			Model model) {
		int auth_manager_id = 1;
		int delOk = dao.classJoinMemDelete(m_ids,class_id,auth_ename);
		model.addAttribute("json", "{\"data\": "+delOk+"}");
		return "/ajax/ajaxDefault";
	}

	//member
	@RequestMapping(value = "/admin/member" , method = RequestMethod.GET)
	public String memberDefault(MemberSearchCommand command,Errors errors, Model model) {
		return "/admin/memberList";
	}
	@RequestMapping(value = "/admin/member", method = RequestMethod.POST)
	public String memberList(MemberSearchCommand command,Errors errors, Model model) {
		List<AuthMember> classList = dao.memberList(command);
		String json = "";
		try {
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			json = ow.writeValueAsString(classList);
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
	@RequestMapping(value = "/admin/member/insert", method = RequestMethod.POST)
	public String memberInsert(AuthMember command,
			Errors errors,
			Model model) {
		int rs = dao.memberInsert(command);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/member/edit", method = RequestMethod.POST)
	public String memberEdit(AuthMember command,
			Errors errors,
			Model model) {
		int rs = dao.memberEdit(command);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		System.out.println(rs);
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/member/delete")
	public String memberDelete(int m_id, Model model) {
		System.out.println(m_id);
		int delOk = dao.memberDelete(m_id);
		model.addAttribute("json", "{\"data\": "+delOk+"}");
		return "/ajax/ajaxDefault";
	}

}
