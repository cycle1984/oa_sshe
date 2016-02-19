package cycle.oa_sshe.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cycle.oa_sshe.domain.MyGroup;
import cycle.oa_sshe.domain.Unit;
import cycle.oa_sshe.base.BaseAction;
import cycle.oa_sshe.domain.easyui.Grid;
import cycle.oa_sshe.domain.easyui.Json;
import cycle.oa_sshe.utils.HqlFilter;

/**
 * 机构、类别、群组action
 * @author jyj
 *
 */
@Controller("myGroupAction")
@Scope("prototype")
public class MyGroupAction extends BaseAction<MyGroup> {

	private static final long serialVersionUID = 7584495575182277526L;

	/**
	 * 新增
	 */
	public void save(){
		Json json = new Json();
		if(model!=null){
			MyGroup group = new MyGroup();
			BeanUtils.copyProperties(model, group);
			try {
				myGroupService.save(group);
				json.setSuccess(true);
				json.setMsg("新建机构【"+group.getName()+"】成功");
				json.setObj(group);
			} catch (Exception e) {
				json.setMsg("新建失败");
				e.printStackTrace();
			}
		}
		writeJson(json);
	}
	
	/**
	 * 修改
	 */
	public void edit(){
		Json json = new Json();
		MyGroup group = myGroupService.getById(model.getId());
		group.setName(model.getName());
		group.setDescription(model.getDescription());
		try {
			myGroupService.update(group);
			json.setSuccess(true);
			json.setMsg("机构【" + group.getName() + "】修改成功");
			json.setObj(group);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("操作失败");
		}

		writeJson(json);
	}
	
	/**
	 * 删除一批对象
	 */
	public void delete() {
		Json json = new Json();
	
		String hql = "delete MyGroup g where g.id in (";
		String[] nids = ids.split(",");
		for (int i = 0; i < nids.length; i++) {
			if (i > 0) {
				hql += ",";
			}
			hql += "'" + nids[i] + "'";
		}
		hql += ")";
		try {
			int num = myGroupService.executeHql(hql);
			json.setSuccess(true);
			json.setMsg("成功删除【" + num + "】条数据！");
			// json.setObj(o);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("删除失败,请检查是否还存在所辖单位");
		}
		writeJson(json);
	}
	
	/**
	 * 分页查询机构
	 * 为Grid准备数据
	 */
	public void grid(){
		Grid grid = new Grid();
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		grid.setTotal(myGroupService.countByFilter(hqlFilter));//总记录数
		List<MyGroup> list = myGroupService.findByFilter(hqlFilter, page, rows);
		for (MyGroup myGroup : list) {
				List<Unit>  units = (List<Unit>) unitService.find("from Unit where myGroup.id="+myGroup.getId());
				String tempName = "";
				if(units!=null){
					for (Unit unit : units) {
						tempName+=unit.getName()+",";
					}
					tempName+="共"+units.size()+"个单位。";
					myGroup.setOwnerUnit(tempName);
				}
			
		}
		grid.setRows(list);//获得当前页显示的数据
		writeJson(grid);
	}
	
	/**
	 * 查询出所有机构
	 */
	public void findAll(){
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		writeJson(myGroupService.findByFilter(hqlFilter));
	}
}
