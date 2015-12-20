package cycle.oa_sshe.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 签收信息列表
 * @author jyj
 *
 */
public class SignInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -885687709088660376L;

	private Integer id ;
	private String publishUserName;//发布人姓名
	private String publishUnitName;//发布人的单位名称
	private Boolean state = false;//签收状态,true表示已签收
	private Date signDate;//签收时间
	private String ip = null;//签收IP地址
	private String signUserName;//签收人姓名
	
	private Document document;//对应的公文
	private Unit signUnit;//公文接收单位
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPublishUserName() {
		return publishUserName;
	}
	public void setPublishUserName(String publishUserName) {
		this.publishUserName = publishUserName;
	}
	public String getPublishUnitName() {
		return publishUnitName;
	}
	public void setPublishUnitName(String publishUnitName) {
		this.publishUnitName = publishUnitName;
	}
	public Boolean getState() {
		return state;
	}
	public void setState(Boolean state) {
		this.state = state;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getSignUserName() {
		return signUserName;
	}
	public void setSignUserName(String signUserName) {
		this.signUserName = signUserName;
	}
	public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
	public Unit getSignUnit() {
		return signUnit;
	}
	public void setSignUnit(Unit signUnit) {
		this.signUnit = signUnit;
	}
	
	
}
