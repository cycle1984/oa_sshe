package cycle.oa_sshe.utils;

import java.util.Collection;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cycle.oa_sshe.domain.MyResource;
import cycle.oa_sshe.service.MyResourceService;

/**
 * 系统启动时准备好的数据
 * @author jyj
 *
 */
public class SysStartListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// 获取容器与相关的Service对象
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		MyResourceService myResourceService = (MyResourceService) ac.getBean("myResourceService");
		
		//服务器启动，马上获取左侧菜单信息，如有更新，需要重启tomcat
		List<MyResource> allLeftMenu = myResourceService.find("from MyResource r  where r.type=0");
		sce.getServletContext().setAttribute("allLeftMenu", allLeftMenu);
		
		// 准备数据：allPrivilegeUrls
		Collection<String> allMyResourceUrls = myResourceService.getAllMyResourceUrls();
		sce.getServletContext().setAttribute("allMyResourceUrls", allMyResourceUrls);

	}

}
