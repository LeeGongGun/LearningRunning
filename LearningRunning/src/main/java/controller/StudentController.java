package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dao.StudentDao;

@Controller
@RequestMapping("/student")
public class StudentController {
	private StudentDao dao;
	@Autowired
	public void setStudentDao(StudentDao dao) {
		this.dao = dao;
	}

}
