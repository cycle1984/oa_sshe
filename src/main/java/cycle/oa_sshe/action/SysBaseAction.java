package cycle.oa_sshe.action;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cycle.oa_sshe.base.BaseAction;
import cycle.oa_sshe.domain.SysBase;
import cycle.oa_sshe.domain.User;
import cycle.oa_sshe.domain.easyui.Json;

@Controller("sysBaseAction")
@Scope("prototype")
public class SysBaseAction extends BaseAction<SysBase> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7845855723909594536L;

	public String getConfig(){
		List<SysBase> li = sysBaseService.find("from SysBase");
		if(!li.isEmpty()){
			SysBase config = li.get(0);
			ActionContext.getContext().getValueStack().push(config);
		}
		return "config";
	}
	
	public void edit(){
		Json j = new Json();
		User user = (User) session.getAttribute("userSession");
		if(user.isAdmin()){//管理员才允许修改系统配置
			if(model.getDefPath()!=null&&!"".equals(model.getDefPath())&&model.getRefleshTime()!=null&&!"".equals(model.getRefleshTime())){
				SysBase b = sysBaseService.getById(model.getId());
				BeanUtils.copyProperties(model, b);
				try {
					sysBaseService.update(b);
					j.setSuccess(true);
					j.setMsg("配置成功!");
				} catch (Exception e) {
					j.setMsg("配置失败!");
					e.printStackTrace();
				}
			}else{
				j.setMsg("数据出现空值情况!");
			}
		}else{
			j.setMsg("只有管理员才能修改系统配置!");
		}
		
		writeJson(j);
	}
}
