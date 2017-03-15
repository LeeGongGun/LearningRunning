package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartResolver;

import dao.JshDao;
import dao.LggDao;
@Controller
public class JshController {
	private JshDao jshDao;
	private MultipartResolver multipartResolver;
	public void setJshDao(JshDao jshDao) {
		this.jshDao = jshDao;
	}

	
}
