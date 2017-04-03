package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;	
import org.springframework.web.multipart.MultipartResolver;

import bean.Attendance;
import bean.AuthMember;
import bean.ClassAttend;
import command.AttendancePersonCommand;
import command.PersonSearch;
import dao.LggDao;

@Controller
public class LggController {
	private LggDao lggDao;
	private MultipartResolver multipartResolver;
	@Autowired
	public void setLggDao(LggDao lggDao) {
		this.lggDao = lggDao;
	}

	
	@RequestMapping(value = "/attendance/person", method = RequestMethod.GET)
	public String attendPersonSubListGet(Model model) {
		int id = 1;
		List<ClassAttend> ClassAttends = lggDao.memberClassAttendList(id);
		model.addAttribute("ClassAttends", ClassAttends);
		return "attendance/attendancePersonSubList";
	}

	@RequestMapping(value = "/attendance/person/{class_id}/{m_id}", method = RequestMethod.GET)
	public String attendPersonGet(@PathVariable("class_id") int class_id,@PathVariable("m_id") int m_id, Model model ) {
		ClassAttend ClassAttend = lggDao.memberClassAttendList(class_id,m_id);
		AuthMember authMmember = lggDao.selectMember(m_id);

		Calendar cal = Calendar.getInstance ( ); 
		Date from = cal.getTime();
		cal.add ( cal.MONTH, -2 ); 
		Date to = cal.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		model.addAttribute("ClassAttend", ClassAttend);
		model.addAttribute("authMember", authMmember);
		model.addAttribute("from", format.format(from));
		model.addAttribute("to", format.format(to));
		return "attendance/attendancePerson";
	}

	@RequestMapping(value = "/memberAttendList", method = RequestMethod.POST)
	public String attendPersonPost(
			PersonSearch command,
			Errors errors,
			Model model) {

		List<Attendance> rs = lggDao.memberAttendList(command); 
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
