package controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartResolver;

import bean.AuthInfo;
import bean.AuthMember;
import bean.ClassAttend;
import bean.Classes;
import bean.PersonSubList;
import command.AttendancePersonCommand;
import command.LoginCommand;
import command.LoginCommandValidator;
import command.PersonSearch;
import dao.LggDao;
import exception.IdPasswordNotMatchException;

@Controller
public class Lgg2Controller {
	private LggDao lggDao;
	private MultipartResolver multipartResolver;
	@Autowired
	public void setLggDao(LggDao lggDao) {
		this.lggDao = lggDao;
	}
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "home";
	}
	@RequestMapping(value = "/login",method=RequestMethod.GET)
	public String form(LoginCommand loginCommand ,@CookieValue(value="REMEMBER",required=false) Cookie rCookie) {
		if (rCookie != null) {
			loginCommand.setEmail(rCookie.getValue());
			loginCommand.setRemember(true);
		}
		return "login/loginForm";
	}
	
	@RequestMapping(value = "/login",method=RequestMethod.POST)
	public String submit(LoginCommand loginCommand ,Errors errors,HttpSession session,HttpServletResponse response) {
		new LoginCommandValidator().validate(loginCommand, errors);
		if(errors.hasErrors()) return "login/loginForm";
		try {
			AuthMember member = lggDao.memberByEmailAndPass(loginCommand.getEmail(),loginCommand.getPassword());
			if(member == null) throw new IdPasswordNotMatchException();
			AuthInfo authInfo = new AuthInfo(member.getM_id(), member.getM_email(), member.getM_name());
			if (authInfo.getM_email()==null || authInfo.getM_email().equals("")) {
				return "login/loginForm";
			}
			session.setAttribute("authInfo", authInfo);
			
			Cookie rCookie = new Cookie("REMEMBER",	 loginCommand.getEmail());
			rCookie.setPath("/");
			if (loginCommand.isRemember()) {
				rCookie.setMaxAge(60*60*24*30);
			} else {
				rCookie.setMaxAge(0);
			}
			response.addCookie(rCookie);
			return "redirect:/";
		} catch (IdPasswordNotMatchException e) {
			errors.reject("idPasswordNotMatching");
			return "login/loginForm";
		}
	}
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
	
	@RequestMapping(value = "/attendance/person", method = RequestMethod.GET)
	public String attendPersonSubListGet(Model model) {
		int id = 1;
		List<ClassAttend> ClassAttends = lggDao.memberClassAttendList(id);
		model.addAttribute("ClassAttends", ClassAttends);
		return "attendance/attendancePersonSubList";
	}

	@RequestMapping(value = "/attendance/person/{class_id}/{m_id}", method = RequestMethod.GET)
	public String attendPersonGet(@PathVariable("class_id") int class_id,@PathVariable("m_id") int m_id, Model model, PersonSearch personsearch) {
		ClassAttend ClassAttend = lggDao.memberClassAttendList(class_id,m_id);
		AuthMember authMmember = lggDao.selectMember(m_id);
		model.addAttribute("ClassAttend", ClassAttend);
		model.addAttribute("authMember", authMmember);
		return "attendance/attendancePerson";
	}

	@RequestMapping(value = "/attendance/person/{class_id}/{m_id}", method = RequestMethod.POST)
	public String attendPersonPost(@PathVariable("id") int studentId,
			@RequestParam(value = "from", required = false) String strFrom,
			@RequestParam(value = "to", required = false) String strTo, Model model) {

		String student = lggDao.getStudentName(studentId);
		String studentEmail = lggDao.getStudentEmail(studentId);

		int fromForCondition = Integer.parseInt(strFrom);
		int toForCondition = Integer.parseInt(strTo);
		
		List<AttendancePersonCommand> attendancePersonCommand = 
				lggDao.searchPersonPeriod(studentId, strFrom, strTo);
		model.addAttribute("attendancePersonCommand", attendancePersonCommand);
		
//		if (fromForCondition < toForCondition) {
//			List<AttendancePersonCommand> attendancePersonCommand = 
//					jshDao.searchPersonPeriod(studentId, strFrom, strTo);
//			model.addAttribute("attendancePersonCommand", attendancePersonCommand);
//			
//		} else {
//			writer.println("<script type='text/javascript'>");
//			writer.println("alert('�߸��� �Է��Դϴ�.');");
//			writer.println("history.back();");
//			writer.println("</script>");
//			writer.flush();
//			List<AttendancePersonCommand> attendancePersonCommand = jshDao.selectAllPerson(studentId);
//			model.addAttribute("attendancePersonCommand", attendancePersonCommand);
//
//		}
		
		
		String stuSubject = lggDao.getSubjectName(studentId);

		int countAll = lggDao.count(studentId);
		int attendAll = lggDao.attendCount(studentId);
		int late = lggDao.lateCount(studentId);
		int goOut = lggDao.goOutCount(studentId);
		int absentCount = lggDao.absentCount(studentId);
		int leaveEarlyCount = lggDao.leaveEarlyCount(studentId);

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

	// ����
	@RequestMapping(value = "/attendance/beLate/{id}", method = RequestMethod.GET)
	public String belateGet(@PathVariable("id") int studentId, Model model, PersonSearch personsearch) {
		String student = lggDao.getStudentName(studentId);
		String studentEmail = lggDao.getStudentEmail(studentId);
		String stuSubject = lggDao.getSubjectName(studentId);
		List<AttendancePersonCommand> attendancePersonCommand = lggDao.beLate(studentId);
		model.addAttribute("PersonSearch", personsearch);
		model.addAttribute("student", student);
		model.addAttribute("studentEmail", studentEmail);
		model.addAttribute("stuSubject", stuSubject);
		model.addAttribute("attendancePersonCommand", attendancePersonCommand);
		return "attendance/attendancePerson";
	}

	// ����
	@RequestMapping(value = "/attendance/goOut/{id}", method = RequestMethod.GET)
	public String goOutGet(@PathVariable("id") int studentId, Model model,
			@RequestParam(value = "from", required = false) String strFrom,
			@RequestParam(value = "to", required = false) String strTo) {

		String student = lggDao.getStudentName(studentId);
		String studentEmail = lggDao.getStudentEmail(studentId);
		String stuSubject = lggDao.getSubjectName(studentId);
		List<AttendancePersonCommand> attendancePersonCommand = lggDao.goOut(studentId);

		model.addAttribute("from", strFrom);
		model.addAttribute("to", strTo);
		model.addAttribute("student", student);
		model.addAttribute("studentEmail", studentEmail);
		model.addAttribute("stuSubject", stuSubject);
		model.addAttribute("attendancePersonCommand", attendancePersonCommand);
		return "attendance/attendancePerson";
	}

	// ����
	@RequestMapping(value = "/attendance/leaveEarly/{id}", method = RequestMethod.GET)
	public String attendPersonGet(@PathVariable("id") int studentId, Model model, PersonSearch personsearch,
			@RequestParam(value = "from", required = false) String strFrom,
			@RequestParam(value = "to", required = false) String strTo) {

		String student = lggDao.getStudentName(studentId);
		String studentEmail = lggDao.getStudentEmail(studentId);
		String stuSubject = lggDao.getSubjectName(studentId);
		List<AttendancePersonCommand> attendancePersonCommand = lggDao.leaveEarly(studentId);
		model.addAttribute("from", strFrom);
		model.addAttribute("to", strTo);
		model.addAttribute("PersonSearch", personsearch);
		model.addAttribute("student", student);
		model.addAttribute("studentEmail", studentEmail);
		model.addAttribute("stuSubject", stuSubject);
		model.addAttribute("attendancePersonCommand", attendancePersonCommand);
		return "attendance/attendancePerson";
	}

	// �Ἦ
	@RequestMapping(value = "/attendance/absent/{id}", method = RequestMethod.GET)
	public String absentGet(@PathVariable("id") int studentId, Model model, PersonSearch personsearch) {
		String memberId = lggDao.getMemberId(studentId);
		String student = lggDao.getStudentName(studentId);
		String studentEmail = lggDao.getStudentEmail(studentId);
		String stuSubject = lggDao.getSubjectName(studentId);
		List<AttendancePersonCommand> attendancePersonCommand = lggDao.absent(studentId);

		model.addAttribute("memberId", memberId);
		model.addAttribute("PersonSearch", personsearch);
		model.addAttribute("student", student);
		model.addAttribute("studentEmail", studentEmail);
		model.addAttribute("stuSubject", stuSubject);
		model.addAttribute("attendancePersonCommand", attendancePersonCommand);
		return "attendance/attendancePerson";
	}
	
}
