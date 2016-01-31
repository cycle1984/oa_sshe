package cycle.oa_sshe.aop;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cycle.oa_sshe.domain.User;

public class LoginLog extends ActionSupport{

	public void log(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		User u = (User) session.getAttribute("userSession");
		if(u!=null){
			System.out.println("u="+u.getName());
		}
		System.out.println("ddd");
	}
}
