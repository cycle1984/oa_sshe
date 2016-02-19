package cycle.oa_sshe.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import com.opensymphony.xwork2.ActionContext;

/**
 * 用户表
 * @author jyj
 *
 */
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -273681580959694193L;

	private Integer id;//主键
	private Date createdatetime;// 创建日期
	private Date updatedatetime;// 最后修改时间
	private String loginName;// 登陆名
	private String pwd;// 密码
	private String name;// 真实姓名
	private String phone;//手机号码
	private String tel;//办公电话
	private String gender; // 性别
	private String photo;//照片
	private String description;//描述
	private String dep;//所属科室
	
	private Unit unit;//所属单位
	private Role role;//角色
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getCreatedatetime() {
		return createdatetime;
	}
	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}
	public Date getUpdatedatetime() {
		return updatedatetime;
	}
	public void setUpdatedatetime(Date updatedatetime) {
		this.updatedatetime = updatedatetime;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDep() {
		return dep;
	}
	public void setDep(String dep) {
		this.dep = dep;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	//根据struts<s:a>标签的title值判断是否拥有该权限
	/**
	 * 
	 * 判断本用户是否有显示<a>超链接的的权限
	 * 用<a>标签的title和权限里的name对比
	 * @param title
	 * @return
	 */
	public boolean hasMyResourceByTitle(String title) {
		if(isAdmin()){
			return true;
		}
		
		//非超级管理员判断是否拥有此权限
		for (MyResource mr : role.getMyResources()) {
			if(title.equals(mr.getName())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * 判断本用户是否有显示<a>超链接的的权限
	 * 用<a>标签的url和权限里的url对比
	 */
	public Boolean hasMyResourceByUrl(String url){
		if(isAdmin()){
			return true;
		}
		// >> 去掉url后面的参数
		int pos = url.indexOf("?");
		if(pos!=-1){
			url=url.substring(0, pos);
		}
		// >> 去掉UI后缀
		if(url.endsWith("UI")){
			url=url.substring(0, url.length()-2);
		}
		if(url.endsWith("Jsp")){
			url=url.substring(0, url.length()-3);
		}
		// 本URL不需要控制，用户登录就可以使用的情况(url不在数据库中，则不需要控制)
		Collection<String> allMyResourceUrls = (Collection<String>) ActionContext.getContext().getApplication().get("allMyResourceUrls");
		if(!allMyResourceUrls.contains(url)){//如果url不在数据库中
			System.out.println("url不在数据库，不需要控制");
			return true;
		}else{
			// 登录用户要判断是否含有这个权限
			for (MyResource mr : role.getMyResources()) {
				if(url.equals(mr.getUrl())){//需要控制的url
					return true;
				}
			}
			return false;
		}
	}
	
	/**
	 * 判断本用户是否是超级管理员
	 * 
	 * @return
	 */
	public boolean isAdmin() {
		return "admin".equals(loginName);
	}
	
}
