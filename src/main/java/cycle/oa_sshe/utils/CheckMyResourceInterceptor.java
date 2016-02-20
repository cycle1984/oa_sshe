package cycle.oa_sshe.utils;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import cycle.oa_sshe.domain.User;

public class CheckMyResourceInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -7232783289524335980L;
	
	/**
     * ajax session超时返回值
     */
    private static String AJAX_TIME_OUT = null;

    /**
     * ajax 请求无权限返回值
     */
    private static String AJAX_NO_LIMIT = null;

    /**
     * ajax 请求异常返回值 （在每个ajax请求中处理）
     */


    static
    {
        AJAX_TIME_OUT = "ajaxSessionTimeOut";
        AJAX_NO_LIMIT = "ajaxNoLimit";

    }
	

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		//
		HttpServletRequest req = ServletActionContext.getRequest();
		//获得当前请求url
//		String namespace = invocation.getProxy().getNamespace();
		String actionName = invocation.getProxy().getActionName();
		String url = actionName;
		System.out.println("url="+url);
		//获得请求类型
		String type = req.getHeader("X-Requested-With");
		User user = (User) req.getSession().getAttribute("userSession");
		if(user==null){//session空
			if(
					url.startsWith("user_login")//去登录
					||url.startsWith("user_logout")//去退出
					//注册
					||url.startsWith("myGroup_findAll")//注册页面需要查询的所有机构列表
					||url.startsWith("unit_getUnitsByMyGroupId")//注册页面需要根据机构Id查询的所有单位列表
					||url.equals("user_register")
					||url.startsWith("user_searchByLoginName")
			  ){
				return invocation.invoke();//放行
			}else  if ("XMLHttpRequest".equalsIgnoreCase(type)){  //ajax请求 session过期时 返回字符串
				 PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
                 printWriter.print(AJAX_TIME_OUT);
                 printWriter.flush();
                 printWriter.close();
                 
                 System.out.println("没有登录，拦截");

                 return null;
			}else{
				req.setAttribute("msg", "用户登录会话已过期，请重新登录！");
                return "noSession";
			}
		}else {
			if(user.hasMyResourceByUrl(url)){
				return invocation.invoke();
			}else{
				if ("XMLHttpRequest".equalsIgnoreCase(type)){// 如果没有权限，就转到提示页面
	                 PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	                 printWriter.print(AJAX_NO_LIMIT);
	                 printWriter.flush();
	                 printWriter.close();
	                 System.out.println("没有权限，");
	                 return null;
	             } else {//其他Http请求 直接返回页面
	            	 System.out.println("没有权限，不放");
	            	 req.setAttribute("msg", "无相应操作权限，请联系系统管理员！");
	                 return "noLimit";
	             }
			}
		}
		
	}

}
