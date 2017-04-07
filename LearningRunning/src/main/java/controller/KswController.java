package controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import bean.ClassList;
import dao.KswDao;

@Controller
public class KswController {
	private KswDao kswDao;
	private MultipartResolver multipartResolver;

	public void setKswDao(KswDao kswDao) {
		this.kswDao = kswDao;
	}

//	@RequestMapping(value = "/attendance/list", method = RequestMethod.GET)
//	public ModelAndView classGetList(Model model) {
//		List<ClassList> list = kswDao.getAllClass();
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("list", list);
//		mav.setViewName("/attendance/attendanceClassList");
//		return mav;
//	}
	
	@RequestMapping(value="/attendance/dashboard", method = RequestMethod.GET)
	public String getDashBoard(@RequestParam(value="active", defaultValue="dashboard") String active){
		return "/attendance/dashBoard";
	}
	
	@RequestMapping(value="/attendance/classlist", method = RequestMethod.GET)
	public String getClassList(@RequestParam(value="active", defaultValue="attendance") String active){
		return "/attendance/classList";
	}
	
	@RequestMapping(value="/attendance/test", method = RequestMethod.GET)
	public String getTest(@RequestParam(value="active", defaultValue="attendance") String active){
		return "/attendance/test";
	}
}
