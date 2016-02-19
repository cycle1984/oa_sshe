package cycle.oa_sshe.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cycle.oa_sshe.domain.Role;
import cycle.oa_sshe.domain.SignInfo;
import cycle.oa_sshe.domain.SysBase;
import cycle.oa_sshe.domain.Unit;
import cycle.oa_sshe.domain.User;
import cycle.oa_sshe.base.BaseAction;
import cycle.oa_sshe.domain.easyui.Grid;
import cycle.oa_sshe.domain.easyui.Json;
import cycle.oa_sshe.utils.HqlFilter;
import cycle.oa_sshe.utils.IpUtil;
import cycle.oa_sshe.utils.MyUtils;

/**
 * 用户表action
 * @author jyj
 *
 */

@Controller("userAction")
@Scope("prototype")
public class UserAction extends BaseAction<User> {

	private static final long serialVersionUID = -5934293027052655335L;
	
	private Integer unitId;//前台传过来的单位主键
	private Integer roleId;//前台传过来的角色主键
	
	private String oldPwd;//用于获得用户旧密码
	/**
	 * 新增
	 */
	public void save(){
		Json json = new Json();
		if(model!=null){
			User user = new User();
			BeanUtils.copyProperties(model, user);
			user.setCreatedatetime(new Date());//创建时间
			user.setPwd(DigestUtils.md5Hex("jyj123456"));//默认密码
			if(unitId!=null){
				Unit unit = unitService.getById(unitId);
				user.setUnit(unit);
			}
			if(roleId!=null){
				Role role = roleService.getById(roleId);
				user.setRole(role);
			}
			try {
				userService.save(user);
				json.setSuccess(true);
				json.setMsg("新建用户【"+user.getName()+"】成功");
				json.setObj(user);
			} catch (Exception e) {
				json.setMsg("新建失败");
				e.printStackTrace();
			}
		}
		writeJson(json);
	}
	
	/**
	 * 注册
	 */
	public void register(){
		Json json = new Json();
		if(model!=null){
			User user = new User();
			BeanUtils.copyProperties(model, user);
			user.setCreatedatetime(new Date());//创建时间
			user.setPwd(DigestUtils.md5Hex(model.getPwd()));//用户设定的的密码
			if(unitId!=null){//设定所属单位
				Unit unit = unitService.getById(unitId);
				user.setUnit(unit);
			}
			//设定为待审核用户
			Role role = roleService.getByName("待审核用户");
			user.setRole(role);
			try {
				userService.save(user);
				json.setSuccess(true);
				json.setMsg("注册用户【"+user.getName()+"】成功，请联系管理员开通相应权限后再登陆！");
				json.setObj(user);
			} catch (Exception e) {
				json.setMsg("新建失败");
				e.printStackTrace();
			}
		}
		writeJson(json);
	}
	
	/**
	 * 注册功能里的根据登陆名称查询是否存在
	 * 
	 */
	public void searchByLoginName() throws IOException{
		String loginN = model.getLoginName().trim();//去掉前后空格
		if(loginN!=null||!"".equals(loginN)){
			User u = userService.searchByLoginName(loginN);
			if(u!=null){
				response.getWriter().write("false");
			}else{
				response.getWriter().write("true");
			}
		}
	}
	
	
	
	/**
	 * 修改
	 */
	public void edit(){
		Json json = new Json();
		User user = userService.getById(model.getId());
		BeanUtils.copyProperties(model, user,new String[] { "createdatetime","pwd" });//第三个字段为不赋值的数据
		user.setUpdatedatetime(new Date());//设置修改时间
		if(unitId!=null){
			user.setUnit(unitService.getById(unitId));//设置所属单位
		}
		if(roleId!=null){
			user.setRole(roleService.getById(roleId));//设置所属权限组
		}
		try {
			userService.update(user);
			json.setSuccess(true);
			json.setMsg("用户【" + user.getName() + "】修改成功");
			json.setObj(user);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败");
		}

		writeJson(json);
	}
	
	/**
	 * 删除一批对象
	 */
	public void delete() {
		Json json = new Json();
		String hql = "delete User u where u.id in (";
		String[] nids = ids.split(",");
		for (int i = 0; i < nids.length; i++) {
			if (i > 0) {
				hql += ",";
			}
			hql += "'" + nids[i] + "'";
		}
		hql += ")";
		try {
			int num = userService.executeHql(hql);
			json.setSuccess(true);
			json.setMsg("成功删除【" + num + "】条数据！");
			// json.setObj(o);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("删除失败");
		}
		writeJson(json);
	}
	
	/**
	 * 分页查询用户
	 * Grid
	 */
	public void grid(){
		Grid grid = new Grid();
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		hqlFilter.addFilter("QUERY_t#loginName_S_NE", "admin");//添加过滤条件,不显示超级管理员
		grid.setTotal(userService.countByFilter(hqlFilter));//总记录数
		grid.setRows(userService.findByFilter(hqlFilter,page,rows));//获得当前页显示的数据
		writeJson(grid);
	}
	
	/** 登录页面 */
	public String loginUI() throws Exception {
		return "loginUI";
	}
	
