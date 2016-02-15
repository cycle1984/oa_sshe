package cycle.oa_sshe.domain;

import java.io.Serializable;

public class SysBase implements Serializable {

	private Integer id ;
	private String defPath;//公文存储路径
	private Integer refleshTime;//刷新时间,单位分钟
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDefPath() {
		return defPath;
	}
	public void setDefPath(String defPath) {
		this.defPath = defPath;
	}
	public Integer getRefleshTime() {
		return refleshTime;
	}
	public void setRefleshTime(Integer refleshTime) {
		this.refleshTime = refleshTime;
	}
	
	public SysBase() {
	}
	
	public SysBase(String defPath, Integer refleshTime) {
		this.defPath = defPath;
		this.refleshTime = refleshTime;
	}
	
}
