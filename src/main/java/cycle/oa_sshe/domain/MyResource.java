package cycle.oa_sshe.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 权限列表
 * @author jyj
 *
 */
public class MyResource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5531636887923341768L;

	private Integer id;
	private String name;//名称
	private String url;//链接
	private String description;//描述 
	private String iconCls;//图标
	private Integer seq;//顺序
	private String target;//目标
	private Integer type = 0;//0表示只在左侧菜单显示，1表示功能
	
	private MyResource myResource;//上级权限
	private Set<MyResource> myResources = new HashSet<MyResource>();//拥有的下级权限
	private Set<Role> roles = new HashSet<Role>();//拥有此权限的角色
	
	public MyResource() {
		// TODO Auto-generated constructor stub
	}
	
	
	public MyResource(String name, String url, MyResource myResource,Integer type) {
		this.name = name;
		this.url = url;
		this.myResource = myResource;
		this.type = type;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}


	public MyResource getMyResource() {
		return myResource;
	}


	public void setMyResource(MyResource myResource) {
		this.myResource = myResource;
	}


	public Set<MyResource> getMyResources() {
		return myResources;
	}


	public void setMyResources(Set<MyResource> myResources) {
		this.myResources = myResources;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
}
