package cycle.oa_sshe.action;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cycle.oa_sshe.base.BaseAction;
import cycle.oa_sshe.domain.Document;
import cycle.oa_sshe.domain.MyFile;
import cycle.oa_sshe.domain.SignInfo;
import cycle.oa_sshe.domain.User;
import cycle.oa_sshe.domain.easyui.Grid;
import cycle.oa_sshe.utils.HqlFilter;


@Controller("signInfoAction")
@Scope("prototype")
public class SignInfoAction extends BaseAction<SignInfo> {

	private static final long serialVersionUID = 2252594496288056664L;
	
	private Integer docId;//公文id

	/**
	 * 查看签收情况，为前台准备数据
	 * @return
	 */
	public String toViewInfoJsp(){
		Document ducument = documentService.getById(docId);
		Set<MyFile> myFiles = ducument.getMyFiles();
		for (MyFile myFile : myFiles) {
			String filename = myFile.getFileName();
			int i =filename.indexOf("-");
			filename = filename.substring(i+1, filename.length());
			myFile.setFileName(filename);
		}
		ActionContext.getContext().getValueStack().push(ducument);
		return "toViewInfoJsp";
	}
	
	/**
	 * 根据公文Id，显示该公文的签收情况grid
	 */
	public void signInfoGrid(){
		Grid grid = new Grid();
		System.out.println("dsad");
		if(docId!=null){
			HqlFilter hqlFilter = new HqlFilter(getRequest());
			hqlFilter.addFilter("QUERY_t#document.id_I_EQ", String.valueOf(docId));
			List<SignInfo> li = signInfoService.findByFilter(hqlFilter, page, rows);
			grid.setTotal(signInfoService.countByFilter(hqlFilter));
			grid.setRows(li);
		}
		writeJson(grid);
	}

	/**
	 * 收文列表
	 */
	public void receiveListGrid(){
		Grid grid = new Grid();
		User user = (User) session.getAttribute("userSession");//获得session中的用户
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		if(user.getUnit().getId()!=null){
			Calendar cal = Calendar.getInstance();
	        int year = cal.get(Calendar.YEAR);//获取年份
	        int month=cal.get(Calendar.MONTH);//获取月份，从0算起，也就是一月份是0
	        int day=cal.get(Calendar.DATE);//获取日
			String da = (year-1)+"-"+month+1+"-"+day;//过滤条件，当前年-1
			hqlFilter.addFilter("QUERY_t#document.createdatetime_D_GE", da);//只查询一年内的发文记录
			hqlFilter.addFilter("QUERY_t#signUnit.id_I_EQ", String.valueOf(user.getUnit().getId()));//指定收文单位
			grid.setTotal(signInfoService.countByFilter(hqlFilter));
			grid.setRows(signInfoService.findByFilter(hqlFilter, page, rows));
		}
		writeJson(grid);
	}
	
	/**
	 * 根据ID获得签收信息
	 * @return
	 */
	public String getByID(){
		SignInfo si = signInfoService.getById(model.getId());
		if(si!=null){
			Set<MyFile> files = si.getDocument().getMyFiles();
			for (MyFile myFile : files) {
				String n = myFile.getFileName();
				int i =n.indexOf("-");
				n = n.substring(i+1, n.length());
				myFile.setFileName(n);
			}
			ActionContext.getContext().getValueStack().push(si);
		}
		return "signInfoDetails";
	}
	
	/**
	 * 跳转到历史收文页
	 * @return
	 */
	public String historytAcceptGridJsp(){
		
		return "historytAcceptGridJsp";
	}
	
	/**
	 * 历史收文数据
	 * @return
	 */
	public void historytAcceptGrid(){
		Grid grid = new Grid();
		User user = (User) session.getAttribute("userSession");//获得session中的用户
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		if(user.getUnit().getId()!=null){
			Calendar cal = Calendar.getInstance();
	        int year = cal.get(Calendar.YEAR);//获取年份
	        int month=cal.get(Calendar.MONTH);//获取月份，从0算起，也就是一月份是0
	        int day=cal.get(Calendar.DATE);//获取日
			String da = (year-1)+"-"+month+1+"-"+day;//过滤条件，当前年-1
			hqlFilter.addFilter("QUERY_t#document.createdatetime_D_LT", da);//只查询一年内的发文记录
			hqlFilter.addFilter("QUERY_t#signUnit.id_I_EQ", String.valueOf(user.getUnit().getId()));//指定收文单位
			grid.setTotal(signInfoService.countByFilter(hqlFilter));
			grid.setRows(signInfoService.findByFilter(hqlFilter, page, rows));
		}
		writeJson(grid);
	}
	
	public Integer getDocId() {
		return docId;
	}

	public void setDocId(Integer docId) {
		this.docId = docId;
	}
	
}
