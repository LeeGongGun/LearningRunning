package controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;

import bean.Attendance;
import bean.AuthMember;
import bean.ClassJoinMem;
import bean.ClassJoinSubject;
import bean.Classes;
import bean.Counsel;
import bean.CurriJoinSubject;
import bean.Curriculum;
import bean.Exam;
import bean.ExamJoinSubject;
import bean.Subject;
import bean.TempAttendance;
import command.AttendanceInsertCommand;
import command.ClassesSearchCommand;
import command.MemberSearchCommand;
import command.PersonSearch;
import command.examCommand;
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
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/class/delete")
	public String classDelete(int class_id, Model model) {
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
	
	@RequestMapping(value = "/admin/classMembers", method = RequestMethod.POST)
	public String classStudentList(
			@RequestParam(value="class_id") Integer class_id,
			Model model) {
		List<ClassJoinMem> memberList = dao.classMembers(class_id,"student");
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
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		MultipartFile multi = command.getM_file();
		String newFileName = "";
		System.out.println(multi);
		if( multi!= null && !multi.isEmpty()){
			String fileName = multi.getOriginalFilename(); 
			newFileName= System.currentTimeMillis()+"_"+fileName;
			Set<String> pathSet = request.getSession().getServletContext().getResourcePaths("/");
			
			String path = request.getRealPath("/resources/uploads/")+"m_image/"+newFileName;
			System.out.println();
			try {
				File file =  new File(path);
				multi.transferTo(file);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			command.setM_image(newFileName);
		}

		
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
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/member/delete")
	public String memberDelete(int m_id, Model model) {
		int delOk = dao.memberDelete(m_id);
		model.addAttribute("json", "{\"data\": "+delOk+"}");
		return "/ajax/ajaxDefault";
	}
	
	//Curriculum
	@RequestMapping(value = "/admin/curri" , method = RequestMethod.GET)
	public String curriDefault(Model model) {
		return "/admin/curriList";
	}
	@RequestMapping(value = "/admin/curri", method = RequestMethod.POST)
	public String curriList(Model model) {
		List<Curriculum> curriList = dao.curriList();
		String json = "";
		try {
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			json = ow.writeValueAsString(curriList);
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

	@RequestMapping(value = "/admin/curri/insert", method = RequestMethod.POST)
	public String curriInsert(String cur_name,
			Model model) {
		int rs = dao.curriInsert(cur_name);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/curri/edit", method = RequestMethod.POST)
	public String curriEdit(String cur_name,int cur_id,
			Model model) {
		int rs = dao.curriEdit(cur_name,cur_id);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/curri/delete")
	public String curriDelete(int cur_id, Model model) {
		int delOk = dao.curriDelete(cur_id);
		model.addAttribute("json", "{\"data\": "+delOk+"}");
		return "/ajax/ajaxDefault";
	}
	
	@RequestMapping(value = "/admin/curriSubject", method = RequestMethod.POST)
	public String curriSubjectList(int cur_id,Model model) {
		List<CurriJoinSubject> rs = dao.curriSubjectList(cur_id);
		String json = "";
		try {
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			json = ow.writeValueAsString(rs);
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
	
	@RequestMapping(value = "/admin/curriSubject/insert", method = RequestMethod.POST)
	public String curriJoinSubInsert(
			@RequestParam(value="subject_id[]") List<Integer> subject_ids,
			@RequestParam(value="cur_id") String cur_id,
			Model model) {
		int auth_manager_id = 1;
		int rs = dao.curriJoinSubInsert(subject_ids,cur_id);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/curriSubject/delete", method = RequestMethod.POST)
	public String curriJoinSubDelete(
			@RequestParam(value="subject_id[]") List<Integer> subject_ids,
			@RequestParam(value="cur_id") String cur_id,
			Model model) {
		int auth_manager_id = 1;
		int delOk = dao.curriJoinSubDelete(subject_ids,cur_id);
		model.addAttribute("json", "{\"data\": "+delOk+"}");
		return "/ajax/ajaxDefault";
	}
	
	//ClassSubject
	@RequestMapping(value = "/admin/classSubject" , method = RequestMethod.GET)
	public String classSubjectDefault(Model model) {
		List<Classes> classList = dao.simpleClassList();
		model.addAttribute("classList",classList);
		return "/admin/subjects";
	}

	@RequestMapping(value = "/admin/classSubject", method = RequestMethod.POST)
	public String classSubjectList(int class_id,Model model) {
		List<ClassJoinSubject> rs = dao.classSubjectList(class_id);
		String json = "";
		try {
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			json = ow.writeValueAsString(rs);
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
	@RequestMapping(value = "/admin/classSubject/simple", method = RequestMethod.POST)
	public String classSimpleSubjectList(Model model) {
		List<ClassJoinSubject> rs = dao.classSimpleSubjectList();
		String json = "";
		try {
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			json = ow.writeValueAsString(rs);
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
	
	@RequestMapping(value = "/admin/classSubject/join", method = RequestMethod.POST)
	public String classJoinSubInsert(
			@RequestParam(value="subject_id[]") List<Integer> subject_ids,
			@RequestParam(value="class_id") String class_id,
			Model model) {
		int auth_manager_id = 1;
		int rs = dao.classJoinSubInsert(subject_ids,class_id);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/classSubject/cut", method = RequestMethod.POST)
	public String classJoinSubDelete(
			@RequestParam(value="subject_id[]") List<Integer> subject_ids,
			@RequestParam(value="class_id") String class_id,
			Model model) {
		int auth_manager_id = 1;
		int delOk = dao.classJoinSubDelete(subject_ids,class_id);
		model.addAttribute("json", "{\"data\": "+delOk+"}");
		return "/ajax/ajaxDefault";
	}
	
	@RequestMapping(value = "/admin/classSubject/insert", method = RequestMethod.POST)
	public String classSubjectInsert(Subject command,
			Errors errors,
			Model model) {
		int rs = dao.subjectInsert(command);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/classSubject/edit", method = RequestMethod.POST)
	public String classSubjectEdit(Subject command,
			Errors errors,
			Model model) {
		int rs = dao.subjectEdit(command);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/classSubject/delete")
	public String classSubjectDelete(int subject_id, Model model) {
		int delOk = dao.subjectDelete(subject_id);
		model.addAttribute("json", "{\"data\": "+delOk+"}");
		return "/ajax/ajaxDefault";
	}

	@RequestMapping(value = "/admin/exam" , method = RequestMethod.GET)
	public String examDefault(Model model) {
		//반 리스트
		List<Classes> classList = dao.simpleClassList();
		model.addAttribute("classList",classList);
		return "/admin/examList";
	}
	
	@RequestMapping(value = "/admin/exam", method = RequestMethod.POST)
	public String examList(examCommand command,Errors errors, Model model) {
		List<Exam> examlList = dao.examlList(command);
		String json = "";
		try {
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			json = ow.writeValueAsString(examlList);
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
	
	@RequestMapping(value = "/admin/exam/insert", method = RequestMethod.POST)
	public String examInsert(Exam command,
			Errors errors,
			Model model) {
		int rs = dao.examInsert(command);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/exam/edit", method = RequestMethod.POST)
	public String examEdit(Exam command,
			Errors errors,
			Model model) {
		int rs = dao.examEdit(command);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/exam/delete", method = RequestMethod.POST)
	public String examDelete(int exam_id, Model model) {
		int delOk=0;
		if(dao.countExamScore(exam_id)==0) delOk = dao.examDelete(exam_id);//점수가 없는경우에만 삭제가능
		model.addAttribute("json", "{\"data\": "+delOk+"}");
		return "/ajax/ajaxDefault";
	}	
	
	//examJoinSubject
	@RequestMapping(value = "/admin/examSubject", method = RequestMethod.POST)
	public String examSubjectList(int class_id,int exam_id,Model model) {
		List<ExamJoinSubject> rs = dao.examSubjectList(class_id,exam_id);
		String json = "";
		try {
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			json = ow.writeValueAsString(rs);
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
	
	@RequestMapping(value = "/admin/examSubject/join", method = RequestMethod.POST)
	public String examJoinSubInsert(
			@RequestParam(value="subject_id[]") List<Integer> subject_ids,
			@RequestParam(value="exam_id") String exam_id,
			Model model) {
		int auth_manager_id = 1;
		int rs = dao.examJoinSubInsert(subject_ids,exam_id);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/examSubject/cut", method = RequestMethod.POST)
	public String examJoinSubDelete(
			@RequestParam(value="subject_id[]") List<Integer> subject_ids,
			@RequestParam(value="exam_id") String exam_id,
			Model model) {
		int auth_manager_id = 1;
		int delOk = dao.examJoinSubDelete(subject_ids,exam_id);
		model.addAttribute("json", "{\"data\": "+delOk+"}");
		return "/ajax/ajaxDefault";
	}

	@RequestMapping(value = "/attendance/person", method = RequestMethod.GET)
	public String attendPersonSubListPost(Model model) {
		boolean isTeacher = false;
		boolean isAdmin = true;
		boolean isStudent = false;
		List<Classes> classesList = null;
		List<AuthMember> authMembers = null;
		if(isAdmin) {
			classesList = dao.simpleClassList();
			authMembers = dao.authList("student");
		}
		model.addAttribute("cList", classesList);
		model.addAttribute("authMembers", authMembers);
		return "attendance/attendancePerson";
	}


	@RequestMapping(value = "/attendance/person", method = RequestMethod.POST)
	public String attendPersonPost(
			PersonSearch command,
			Errors errors,
			Model model) {

		List<Attendance> rs = dao.memberAttendList(command); 
		String json = "";
		try {
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			json = ow.writeValueAsString(rs);
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
	@RequestMapping(value = "/attendance/selectStudentList", method = RequestMethod.POST)
	public String selectStudentList(
			Integer class_id,
			Model model) {
		
		List<AuthMember> rs = dao.studentList(class_id); 
		String json = "";
		try {
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			json = ow.writeValueAsString(rs);
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

	//attendance
	@RequestMapping(value = "/admin/tempAttend", method = RequestMethod.GET)
	public String tempAttendanceDefault(Model model) {
		List<Classes> classList = dao.simpleClassList();
		model.addAttribute("classList",classList);
		return "/attendance/attendanceInsert";
	}
	@RequestMapping(value = "/admin/tempAttend", method = RequestMethod.POST)
	public String tempAttendanceList(int class_id,String status, Model model) {
		
		
		List<TempAttendance> rs = (status.toUpperCase().equals("CHECK"))?null:dao.tempAttendanceList(class_id,status); 
		String json = "";
		try {
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			json = ow.writeValueAsString(rs);
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
	@RequestMapping(value = "/admin/tempAttend/insert", method = RequestMethod.POST)
	public String tempAttendInsert(
			@RequestParam(value="m_id[]") List<Integer> m_ids,
			@RequestParam(value="class_id") String class_id,
			@RequestParam(value="status") String status,
			Model model) {
		int auth_manager_id = 1;
		if(!status.equals("CHECK")){
			for (Iterator<Integer> it = m_ids.iterator() ; it.hasNext() ;) {
				Integer m_id = it.next();
				boolean isAttend = dao.isAttend(m_id,class_id,status);
				if(isAttend) it.remove();
//				System.out.println(isAttend+" "+m_ids.toString());
			}
		}
		Time time = new Time(System.currentTimeMillis());
		int rs = (m_ids.isEmpty())?0:dao.tempAttendInsert(m_ids,class_id, time, status);
//		int rs = (m_ids.isEmpty())?0:0;
		model.addAttribute("json", "{\"data\": "+rs+"}");
		return "/ajax/ajaxDefault";
	}
	
}
