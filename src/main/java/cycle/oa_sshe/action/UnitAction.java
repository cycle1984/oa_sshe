package cycle.oa_sshe.action;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cycle.oa_sshe.domain.MyGroup;
import cycle.oa_sshe.domain.Unit;
import cycle.oa_sshe.base.BaseAction;
import cycle.oa_sshe.domain.easyui.Grid;
import cycle.oa_sshe.domain.easyui.Json;
import cycle.oa_sshe.domain.easyui.Tree;
import cycle.oa_sshe.utils.HqlFilter;

/**
 * 单位
 * @author jyj
 *
 */
@Controller("unitAction")
@Scope("prototype")
public class UnitAction extends BaseAction<Unit> {

	private static final long serialVersionUID = -8687505624472476816L;

	private Integer myGroupId;
	/**
	 * 新增
	 */
	public void save(){
		Json json = new Json();
		if(model!=null){
			Unit unit = new Unit();
			BeanUtils.copyProperties(model, unit);
			unit.setCreatedatetime(new Date());
			if(myGroupId!=null){
				MyGroup group = myGroupService.getById(myGroupId);
				unit.setMyGroup(group);
			}
			try {
				unitService.save(unit);
				json.setSuccess(true);
				json.setMsg("新建单位【"+unit.getName()+"】成功");
				json.setObj(unit);
			} catch (Exception e) {
				json.setMsg("新建失败");
				e.printStackTrace();
			}
		}
		writeJson(json);
	}
	
	/**
	 * 编辑
	 */
	public void edit(){
		
		Json json = new Json();
		if(model.getId()!=null){
			Unit u = unitService.getById(model.getId());
			BeanUtils.copyProperties(model, u,new String[] { "createdatetime" });//第三个字段为不赋值的数据
			u.setUpdatedatetime(new Date());
			
			if(myGroupId!=null){
				MyGroup group = myGroupService.getById(myGroupId);
				u.setMyGroup(group);
			}
			try {
				unitService.update(u);
				json.setSuccess(true);
				json.setMsg("单位【"+u.getName()+"】修改成功");
				json.setObj(u);
			} catch (Exception e) {
				json.setMsg("修改失败");
				e.printStackTrace();
			}
		}
		writeJson(json);
	}
	
	/**
	 * 删除一批对象
	 */
	public void delete() {
		Json json = new Json();
	
		String hql = "delete Unit u where u.id in (";
		String[] nids = ids.split(",");
		for (int i = 0; i < nids.length; i++) {
			if (i > 0) {
				hql += ",";
			}
			hql += "'" + nids[i] + "'";
		}
		hql += ")";
		try {
			int num = unitService.executeHql(hql);
			json.setSuccess(true);
			json.setMsg("成功删除【" + num + "】条数据！");
			// json.setObj(o);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("删除失败,可能单位还存在用户");
		}
		writeJson(json);
	}
	
	/**
	 * grid表格查询所有单位
	 */
	public void listGrid(){
		Grid grid = new Grid();
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		grid.setTotal(unitService.countByFilter(hqlFilter));//总记录数
		grid.setRows(unitService.findByFilter(hqlFilter,page,rows));//获得当前页显示的数据
		writeJson(grid);
	}
	
	/**
	 * 查询所有单位
	 */
	public void list(){
		Grid grid = new Grid();
		grid.setTotal(unitService.count());//总记录数
		grid.setRows(unitService.find());//获得当前页显示的数据
		writeJson(grid);
	}
	
	/**
	 * 查询单位名称，按首字母排序
	 */
	public void listByInitial(){
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		hqlFilter.addFilter("QUERY_t#state_I_EQ", "0");
		List<Unit> list = unitService.findByFilter(hqlFilter);
		Comparator comparator = Collator.getInstance(java.util.Locale.CHINA);
		String[] unitNames =new String[list.size()];
		int i =0;
		for (Unit unit : list) {
			unitNames[i]=unit.getName();
			i++;
		}
		Arrays.sort(unitNames, comparator);
		List<Unit> l = new ArrayList<Unit>();
		for (String string : unitNames) {
			
			Unit u = new Unit();
			u.setName(string);
			l.add(u);
		}
		writeJson(l);
	}
	
