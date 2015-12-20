package cycle.oa_sshe.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * 公文实体类
 * @author jyj
 *
 */
public class Document implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5181850655256922166L;
	
	private Integer id ;
	private String documentTitle;
	private String level;//公文紧急等级
	private Date createdatetime;//创建日期
	private String publishUserName;//发布人姓名
	private String description;//描述、备注
	
	private Unit publishUnit;//发布人的单位
	private Set<SignInfo> signInfos = new HashSet<SignInfo>(); //签收信息列表
	private Set<MyFile> myFiles = new HashSet<MyFile>();//附件信息(下载所需)
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDocumentTitle() {
		return documentTitle;
	}
	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public Date getCreatedatetime() {
		return createdatetime;
	}
	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}
	public String getPublishUserName() {
		return publishUserName;
	}
	public void setPublishUserName(String publishUserName) {
		this.publishUserName = publishUserName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Unit getPublishUnit() {
		return publishUnit;
	}
	public void setPublishUnit(Unit publishUnit) {
		this.publishUnit = publishUnit;
	}
	public Set<SignInfo> getSignInfos() {
		return signInfos;
	}
	public void setSignInfos(Set<SignInfo> signInfos) {
		this.signInfos = signInfos;
	}
	public Set<MyFile> getMyFiles() {
		return myFiles;
	}
	public void setMyFiles(Set<MyFile> myFiles) {
		this.myFiles = myFiles;
	}
	
}
