package cycle.oa_sshe.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cycle.oa_sshe.base.BaseAction;
import cycle.oa_sshe.domain.LoginLog;
import cycle.oa_sshe.domain.easyui.Grid;
import cycle.oa_sshe.domain.easyui.Json;
import cycle.oa_sshe.service.LoginLogService;
import cycle.oa_sshe.utils.HqlFilter;

@Controller("loginLogAction")
@Scope("prototype")
public class LoginLogAction extends BaseAction<LoginLog> {
	private static final long serialVersionUID = -312788416959024835L;
	
	@Resource(name="loginLogService")
	private LoginLogService loginLogService;

	
	/**
	 * 日志数据
	 */
	public void grid(){
		Grid grid = new Grid();
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		grid.setTotal(loginLogService.countByFilter(hqlFilter));
		grid.setRows(loginLogService.findByFilter(hqlFilter, page, rows));
		writeJson(grid);
	}
	
	/**
	 *批量删除数据 
	 *
	 */
	public void delete(){
		Json json = new Json();
		String hql = "delete LoginLog l where l.id in (";
		String[] nids = ids.split(",");
		for (int i = 0; i < nids.length; i++) {
			if (i > 0) {
				hql += ",";
			}
			hql += "'" + nids[i] + "'";
		}
		hql += ")";
		try {
			int num = loginLogService.executeHql(hql);
			json.setSuccess(true);
			json.setMsg("成功删除【" + num + "】条数据！");
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("删除失败");
		}
		writeJson(json);
	}
}
