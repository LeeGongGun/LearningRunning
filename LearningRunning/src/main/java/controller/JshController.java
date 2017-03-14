package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartResolver;

import dao.LggDao;
@Controller
public class JshController {
	private LggDao ggDao;
	private MultipartResolver multipartResolver;
	public void setBoardDao(LggDao ggDao) {
		this.ggDao = ggDao;
	}

	
}
