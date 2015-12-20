package cycle.oa_sshe.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 角色
 * @author jyj
 *
 */
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8469210514164912928L;

	private Integer id;//主键
	private String name;//角色名称
	private String description;//描述
	
	private Set<User> users = new HashSet<User>();//含有的用户
	private Set<MyResource> myResources = new HashSet<MyResource>();//所含有的权限
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public Set<MyResource> getMyResources() {
		return myResources;
	}
	public void setMyResources(Set<MyResource> myResources) {
		this.myResources = myResources;
	}
	
	
}
