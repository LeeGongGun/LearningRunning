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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartResolver;

import bean.AuthMember;
import bean.ClassJoinSubject;
import bean.Classes;
import bean.Counsel;
import bean.Curriculum;
import bean.Exam;
import bean.ExamJoinSubject;
import bean.Score;
import bean.TempAttendance;
import command.AttendanceInsertCommand;
import command.CounselSearchCommand;
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
	
	//counsel
	@RequestMapping(value = "/teacher/counsel" , method = RequestMethod.GET)
	public String counselDefault(Model model) {
		//선생님 리스트
		List<AuthMember> listTeacher = dao.authOnlyList("teacher");
		//학생리스트
		List<AuthMember> listStudent = dao.authOnlyList("student");
		model.addAttribute("listTeacher",listTeacher);
		model.addAttribute("listStudent",listStudent);
		return "/teacher/counsel";
	}
	@RequestMapping(value = "/teacher/counsel", method = RequestMethod.POST)
	public String counselList(CounselSearchCommand command,Errors errors, Model model) {
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
	
	@RequestMapping(value = "/admin/counsel/insert", method = RequestMethod.POST)
	public String counselInsert(Counsel command,
			Errors errors,
			Model model) {
		int rs = dao.counselInsert(command);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/counsel/edit", method = RequestMethod.POST)
	public String counselEdit(Counsel command,
			Errors errors,
			Model model) {
		int rs = dao.counselEdit(command);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		System.out.println(rs);
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/admin/counsel/delete")
	public String counselDelete(int counsel_id, Model model) {
		int delOk = dao.counselDelete(counsel_id);
		model.addAttribute("json", "{\"data\": "+delOk+"}");
		return "/ajax/ajaxDefault";
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/teacher/score", method = RequestMethod.GET)
	public String examScoreList(Model model) {
		List<Classes> classList = dao.simpleClassList();
		model.addAttribute("classList",classList);
		return "/teacher/scoreList";
		
	}
	@RequestMapping(value = "/teacher/score", method = RequestMethod.POST)
	public String defaultScoreList(
			@RequestParam(value="exam_id") Integer exam_id,
			Model model) {
		List<Score> scoreList = dao.scoreListByExam(exam_id);
		String json = "";
		try {
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			json = ow.writeValueAsString(scoreList);
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
	@RequestMapping(value = "/teacher/score/insert", method = RequestMethod.POST)
	public String scoreInsert(
			@RequestParam(value="exam_id[]") List<Integer> exam_ids,
			@RequestParam(value="m_id[]") List<Integer> m_ids,
			@RequestParam(value="subject_id[]") List<Integer> subject_ids,
			@RequestParam(value="score[]") List<Integer> scores,
			Model model) {
		int rs = dao.scoreInsert(exam_ids,m_ids,subject_ids,scores);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/teacher/score/update", method = RequestMethod.POST)
	public String scoreUpdate(
			@RequestParam(value="exam_id[]") List<Integer> exam_ids,
			@RequestParam(value="m_id[]") List<Integer> m_ids,
			@RequestParam(value="subject_id[]") List<Integer> subject_ids,
			@RequestParam(value="score[]") List<Integer> scores,
			Model model) {
		int rs = dao.scoreUpdate(exam_ids,m_ids,subject_ids,scores);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		return "/ajax/ajaxDefault";
	}
	@RequestMapping(value = "/teacher/score/delete", method = RequestMethod.POST)
	public String scoreDelete(
			@RequestParam(value="exam_id") Integer exam_id,
			Model model) {
		int rs = dao.scoreDelete(exam_id);
		model.addAttribute("json", "{\"data\": "+rs+"}");
		return "/ajax/ajaxDefault";
	}
	
	@RequestMapping(value = "/teacher/memberScore", method = RequestMethod.GET)
	public String memberScoreList(Model model) {
		List<Classes> classList = dao.simpleClassList();
		model.addAttribute("classList",classList);
		return "/teacher/memberScore";
		
	}

	@RequestMapping(value = "/teacher/memberScore", method = RequestMethod.POST)
	public String memberScoreList(
			@RequestParam(value="class_id") Integer class_id,
			Model model) {
		List<Score> scoreList = dao.scoreListByClass(class_id);
		String json = "";
		try {
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			json = ow.writeValueAsString(scoreList);
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

	@RequestMapping(value = "/teacher/memberExamSubject", method = RequestMethod.POST)
	public String memberExamSubjectList(int class_id,Model model) {
		List<ExamJoinSubject> rs = dao.memberExamSubjectList(class_id);
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
