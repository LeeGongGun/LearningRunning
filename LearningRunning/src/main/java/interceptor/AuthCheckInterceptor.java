package interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthCheckInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession(false);
		String reqConType = (request.getContentType()==null)?"null":request.getContentType().split(";")[0];
		if (session!=null) {
			Object authInfo = session.getAttribute("authInfo");
			if (authInfo!=null) {
				return true;
			}
		}
		String redirectAddr = (reqConType.equals("null"))?request.getContextPath()+"/login":request.getContextPath()+"/error";
		response.sendRedirect(redirectAddr);
		return false;
	}
	
}
