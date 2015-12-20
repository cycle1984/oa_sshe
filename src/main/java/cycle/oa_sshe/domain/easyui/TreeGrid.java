package cycle.oa_sshe.domain.easyui;

import java.io.Serializable;
import java.util.List;

public class TreeGrid implements Serializable {

	private Long id;//主键  标识树节点
	private String name;//树节点字段
	private String tel;//办公室电话
	private String mobile;//移动电话
	private String dep;//所属部门
	private String state = "open";//节点状态
	
	private List<TreeGrid> children;//子节点
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getDep() {
		return dep;
	}
	public void setDep(String dep) {
		this.dep = dep;
	}
	public List<TreeGrid> getChildren() {
		return children;
	}
	public void setChildren(List<TreeGrid> children) {
		this.children = children;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
