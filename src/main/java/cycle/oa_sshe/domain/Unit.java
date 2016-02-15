package cycle.oa_sshe.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 单位、部门
 * @author jyj
 *
 */
public class Unit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5979357220444776982L;

	private Integer id;//主键
	private Date createdatetime;//创建时间
	private Date updatedatetime;//最后更新时间
	private String name;//单位名称
	private String fullName;//单位全称
	private String tel;//办公室电话
	private String description;//描述
	private Integer state=0;//状态，0表示正常状态，1表示禁用状态
	
	private MyGroup myGroup;//所属类别
	private Set<User> users = new HashSet<User>();
	private Set<Document> documents = new HashSet<Document>();//单位发布的文件
	private Set<SignInfo> signInfos = new HashSet<SignInfo>();//签收信息列表
	private Set<News> newsList = new HashSet<News>();
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public MyGroup getMyGroup() {
		return myGroup;
	}
	public void setMyGroup(MyGroup myGroup) {
		this.myGroup = myGroup;
	}
	public Set<Document> getDocuments() {
		return documents;
	}
	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}
	public Set<SignInfo> getSignInfos() {
		return signInfos;
	}
	public void setSignInfos(Set<SignInfo> signInfos) {
		this.signInfos = signInfos;
	}
	public Set<News> getNewsList() {
		return newsList;
	}
	public void setNewsList(Set<News> newsList) {
		this.newsList = newsList;
	}
	
}
