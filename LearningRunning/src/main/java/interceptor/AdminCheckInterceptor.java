package interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import bean.AuthInfo;

public class AdminCheckInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession(false);
		if (session!=null) {
			AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
			if (authInfo.isAdmin()) {
				return true;
			}
		}
		response.sendRedirect(request.getContextPath());
		return false;
	}
	
}
