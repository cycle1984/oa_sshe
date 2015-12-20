package cycle.oa_sshe.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 组群、类别
 * @author jyj
 *
 */
public class MyGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5718606067445910278L;

	
	private Integer id;//主键
	private String name;//类别名称
	private String  description;//描述
	
	private Set<Unit> units = new HashSet<Unit>();//所拥有的单位
	
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
	public Set<Unit> getUnits() {
		return units;
	}
	public void setUnits(Set<Unit> units) {
		this.units = units;
	}
	
	
}
