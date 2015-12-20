package cycle.oa_sshe.domain.easyui;

import java.io.Serializable;
import java.util.List;

public class Tree implements Serializable {

	private Integer id;
	private String text;
	private String state = "open";// open,closed
	private boolean checked = false;
	private Object attributes;
	private List<Tree> children;
	private String iconCls;
	private Integer pid;
	public Integer getId() {
		return id;
	}
	public String getText() {
		return text;
	}
	public String getState() {
		return state;
	}
	public boolean isChecked() {
		return checked;
	}
	public Object getAttributes() {
		return attributes;
	}
	public List<Tree> getChildren() {
		return children;
	}
	public String getIconCls() {
		return iconCls;
	}
	public Integer getPid() {
		return pid;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}
	public void setChildren(List<Tree> children) {
		this.children = children;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	
	
}
