package controller;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import bean.SubJoinMem;
import bean.Subject;
import command.AttendanceInsertCommand;
import command.MemberSearchCommand;
import command.SubjectSearchCommand;
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
	@RequestMapping(value = "/teacher/attendance/subList", method = RequestMethod.GET)
	public String attendanceSubjectList(Model model) {
		int teacherId = 2;
		List<Subject> subjectList = lggDao.teachersSubject(teacherId);
		model.addAttribute("subjectList", subjectList );
		return "/attendance/attendanceSubList";
	}
	@RequestMapping(value = "/teacher/attendance/insert/{id}", method = RequestMethod.GET)
	public String attendanceInsert(@PathVariable("id") int subjectId, Model model) {
		List<Attendance> aList = lggDao.tempAttendanceList(subjectId);
		for (int i = 0; i < aList.size(); i++) {
			Attendance attendance = aList.get(i);
		}
		model.addAttribute("aList", aList );
		return "/attendance/attendanceInsert";
	}
	@RequestMapping(value = "/teacher/attendance/insert/{id}", method = RequestMethod.POST)
	public String attendanceInsertAction(AttendanceInsertCommand command,@PathVariable("id") int subjectId, Model model) {
		String[] tmpArr = command.getAttendanceCheck();
		int request = 0;
		if (tmpArr!=null) {
			request = lggDao.attendInsert(command,subjectId);
		}
		model.addAttribute("json", "{\"data\": "+request+"}");
		
		return "/ajax/ajaxDefault";
	}
	
	
	
	//subject
	@RequestMapping(value = "/admin/course/insert", method = RequestMethod.POST)
	public String subjectInsert(Subject command,
			Errors errors,//command ��ü�� null�� ���԰����Ұ�� �ݵ�� ���ٰ�
			Model model) {
		int rs = lggDao.subjectInsert(command);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/course/edit", method = RequestMethod.POST)
	public String subjectEdit(Subject command,
			Errors errors,//command ��ü�� null�� ���԰����Ұ�� �ݵ�� ���ٰ�
			Model model) {
		int rs = lggDao.subjectEdit(command);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		System.out.println(rs);
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/course/delete")
	public String subjectDelete(int subject_id, Model model) {
		System.out.println(subject_id);
		int delOk = lggDao.subjectDelete(subject_id);
		model.addAttribute("json", "{\"data\": "+delOk+"}");
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/course" , method = RequestMethod.GET)
	public String subjectListDefault(SubjectSearchCommand command,Errors errors, Model model) {
		return "/admin/subList";
	}
	@RequestMapping(value = "/admin/course", method = RequestMethod.POST)
	public String subjectList(SubjectSearchCommand command,Errors errors, Model model) {
		List<Subject> subjectList = lggDao.subjectList(command);
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
		int auth_manager_id = 1;//���� ��ü
		int rs = lggDao.authInsert(m_ids,auth_ename,auth_manager_id);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/auth/delete", method = RequestMethod.POST)
	public String authDelete(
			@RequestParam(value="m_id[]") List<Integer> m_ids,
			@RequestParam(value="auth_ename") String auth_ename,
			Model model) {
		int auth_manager_id = 1;//���� ��ü
		int delOk = lggDao.authDelete(m_ids,auth_ename);
		model.addAttribute("json", "{\"data\": "+delOk+"}");
		return "/ajax/ajaxDefault";
	}

	
	
	
	
	
	//��-�л�(����)
	@RequestMapping(value = "/admin/subJoinMem", method = RequestMethod.GET)
	public String subJoinMemDefault(Model model) {
		List<Subject> subjectList = lggDao.simpleSubjectList();
		model.addAttribute("subjectList",subjectList);
		return "/admin/subJoinMem";
	}
	
	@RequestMapping(value = "/admin/subJoinMem", method = RequestMethod.POST)
	public String subJoinMemList(
			@RequestParam(value="subject_id") Integer subject_id,
			@RequestParam(value="auth_ename") String auth_ename,
			Model model) {
		List<SubJoinMem> memberList = lggDao.subJoinMemList(subject_id,auth_ename);
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
	@RequestMapping(value = "/admin/subJoinMem/insert", method = RequestMethod.POST)
	public String subJoinMemInsert(
			@RequestParam(value="m_id[]") List<Integer> m_ids,
			@RequestParam(value="subject_id") String subject_id,
			@RequestParam(value="auth_ename") String auth_ename,
			Model model) {
		int stuJoinSub_manager_id = 1;//���� ��ü
		int rs = lggDao.subJoinMemInsert(m_ids,subject_id,auth_ename);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/subJoinMem/delete", method = RequestMethod.POST)
	public String subJoinMemDelete(
			@RequestParam(value="m_id[]") List<Integer> m_ids,
			@RequestParam(value="subject_id") String subject_id,
			@RequestParam(value="auth_ename") String auth_ename,
			Model model) {
		int stuJoinSub_manager_id = 1;//���� ��ü
		int delOk = lggDao.subJoinMemDelete(m_ids,subject_id,auth_ename);
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
	@RequestMapping(value = "/admin/member/insert", method = RequestMethod.POST)
	public String memberInsert(AuthMember command,
			Errors errors,//command ��ü�� null�� ���԰����Ұ�� �ݵ�� ���ٰ�
			Model model) {
		int rs = lggDao.memberInsert(command);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/member/edit", method = RequestMethod.POST)
	public String memberEdit(AuthMember command,
			Errors errors,//command ��ü�� null�� ���԰����Ұ�� �ݵ�� ���ٰ�
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