	/**
	 * 登陆
	 * @return
	 */
	public void login(){
		Json j = new Json();
		User user = userService.login(model.getLoginName(), model.getPwd());
		if(user!=null){
			
			if("admin".equals(user.getLoginName())){//是管理员登录的情况
				session.setAttribute("userSession", user);//将用户信息放到session域
				j.setSuccess(true);
				j.setMsg("登录成功!");
			}else{//非管理员登录
				
				//系统配置存入session，主要是存刷新时间
				List<SysBase> l = sysBaseService.find("from SysBase");
				if(!l.isEmpty()){
					SysBase config = l.get(0);
					session.setAttribute("config", config);
				}
				Hibernate.initialize(user.getRole());//强制加载对象内容
				if(user.getRole()!=null){
					Hibernate.initialize(user.getRole().getMyResources());
					Hibernate.initialize(user.getUnit());
				}
				if("待审核用户".equals(user.getRole().getName())){
					j.setMsg("您是待审核用户，请联系管理员开通相应权限后再登陆！");
				}else{
					session.setAttribute("userSession", user);//将用户信息放到session域
					j.setSuccess(true);
					j.setMsg("登录成功!");
				}
				
			}
		}else{
			j.setMsg("登陆失败，账号或密码不正确!");
		}
		
		writeJson(j);
	}
	
	/**
	 * 注销、退出
	 * @return
	 */
	public void logout()  throws Exception{
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.removeAttribute("userSession");
		Json j = new Json();
		j.setSuccess(true);
		writeJson(j);
	}
	
	/**
	 * 初始化用户密码jyj123456
	 * @return
	 */
	public void initPassword() throws Exception{
		Json json = new Json();
		Integer[] u_ids = MyUtils.string2Integer(ids);
		try {
			for (int i = 0; i < u_ids.length; i++) {
				User user = userService.getById(u_ids[i]);
				// 2，设置要修改的属性（要使用MD5摘要）
				String md5Digest = DigestUtils.md5Hex("jyj123456");
				user.setPwd(md5Digest);
				// 3，更新到数据库
				userService.update(user);
			}
			json.setSuccess(true);
			json.setMsg("成功重置【"+u_ids.length+"】条数据");
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("重置失败");
		}
		writeJson(json);
	}
	
	/**
	 * 签收
	 */
	public void searchByLoginNameAndPwd(){
		Json json = new Json();
		User user = (User) session.getAttribute("userSession");//获得session中的当前登录用户
		if(user!=null){
			if(user.getPwd().equals(DigestUtils.md5Hex(model.getPwd()))){//和当前登录用户密码一致
				SignInfo si = signInfoService.getById(model.getId());//获得本单位的签收信息对象
				si.setState(true);//设为已签收
				si.setSignDate(new Date());//设置签收时间
				si.setSignUserName(user.getName());//签收人姓名
				si.setIp(IpUtil.getIpAddr(request));//签收IP
				try {
					signInfoService.update(si);
					json.setSuccess(true);
					json.setMsg("签收成功");
				} catch (Exception e) {
					json.setMsg("操作失败!!");
					e.printStackTrace();
				}
			}else{
				json.setMsg("密码错误，请重新输入!!");
			}
		}
		writeJson(json);
	}
	
	/**
	 * 修改自己的密码
	 */
	public void updateCurrentPwd(){
		Json json = new Json();
		User user = (User) session.getAttribute("userSession");
		if(DigestUtils.md5Hex(oldPwd).equals(user.getPwd())){
			User u = userService.getById(user.getId());
			u.setPwd(DigestUtils.md5Hex(model.getPwd()));
			u.setUpdatedatetime(new Date());
			try {
				userService.update(u);
				json.setMsg("密码修改成功，请使用新的密码重新登陆!");
				json.setSuccess(true);
			} catch (Exception e) {
				json.setMsg("密码修改失败!");
				e.printStackTrace();
			}
		}else{
			json.setMsg("输入的旧密码与当前使用的密码不匹配，修改失败!");
		}
		writeJson(json);
	}
	
	//用户修改自己个人信息页面
	public String modifyInfoUI(){
		return "modifyInfoUI";
	}
	
	//用户修改个人信息
	public void modifyInfo(){
		Json json = new Json();
		User user = (User) session.getAttribute("userSession");
		if(user.getPwd().equals(DigestUtils.md5Hex(model.getPwd()))){
			User u = userService.getById(user.getId());
			u.setTel(model.getTel());
			u.setPhone(model.getPhone());
			u.setGender(model.getGender());
			try {
				userService.update(u);
				json.setSuccess(true);
				json.setMsg("修改成功");
			} catch (Exception e) {
				e.printStackTrace();
			}
			session.setAttribute("userSession", u);//更新session信息
		}else{
			json.setMsg("登陆密码验证错误");
		}
		writeJson(json);
	}
	
	/**
	 * 批量修改权限窗口
	 * 
	 */
	public String checkUserJsp(){
		return "checkUser";
	}
	
	public void checkUser(){
		Json json = new Json();
		if(roleId!=null&&ids!=null){
			Role role = roleService.getById(roleId);
			Integer[] i = MyUtils.string2Integer(ids);
				try {
					for (Integer integer : i) {
						User user = userService.getById(integer);
						user.setRole(role);
						userService.update(user);
						
					}
					json.setSuccess(true);
					json.setMsg("审批成功");
				} catch (Exception e) {
					e.printStackTrace();
					json.setMsg("审批失败");
				}
		}
		writeJson(json);
	}
	
	/**
	 * 跳转到通讯录页面
	 * @return
	 */
	public String contactsGridJsp(){
		return "contactsJsp";
	}
	
	/**
	 * 通讯录数据
	 */
	public void contactsGrid(){
		Grid grid = new Grid();
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		hqlFilter.addFilter("QUERY_t#loginName_S_NE", "admin");//添加过滤条件,不显示超级管理员
		grid.setTotal(userService.countByFilter(hqlFilter));//总记录数
		grid.setRows(userService.findByFilter(hqlFilter,page,rows));//获得当前页显示的数据
		writeJson(grid);
	}
	
	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	
}
