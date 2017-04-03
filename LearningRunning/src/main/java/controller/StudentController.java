package controller;

import org.springframework.beans.factory.annotation.Autowired;

import dao.StudentDao;

public class StudentController {
	private StudentDao dao;
	@Autowired
	public void setStudentDao(StudentDao dao) {
		this.dao = dao;
	}

}
