package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartResolver;

import dao.KswDao;
@Controller
public class KswController {
	private KswDao kswDao;
	private MultipartResolver multipartResolver;
	public void setKswDao(KswDao kswDao) {
		this.kswDao = kswDao;
	}

	
}
