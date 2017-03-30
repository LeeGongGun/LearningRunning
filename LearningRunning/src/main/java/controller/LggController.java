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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartResolver;

import bean.Attendance;
import bean.AuthMember;
import bean.ClassJoinMem;
import bean.Classes;
import bean.TempAttendance;
import command.AttendanceInsertCommand;
import command.ClassesSearchCommand;
import command.MemberSearchCommand;
import dao.LggDao;
@Controller
public class LggController {
	private LggDao lggDao;
	private MultipartResolver multipartResolver;
	@Autowired
	public void setLggDao(LggDao lggDao) {
		this.lggDao = lggDao;
	}
	
	
	//attendance
	@RequestMapping(value = "/teacher/attendance/classList", method = RequestMethod.GET)
	public String attendanceClassesList(Model model) {
		int teacherId = 2;
		List<Classes> classList = lggDao.teachersClasses(teacherId);
		model.addAttribute("classList", classList );
		return "/attendance/attendanceInsertClassList";
	}
	@RequestMapping(value = "/teacher/attendance/insert/{id}", method = RequestMethod.GET)
	public String attendanceInsert(@PathVariable("id") int class_id, Model model) {
		List<TempAttendance> aList = lggDao.tempAttendanceList(class_id);
		for (int i = 0; i < aList.size(); i++) {
			TempAttendance attendance = aList.get(i);
		}
		model.addAttribute("aList", aList );
		return "/attendance/attendanceInsert";
	}
	@RequestMapping(value = "/teacher/attendance/insert/{id}", method = RequestMethod.POST)
	public String attendanceInsertAction(AttendanceInsertCommand command,@PathVariable("id") int class_id, Model model) {
		String[] tmpArr = command.getAttendanceCheck();
		int request = 0;
		if (tmpArr!=null) {
			request = lggDao.attendInsert(command,class_id);
		}
		model.addAttribute("json", "{\"data\": "+request+"}");
		
		return "/ajax/ajaxDefault";
	}
	
	
	
	//class
	@RequestMapping(value = "/admin/class/insert", method = RequestMethod.POST)
	public String classInsert(Classes command,
			Errors errors,
			Model model) {
		int rs = lggDao.classInsert(command);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/class/edit", method = RequestMethod.POST)
	public String classEdit(Classes command,
			Errors errors,
			Model model) {
		int rs = lggDao.classEdit(command);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		System.out.println(rs);
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/class/delete")
	public String classDelete(int class_id, Model model) {
		System.out.println(class_id);
		int delOk = lggDao.classDelete(class_id);
		model.addAttribute("json", "{\"data\": "+delOk+"}");
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/class" , method = RequestMethod.GET)
	public String classListDefault(ClassesSearchCommand command,Errors errors, Model model) {
		return "/admin/classList";
	}
	@RequestMapping(value = "/admin/class", method = RequestMethod.POST)
	public String classList(ClassesSearchCommand command,Errors errors, Model model) {
		List<Classes> classList = lggDao.classList(command);
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
		List<AuthMember> memberList = lggDao.authList(auth_ename);
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
		int rs = lggDao.authInsert(m_ids,auth_ename,auth_manager_id);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/auth/delete", method = RequestMethod.POST)
	public String authDelete(
			@RequestParam(value="m_id[]") List<Integer> m_ids,
			@RequestParam(value="auth_ename") String auth_ename,
			Model model) {
		int auth_manager_id = 1;
		int delOk = lggDao.authDelete(m_ids,auth_ename);
		model.addAttribute("json", "{\"data\": "+delOk+"}");
		return "/ajax/ajaxDefault";
	}
	
	@RequestMapping(value = "/admin/classJoinMem", method = RequestMethod.GET)
	public String classJoinMemDefault(Model model) {
		List<Classes> classList = lggDao.simpleClassList();
		model.addAttribute("classList",classList);
		return "/admin/classJoinMem";
	}
	
	@RequestMapping(value = "/admin/classJoinMem", method = RequestMethod.POST)
	public String classJoinMemList(
			@RequestParam(value="class_id") Integer class_id,
			@RequestParam(value="auth_ename") String auth_ename,
			Model model) {
		List<ClassJoinMem> memberList = lggDao.classJoinMemList(class_id,auth_ename);
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
		int rs = lggDao.classJoinMemInsert(m_ids,class_id,auth_ename);
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
		int delOk = lggDao.classJoinMemDelete(m_ids,class_id,auth_ename);
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
		List<AuthMember> classList = lggDao.memberList(command);
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
		int rs = lggDao.memberInsert(command);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/member/edit", method = RequestMethod.POST)
	public String memberEdit(AuthMember command,
			Errors errors,
			Model model) {
		int rs = lggDao.memberEdit(command);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		System.out.println(rs);
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/member/delete")
	public String memberDelete(int m_id, Model model) {
		System.out.println(m_id);
		int delOk = lggDao.memberDelete(m_id);
		model.addAttribute("json", "{\"data\": "+delOk+"}");
		return "/ajax/ajaxDefault";
	}

	
}
