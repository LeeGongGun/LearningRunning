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
import org.springframework.web.multipart.MultipartResolver;

import bean.Attendance;
import bean.AuthMember;
import bean.Classes;
import command.PersonSearch;
import dao.LggDao;

@Controller
public class LggController {	
	private LggDao dao;
	private MultipartResolver multipartResolver;
	@Autowired
	public void setLggDao(LggDao dao) {
		this.dao = dao;
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
			authMembers = dao.studentList(null);
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




	
}
