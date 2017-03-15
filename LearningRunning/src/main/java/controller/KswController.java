package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartResolver;

import dao.KswDao;

@Controller
public class KswController {
	private KswDao kswDao;
	private MultipartResolver multipartResolver;

	public void setKswDao(KswDao kswDao) {
		this.kswDao = kswDao;
	}

	@RequestMapping(value = "/attendance/list", method = RequestMethod.GET)
	public String classGetList(KswDao dao, Model model) {

		return "/attendance/attendanceInsert";
	}

}
