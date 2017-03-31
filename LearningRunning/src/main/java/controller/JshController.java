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


}
