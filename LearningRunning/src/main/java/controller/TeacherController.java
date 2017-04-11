package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.web.multipart.MultipartResolver;

import bean.AuthMember;
import bean.Classes;
import bean.Counsel;
import bean.Exam;
import bean.TempAttendance;
import command.AttendanceInsertCommand;
import command.MemberSearchCommand;
import command.examCommand;
import dao.LggDao;
import dao.TeacherDao;

@Controller
public class TeacherController {
	private TeacherDao dao;
	private MultipartResolver multipartResolver;
	@Autowired
	public void setTeacherDao(TeacherDao dao) {
		this.dao = dao;
	}

	//attendance
	@RequestMapping(value = "/teacher/attendance/classList", method = RequestMethod.GET)
	public String attendanceClassesList(Model model) {
		int teacherId = 2;
		List<Classes> classList = dao.teachersClasses(teacherId);
		model.addAttribute("classList", classList );
		return "/attendance/attendanceInsertClassList";
	}
	@RequestMapping(value = "/teacher/attendance/insert/{id}", method = RequestMethod.GET)
	public String attendanceInsert(@PathVariable("id") int class_id, Model model) {
		List<TempAttendance> aList = dao.tempAttendanceList(class_id);
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
			request = dao.attendInsert(command,class_id);
		}
		model.addAttribute("json", "{\"data\": "+request+"}");
		
		return "/ajax/ajaxDefault";
	}
	
	//member
	@RequestMapping(value = "/teacher/counsel" , method = RequestMethod.GET)
	public String counselDefault(Model model) {
		//반 리스트
		List<Classes> counselClasses = dao.counselClasses();
		//선생님 리스트
		List<AuthMember> listTeacher = dao.authList("teacher");
		//학생리스트
		List<AuthMember> listStudent = dao.authList("student");
		model.addAttribute("listTeacher",listTeacher);
		model.addAttribute("listStudent",listStudent);
		return "/teacher/counsel";
	}
	@RequestMapping(value = "/teacher/counsel", method = RequestMethod.POST)
	public String counselList(MemberSearchCommand command,Errors errors, Model model) {
		List<Counsel> counselList = dao.counselList(command);
		String json = "";
		try {
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			json = ow.writeValueAsString(counselList);
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

	
	
}
