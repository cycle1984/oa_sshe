package cycle.oa_sshe.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cycle.oa_sshe.domain.User;

public class LoginLog {

	public void log(HttpServletRequest request){
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("userSession");
		if(u!=null){
			System.out.println("u="+u.getName());
		}
		System.out.println("ddd");
	}
}
