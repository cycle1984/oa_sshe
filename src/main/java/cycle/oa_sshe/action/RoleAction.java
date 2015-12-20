package cycle.oa_sshe.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cycle.oa_sshe.domain.MyResource;
import cycle.oa_sshe.domain.Role;
import cycle.oa_sshe.base.BaseAction;
import cycle.oa_sshe.domain.easyui.Grid;
import cycle.oa_sshe.domain.easyui.Json;
import cycle.oa_sshe.domain.easyui.Tree;
import cycle.oa_sshe.utils.HqlFilter;
import cycle.oa_sshe.utils.MyUtils;

/**
 * 角色
 * @author jyj
 *
 */

@Controller("roleAction")
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {
	private static final long serialVersionUID = -857942141472519658L;
	
	private Integer roleId;
	/**
	 * 新增
	 */
	public void save(){
		Json json = new Json();
		if(model!=null){
			Role role = new Role();
			BeanUtils.copyProperties(model, role);
			try {
				roleService.save(role);
				json.setSuccess(true);
				json.setMsg("新建角色【"+role.getName()+"】成功");
				json.setObj(role);
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
		Role role = roleService.getById(model.getId());
		role.setName(model.getName());
		role.setDescription(model.getDescription());
		try {
			roleService.update(role);
			json.setSuccess(true);
			json.setMsg("角色【" + role.getName() + "】修改成功");
			json.setObj(role);
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
	
		String hql = "delete Role r where r.id in (";
		String[] nids = ids.split(",");
		for (int i = 0; i < nids.length; i++) {
			if (i > 0) {
				hql += ",";
			}
			hql += "'" + nids[i] + "'";
		}
		hql += ")";
		try {
			int num = roleService.executeHql(hql);
			json.setSuccess(true);
			json.setMsg("成功删除【" + num + "】条数据！");
			// json.setObj(o);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("删除失败，也许权限组还存在用户");
		}
		writeJson(json);
	}
	
	/**
	 * 分页查询角色
	 * 为Grid准备数据
	 */
	public void listGrid(){
		Grid grid = new Grid();
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		grid.setTotal(roleService.countByFilter(hqlFilter));//总记录数
		grid.setRows(roleService.findByFilter(hqlFilter,page,rows));//获得当前页显示的数据
		writeJson(grid);
	}
	
	/**
	 * 查询出所有角色
	 */
	public void findAll(){
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		writeJson(roleService.findByFilter(hqlFilter));
	}

	/**
	 * 
	 * 获得所有的资源列表(转换成树，显示到前端)
	 */
	public void setMyResourceUI() throws Exception{
		System.out.println("ddd");
		List<MyResource> myResources = myResourceService.find("from MyResource");//获得所有的权限（资源）
		List<Tree> tree = new ArrayList<Tree>();//创建一棵树
		for (MyResource resource : myResources) {
			Tree node = new Tree();//节点
			BeanUtils.copyProperties(resource, node);//将权限（资源）赋值到树节点中
			node.setText(resource.getName());//设置树节点显示名称
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("url", resource.getUrl());//设置url
			attributes.put("target", resource.getTarget());
			node.setAttributes(attributes);
			MyResource cy = resource.getMyResource();//获取当前权限（资源）的上级权限（资源）（父节点）
			if (cy != null) {
				node.setPid(cy.getId());//设置当前节点的上节点
			}
//			if (cyRoleID != null) {
//				CyRole role = cyRoleService.getById(cyRoleID);
//				Set<CyResource> privileges = role.getCyResources();
//				// 判断role有的权限，如有，则checked=true
//				if (privileges != null) {
//					for (CyResource roleResource : privileges) {
//						if (roleResource.getId() == resource.getId()) {
//							node.setChecked(true);
//						}
//					}
//				}
//			}
			tree.add(node);
		}
		writeJson(tree);
	}
	
	/**
	 * 获得角色所拥有的资源（权限）,用于勾选当前角色（权限组）所拥有的
	 */
	public void getRoleMyResource(){
		Set<MyResource> resources = new HashSet<MyResource>();
		if (roleId != null) {
			Role role = roleService.getById(roleId);
			resources = role.getMyResources();//获得角色所拥有的资源（权限）
		}
		writeJson(resources);
	}
	
	/**
	 * 设置角色的权限（资源）
	 * @throws Exception
	 */
	public void setMyResource() {
		if(roleId!=null){
			Json json = new Json();
			//从数据库中获取原对象
			Role role = roleService.getById(roleId);
			List<MyResource> mrs = myResourceService.getByIds(MyUtils.string2Integer(ids));//根据勾选的权限（资源）id获取所有的权限（资源）
			role.setMyResources(new HashSet<MyResource>(mrs));//设置权限组的权限（资源）
			String cyResouceString = "";
			for (MyResource cyResource : mrs) {
				if(cyResource.getMyResources().isEmpty()){//如果是叶子权限
					cyResouceString=cyResouceString+cyResource.getName()+",";//把所有权限的名称转换成字符串，拥有显示权限组（角色）的描述信息
					
				}
			}
			role.setDescription(cyResouceString);
			
			
			try {
				roleService.update(role);//更新
				json.setSuccess(true);
				json.setMsg("权限组【"+role.getName()+"】设置成功");
			} catch (Exception e) {
				json.setMsg("权限组修改失败");
				e.printStackTrace();
			}
			writeJson(json);
		}
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	
	
}
