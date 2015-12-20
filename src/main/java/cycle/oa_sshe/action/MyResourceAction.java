package cycle.oa_sshe.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cycle.oa_sshe.domain.MyResource;
import cycle.oa_sshe.domain.User;
import cycle.oa_sshe.base.BaseAction;
import cycle.oa_sshe.domain.easyui.Tree;


/**
 * 用于获得系统资源
 * @author jyj
 *
 */
@Controller("myResourceAction")
@Scope("prototype")
public class MyResourceAction extends BaseAction<MyResource> {

	private static final long serialVersionUID = -6910463087914232678L;
	
	/**
	 * 用于获得系统主界面左侧菜单
	 */
	public void getAllMenu(){
		
		System.out.println("执行到获取菜单");
		//取出当前session中的user对象，用户拥有的对象和所有菜单对比，相等则添加到用户应显示菜单
		HttpSession session = ServletActionContext.getRequest().getSession();
		User u = (User) session.getAttribute("userSession");
		
		//从session域中获得系统加载好的所有菜单
		List<MyResource> allMenu =  (List<MyResource>) ActionContext.getContext().getApplication().get("allLeftMenu");
		
		List<MyResource> myResourceList = new ArrayList<MyResource>();
		if("admin".equals(u.getLoginName())){//超级管理员登录
			for (MyResource myResource : allMenu) {//遍历加载好的菜单
				if(!"历史收文".equals(myResource.getName())){//因为超级管理员没有收文，不需要加载历史收文菜单,其他功能均有
					myResourceList.add(myResource);//获得菜单项
				}
			}
		}else{//非超级管理员,获取左侧菜单
			List<MyResource> myResources = new ArrayList<MyResource>(u.getRole().getMyResources());
			for (MyResource allM : allMenu) {//遍历加载好的菜单
				for (MyResource myResource : myResources) {
					if(allM.getId().equals(myResource.getId())){
						myResourceList.add(myResource);//获得菜单项
					}
				}
				
			}
		}
		
		List<Tree> tree = new ArrayList<Tree>();//准备菜单树
		for (MyResource myResource : myResourceList) {//遍历菜单项,将菜单项添加到树中
			Tree node = new Tree();//树节点
			BeanUtils.copyProperties(myResource, node);//将菜单项复制到树节点
			node.setText(myResource.getName());//设置树节点的名称
			Map<String,String> attributes = new HashMap<String,String>();
			attributes.put("url", myResource.getUrl());//菜单项对应的URL
			attributes.put("target", myResource.getTarget());
			node.setAttributes(attributes);
			MyResource cy = myResource.getMyResource();
			if(cy!=null){//存在上级节点的情况
				node.setPid(cy.getId());//设置上级节点ID
			}
			tree.add(node);
			
		}
		writeJson(tree);//输出json
	}
	
}
