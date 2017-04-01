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
import org.springframework.web.servlet.ModelAndView;

import bean.ClassList;
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

	@RequestMapping(value = "/teacher/counsel/counselManage", method = RequestMethod.GET)
	public String counselGetList(Model model) {
		return "/counsel/counselManage";
	}
	
	@RequestMapping(value = "/teacher/counsel/counselDetail", method = RequestMethod.GET)
	public String cunslDtailGetList(Model model) {
		return "/counsel/counselDetail";
	}

}
