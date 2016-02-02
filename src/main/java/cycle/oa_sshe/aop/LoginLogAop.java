package cycle.oa_sshe.aop;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cycle.oa_sshe.domain.LoginLog;
import cycle.oa_sshe.domain.User;
import cycle.oa_sshe.service.LoginLogService;
import cycle.oa_sshe.utils.IpUtil;

public class LoginLogAop {//继承ActionSupport为了获得request

	
	@Resource(name="loginLogService")
	private LoginLogService loginLogService;
	
	public void log(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = ServletActionContext.getRequest().getSession();
		User u = (User) session.getAttribute("userSession");
		if(u!=null){
			LoginLog loginLog = new LoginLog();
			loginLog.setLogDate(new Date());
			loginLog.setUserName(u.getName());
			if(u.getUnit()!=null){
				loginLog.setUnitName(u.getUnit().getName());
			}
			loginLog.setIp(IpUtil.getIpAddr(request));
			loginLogService.save(loginLog);
		}
	}
}
