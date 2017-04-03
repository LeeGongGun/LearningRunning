package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartResolver;

import bean.Classes;
import bean.TempAttendance;
import command.AttendanceInsertCommand;
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
	
}
