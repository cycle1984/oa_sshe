package cycle.oa_sshe.domain;

import java.util.Date;

/**
 * 登录日志
 * @author Administrator
 *
 */
public class LoginLog {
	private Integer id;
	private Date logDate;//登陆时间
	private String userName;//用户名
	private String unitName;//所属单位
	private String ip;//登陆IP
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getLogDate() {
		return logDate;
	}
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
