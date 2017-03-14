package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartResolver;

import dao.LggDao;
@Controller
public class KymController {
	private LggDao ggDao;
	private MultipartResolver multipartResolver;
	public void setBoardDao(LggDao ggDao) {
		this.ggDao = ggDao;
	}

	
}