	/**
	 * 根据机构ID获得单位
	 */
	public void getUnitsByMyGroupId(){
		if(myGroupId!=null){
			String hql = "from Unit u where u.myGroup.id="+myGroupId;
			List<Unit> list = unitService.find(hql);
			System.out.println(list.isEmpty());
			writeJson(list);
		}
	}
	
	/**
	 *  收文单位菜单
	 */
	public void getUnitTree(){
		
		List<Tree> tree = new ArrayList<Tree>();//用于放树的顶点
		List<Tree> tree2 = new ArrayList<Tree>();//二级节点集合
		Tree node = new Tree();//顶点
		node.setText("全选");//顶点名称
		node.setId(999999);//设置顶点的id
		
		String hql = "from MyGroup";
		List<MyGroup> myGroups = myGroupService.find(hql);//获得所有的机构（类别）
		for (MyGroup myGroup : myGroups) {//遍历二级节点（所有机构）
			Tree node2 = new Tree();//二级节点
			node2.setText(myGroup.getName());
			node2.setId(myGroup.getId());
			node2.setState("closed");//二级菜单默认不展开
			
			List<Tree> tree3 =  new ArrayList<Tree>();//三级节点集合
			Set<Unit> cyUnits = myGroup.getUnits();//获得当前机构下的所有单位
			for (Unit unit : cyUnits) {
				if(unit.getState()==0){//state=0的才允许接收公文
					Tree node3 = new Tree();//三级节点
					node3.setText(unit.getName());
					node3.setId(unit.getId());
					tree3.add(node3);//添加到三级节点树
					
					node2.setChildren(tree3);//将节点设置为当前机构的三级节点
				}
				
			}
			if(node2.getChildren()!=null){//如果当前机构存在单位（既当前二级节点的子节点不为空）
				tree2.add(node2);//将此节点设置为树的二级节点
			}
		}
		node.setChildren(tree2);//将二级节点集合设为顶点的二级节点
		tree.add(node);
		writeJson(tree);
		
	}
	
	/**
	 *  根据发文单位查询菜单
	 */
	public void getUnitTree2(){
		
		List<Tree> tree = new ArrayList<Tree>();//用于放树的顶点
		List<Tree> tree2 = new ArrayList<Tree>();//二级节点集合
//		Tree node = new Tree();//顶点
//		node.setText("全选");//顶点名称
//		node.setId(999999);//设置顶点的id
		
		String hql = "from MyGroup";
		List<MyGroup> myGroups = myGroupService.find(hql);//获得所有的机构（类别）
		for (MyGroup myGroup : myGroups) {//遍历二级节点（所有机构）
			Tree node2 = new Tree();//二级节点
			node2.setText(myGroup.getName());
			node2.setId(myGroup.getId());
			node2.setState("closed");//二级菜单默认不展开
			
			List<Tree> tree3 =  new ArrayList<Tree>();//三级节点集合
			Set<Unit> cyUnits = myGroup.getUnits();//获得当前机构下的所有单位
			for (Unit unit : cyUnits) {
				if(unit.getState()==0){//state=0的才允许接收公文
					Tree node3 = new Tree();//三级节点
					node3.setText(unit.getName());
					node3.setId(unit.getId());
					tree3.add(node3);//添加到三级节点树
					
					node2.setChildren(tree3);//将节点设置为当前机构的三级节点
				}
				
			}
			if(node2.getChildren()!=null){//如果当前机构存在单位（既当前二级节点的子节点不为空）
				tree2.add(node2);//将此节点设置为树的二级节点
			}
		}
//		node.setChildren(tree2);//将二级节点集合设为顶点的二级节点
//		tree.add(node);
		writeJson(tree2);
		
	}
	
	public String searchByUnit(){
		return "searchByUnit";
	}

	public Integer getMyGroupId() {
		return myGroupId;
	}

	public void setMyGroupId(Integer myGroupId) {
		this.myGroupId = myGroupId;
	}

	
	
}
