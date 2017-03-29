package controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartResolver;

import bean.PersonSubList;
import command.AttendancePersonCommand;
import command.PersonSearch;
import dao.JshDao;

@Controller
@RequestMapping
public class JshController {
	private JshDao jshDao;
	private MultipartResolver multipartResolver;
	private PrintWriter writer = null;

	public void setJshDao(JshDao jshDao) {
		this.jshDao = jshDao;
	}

	@RequestMapping(value = "/attendance/person", method = RequestMethod.GET)
	public String attendPersonSubListGet(Model model) {
		int id = 1;
		List<PersonSubList> personSubList = jshDao.selectSubPerson(id);
		String stuSubject = jshDao.getSubjectName(id);
		double attendRate = jshDao.getAttendRate(id);
		model.addAttribute("attendRate", attendRate);
		model.addAttribute("subject", stuSubject);
		model.addAttribute("personSubList", personSubList);
		return "attendance/attendancePersonSubList";
	}

	@RequestMapping(value = "/attendance/person/{id}", method = RequestMethod.GET)
	public String attendPersonGet(@PathVariable("id") int studentId, Model model, PersonSearch personsearch) {
		
		List<PersonSubList> personSubList =jshDao.selectSubPerson(studentId);
		model.addAttribute("personSubList", personSubList);
		
		String student = jshDao.getStudentName(studentId);
		String studentEmail = jshDao.getStudentEmail(studentId);
		double attendRate = jshDao.getAttendRate(studentId);
		List<AttendancePersonCommand> attendancePersonCommand = jshDao.selectAllPerson(studentId);
		String stuSubject = jshDao.getSubjectName(studentId);
		
		int countAll = jshDao.count(studentId);
		int attendAll = jshDao.attendCount(studentId);
		int late = jshDao.lateCount(studentId);
		int goOut = jshDao.goOutCount(studentId);
		int absentCount = jshDao.absentCount(studentId);
		int leaveEarlyCount = jshDao.leaveEarlyCount(studentId);

		model.addAttribute("countAll", countAll);
		model.addAttribute("attendAll", attendAll);
		model.addAttribute("late", late);
		model.addAttribute("goOut", goOut);
		model.addAttribute("absentCount", absentCount);
		model.addAttribute("leaveEarlyCount", leaveEarlyCount);

		model.addAttribute("PersonSearch", personsearch);
		model.addAttribute("student", student);
		model.addAttribute("studentEmail", studentEmail);
		model.addAttribute("attendRate", attendRate);
		model.addAttribute("stuSubject", stuSubject);
		model.addAttribute("attendancePersonCommand", attendancePersonCommand);
		return "attendance/attendancePerson";
	}

	@RequestMapping(value = "/attendance/person/{id}", method = RequestMethod.POST)
	public String attendPersonPost(@PathVariable("id") int studentId,
			@RequestParam(value = "from", required = false) String strFrom,
			@RequestParam(value = "to", required = false) String strTo, Model model) {

		String student = jshDao.getStudentName(studentId);
		String studentEmail = jshDao.getStudentEmail(studentId);
		double attendRate = jshDao.getAttendRate(studentId);

		int fromForCondition = Integer.parseInt(strFrom);
		int toForCondition = Integer.parseInt(strTo);
		
		List<AttendancePersonCommand> attendancePersonCommand = 
				jshDao.searchPersonPeriod(studentId, strFrom, strTo);
		model.addAttribute("attendancePersonCommand", attendancePersonCommand);
		
//		if (fromForCondition < toForCondition) {
//			List<AttendancePersonCommand> attendancePersonCommand = 
//					jshDao.searchPersonPeriod(studentId, strFrom, strTo);
//			model.addAttribute("attendancePersonCommand", attendancePersonCommand);
//			
//		} else {
//			writer.println("<script type='text/javascript'>");
//			writer.println("alert('Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù.');");
//			writer.println("history.back();");
//			writer.println("</script>");
//			writer.flush();
//			List<AttendancePersonCommand> attendancePersonCommand = jshDao.selectAllPerson(studentId);
//			model.addAttribute("attendancePersonCommand", attendancePersonCommand);
//
//		}
		
		
		String stuSubject = jshDao.getSubjectName(studentId);

		int countAll = jshDao.count(studentId);
		int attendAll = jshDao.attendCount(studentId);
		int late = jshDao.lateCount(studentId);
		int goOut = jshDao.goOutCount(studentId);
		int absentCount = jshDao.absentCount(studentId);
		int leaveEarlyCount = jshDao.leaveEarlyCount(studentId);

		model.addAttribute("countAll", countAll);
		model.addAttribute("attendAll", attendAll);
		model.addAttribute("late", late);
		model.addAttribute("goOut", goOut);
		model.addAttribute("absentCount", absentCount);
		model.addAttribute("leaveEarlyCount", leaveEarlyCount);

		model.addAttribute("from", strFrom);
		model.addAttribute("to", strTo);
		model.addAttribute("student", student);
		model.addAttribute("studentEmail", studentEmail);
		model.addAttribute("attendRate", attendRate);
		model.addAttribute("stuSubject", stuSubject);
		return "attendance/attendancePerson";
	}

