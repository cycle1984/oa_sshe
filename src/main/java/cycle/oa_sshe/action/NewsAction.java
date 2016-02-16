package cycle.oa_sshe.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cycle.oa_sshe.base.BaseAction;
import cycle.oa_sshe.domain.LoginLog;
import cycle.oa_sshe.domain.easyui.Grid;
import cycle.oa_sshe.service.NewsService;
import cycle.oa_sshe.utils.HqlFilter;

@Controller("newsAction")
@Scope("prototype")
public class NewsAction extends BaseAction<LoginLog> {

	private static final long serialVersionUID = 6731984335686062170L;
	
	@Resource(name="newsService")
	private NewsService newsService;
	
	/**
	 * 分页查询所有信息资讯
	 * Grid
	 */
	public void listGrid(){
		Grid grid = new Grid();
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		grid.setTotal(newsService.countByFilter(hqlFilter));//总记录数
		grid.setRows(newsService.findByFilter(hqlFilter,page,rows));//获得当前页显示的数据
		writeJson(grid);
	}

}
