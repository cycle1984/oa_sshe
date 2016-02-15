package cycle.oa_sshe.domain;

import java.io.Serializable;
import java.util.Date;

public class News implements Serializable {

	private static final long serialVersionUID = 1214803020783755650L;

	private Integer id;
	private String title;//标题
	private String content;//内容
	private Date createTime;//创建时间
	private Date editTime;//最后修改时间
	private String userName;//作者
	
	private Unit unit;//发布单位

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
}
