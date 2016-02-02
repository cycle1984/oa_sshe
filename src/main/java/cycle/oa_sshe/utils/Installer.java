package cycle.oa_sshe.utils;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cycle.oa_sshe.domain.MyResource;
import cycle.oa_sshe.domain.Role;
import cycle.oa_sshe.domain.User;

/**
 * 系统初始化安装
 * @author jyj
 *
 */
@Component
public class Installer {
	
	@Resource
	private SessionFactory sessionFactory;
	
	@Transactional
	public void install(){
		Session session = sessionFactory.getCurrentSession();
		// ==============================================================
		// 保存超级管理员用户
		User user = new User();
		user.setLoginName("admin");
		user.setPwd(DigestUtils.md5Hex("admin"));
		user.setName("超级管理员");
		user.setCreatedatetime(new Date());
		session.save(user); // 保存
		// ==============================系统参数设置================================a
		// ==============================================================
		Role r = new Role();
		r.setName("待审核用户");
		r.setDescription("无任何权限");
		session.save(r);
		// ==============================================================
		// 保存权限数据
		
		MyResource menu = new MyResource("公文传输系统",null,null,0);//根节点
		
		MyResource menu2 = new MyResource("信息管理",null,menu,0);
		MyResource menu21 = new MyResource("发文管理","document_toGridJsp",menu2,0);
		
		MyResource menu3 = new MyResource("历史公文库",null,menu,0);
		MyResource menu31 = new MyResource("历史发文","document_historyPublishGridJSP",menu3,0);
		MyResource menu32 = new MyResource("历史收文","documentInfo_historytAcceptGridJSP",menu3,0);
		
		MyResource menu4 = new MyResource("常用工具",null,menu,0);
		MyResource menu41 = new MyResource("用户通讯录","user_contacts",menu4,0);
		
		MyResource menu1 = new MyResource("系统管理",null,menu,0);
		MyResource menu11 = new MyResource("用户管理","user_toGridJsp",menu1,0);
		MyResource menu12 = new MyResource("单位管理","unit_toGridJsp",menu1,0);
		MyResource menu13 = new MyResource("机构管理","myGroup_toGridJsp",menu1,0);
		MyResource menu14 = new MyResource("权限组管理","role_toGridJsp",menu1,0);
		MyResource menu15 = new MyResource("登陆日志","loginLog_loginLogListJSP",menu1,0);
		MyResource menu16 = new MyResource("系统配置","sysBase_getConfig",menu1,0);
		
		session.save(menu);
		session.save(menu2);
		session.save(menu21);
		
		session.save(menu3);
		session.save(menu31);
		session.save(menu32);
		
		session.save(menu4);
		session.save(menu41);
		
		session.save(menu1);
		session.save(menu11);
		session.save(menu12);
		session.save(menu13);
		session.save(menu14);
		session.save(menu15);
		session.save(menu16);
		
		session.save(new MyResource("发文列表","document_publishGrid",menu21,1));
		session.save(new MyResource("公文添加","document_save",menu21,1));
		session.save(new MyResource("公文删除","document_delete",menu21,1));
		
		session.save(new MyResource("用户列表","user_grid",menu11,1));
		session.save(new MyResource("用户添加","user_save",menu11,1));
		session.save(new MyResource("用户修改","user_edit",menu11,1));
		session.save(new MyResource("用户删除","user_delete",menu11,1));
		session.save(new MyResource("用户密码重置","user_initPassword",menu11,1));
		
		session.save(new MyResource("单位列表","unit_grid",menu12,1));
		session.save(new MyResource("单位添加","unit_save",menu12,1));
		session.save(new MyResource("单位修改","unit_edit",menu12,1));
		session.save(new MyResource("单位删除","unit_delete",menu12,1));
		
		session.save(new MyResource("机构列表","myGroup_grid",menu13,1));
		session.save(new MyResource("机构添加","myGroup_save",menu13,1));
		session.save(new MyResource("机构修改","myGroup_edit",menu13,1));
		session.save(new MyResource("机构删除","myGroup_delete",menu13,1));
		
		session.save(new MyResource("权限组列表","role_toGridJsp",menu14,1));
		session.save(new MyResource("权限组添加","role_save",menu14,1));
		session.save(new MyResource("权限组修改","role_edit",menu14,1));
		session.save(new MyResource("权限组删除","role_delete",menu14,1));
		session.save(new MyResource("设置权限","role_setMyResourceUI",menu14,1));
		
	}
	
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		Installer installer = (Installer) ac.getBean("installer");
		installer.install();
	}
}