	public Date transDate(String d) {
		Date date = null;
		if (d != null) {
			SimpleDateFormat transFormat;
			transFormat = new SimpleDateFormat("yyMMdd");
			try {
				date = transFormat.parse(d);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return date;
	}

	// ï¿½ï¿½ï¿½ï¿½
	@RequestMapping(value = "/attendance/beLate/{id}", method = RequestMethod.GET)
	public String belateGet(@PathVariable("id") int studentId, Model model, PersonSearch personsearch) {
		String student = jshDao.getStudentName(studentId);
		String studentEmail = jshDao.getStudentEmail(studentId);
		String stuSubject = jshDao.getSubjectName(studentId);
		double attendRate = jshDao.getAttendRate(studentId);
		List<AttendancePersonCommand> attendancePersonCommand = jshDao.beLate(studentId);
		model.addAttribute("PersonSearch", personsearch);
		model.addAttribute("student", student);
		model.addAttribute("studentEmail", studentEmail);
		model.addAttribute("attendRate", attendRate);
		model.addAttribute("stuSubject", stuSubject);
		model.addAttribute("attendancePersonCommand", attendancePersonCommand);
		return "attendance/attendancePerson";
	}

	// ï¿½ï¿½ï¿½ï¿½
	@RequestMapping(value = "/attendance/goOut/{id}", method = RequestMethod.GET)
	public String goOutGet(@PathVariable("id") int studentId, Model model,
			@RequestParam(value = "from", required = false) String strFrom,
			@RequestParam(value = "to", required = false) String strTo) {

		String student = jshDao.getStudentName(studentId);
		String studentEmail = jshDao.getStudentEmail(studentId);
		String stuSubject = jshDao.getSubjectName(studentId);
		double attendRate = jshDao.getAttendRate(studentId);
		List<AttendancePersonCommand> attendancePersonCommand = jshDao.goOut(studentId);

		model.addAttribute("from", strFrom);
		model.addAttribute("to", strTo);
		model.addAttribute("student", student);
		model.addAttribute("studentEmail", studentEmail);
		model.addAttribute("attendRate", attendRate);
		model.addAttribute("stuSubject", stuSubject);
		model.addAttribute("attendancePersonCommand", attendancePersonCommand);
		return "attendance/attendancePerson";
	}

	// ï¿½ï¿½ï¿½ï¿½
	@RequestMapping(value = "/attendance/leaveEarly/{id}", method = RequestMethod.GET)
	public String attendPersonGet(@PathVariable("id") int studentId, Model model, PersonSearch personsearch,
			@RequestParam(value = "from", required = false) String strFrom,
			@RequestParam(value = "to", required = false) String strTo) {

		String student = jshDao.getStudentName(studentId);
		String studentEmail = jshDao.getStudentEmail(studentId);
		String stuSubject = jshDao.getSubjectName(studentId);
		double attendRate = jshDao.getAttendRate(studentId);
		List<AttendancePersonCommand> attendancePersonCommand = jshDao.leaveEarly(studentId);
		model.addAttribute("from", strFrom);
		model.addAttribute("to", strTo);
		model.addAttribute("PersonSearch", personsearch);
		model.addAttribute("student", student);
		model.addAttribute("studentEmail", studentEmail);
		model.addAttribute("attendRate", attendRate);
		model.addAttribute("stuSubject", stuSubject);
		model.addAttribute("attendancePersonCommand", attendancePersonCommand);
		return "attendance/attendancePerson";
	}

	// ï¿½á¼®
	@RequestMapping(value = "/attendance/absent/{id}", method = RequestMethod.GET)
	public String absentGet(@PathVariable("id") int studentId, Model model, PersonSearch personsearch) {
		String memberId = jshDao.getMemberId(studentId);
		String student = jshDao.getStudentName(studentId);
		String studentEmail = jshDao.getStudentEmail(studentId);
		String stuSubject = jshDao.getSubjectName(studentId);
		double attendRate = jshDao.getAttendRate(studentId);
		List<AttendancePersonCommand> attendancePersonCommand = jshDao.absent(studentId);

		model.addAttribute("memberId", memberId);
		model.addAttribute("PersonSearch", personsearch);
		model.addAttribute("student", student);
		model.addAttribute("studentEmail", studentEmail);
		model.addAttribute("attendRate", attendRate);
		model.addAttribute("stuSubject", stuSubject);
		model.addAttribute("attendancePersonCommand", attendancePersonCommand);
		return "attendance/attendancePerson";
	}

}
