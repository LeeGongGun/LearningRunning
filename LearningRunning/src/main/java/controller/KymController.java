package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartResolver;

import dao.KymDao;
@Controller
public class KymController {
	private KymDao kymDao;
	private MultipartResolver multipartResolver;
	public void setKymDao(KymDao kymDao) {
		this.kymDao = kymDao;
	}

	
}
