package controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartResolver;

import bean.ClassList;
import dao.KswDao;

@Controller
public class KswController {
	private KswDao kswDao;
	private MultipartResolver multipartResolver;

	public void setKswDao(KswDao kswDao) {
		this.kswDao = kswDao;
	}

	@RequestMapping(value = "/attendance/list", method = RequestMethod.GET)
	public String classGetList(Model model) {
		List<ClassList> list = kswDao.getAllClass();
		model.addAttribute("list", list);
		return "/attendance/attendanceClassList";
	}

}
