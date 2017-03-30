package controller;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartResolver;

import bean.AuthInfo;
import bean.AuthMember;
import command.LoginCommand;
import command.LoginCommandValidator;
import dao.LggDao;
import exception.IdPasswordNotMatchException;

@Controller
public class Lgg2Controller {
	private LggDao lggDao;
	private MultipartResolver multipartResolver;
	@Autowired
	public void setLggDao(LggDao lggDao) {
		this.lggDao = lggDao;
	}
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "home";
	}
	@RequestMapping(value = "/login",method=RequestMethod.GET)
	public String form(LoginCommand loginCommand ,@CookieValue(value="REMEMBER",required=false) Cookie rCookie) {
		if (rCookie != null) {
			loginCommand.setEmail(rCookie.getValue());
			loginCommand.setRemember(true);
		}
		return "login/loginForm";
	}
	
	@RequestMapping(value = "/login",method=RequestMethod.POST)
	public String submit(LoginCommand loginCommand ,Errors errors,HttpSession session,HttpServletResponse response) {
		new LoginCommandValidator().validate(loginCommand, errors);
		if(errors.hasErrors()) return "login/loginForm";
		try {
			AuthMember member = lggDao.memberByEmailAndPass(loginCommand.getEmail(),loginCommand.getPassword());
			if(member == null) throw new IdPasswordNotMatchException();
			AuthInfo authInfo = new AuthInfo(member.getM_id(), member.getM_email(), member.getM_name());
			if (authInfo.getM_email()==null || authInfo.getM_email().equals("")) {
				return "login/loginForm";
			}
			session.setAttribute("authInfo", authInfo);
			
			Cookie rCookie = new Cookie("REMEMBER",	 loginCommand.getEmail());
			rCookie.setPath("/");
			if (loginCommand.isRemember()) {
				rCookie.setMaxAge(60*60*24*30);
			} else {
				rCookie.setMaxAge(0);
			}
			response.addCookie(rCookie);
			return "redirect:/";
		} catch (IdPasswordNotMatchException e) {
			errors.reject("idPasswordNotMatching");
			return "login/loginForm";
		}
	}
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
	
	
}
