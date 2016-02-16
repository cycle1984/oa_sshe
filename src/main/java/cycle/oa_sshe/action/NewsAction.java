package cycle.oa_sshe.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cycle.oa_sshe.base.BaseAction;
import cycle.oa_sshe.domain.News;
import cycle.oa_sshe.domain.User;
import cycle.oa_sshe.domain.easyui.Grid;
import cycle.oa_sshe.domain.easyui.Json;
import cycle.oa_sshe.service.NewsService;
import cycle.oa_sshe.utils.HqlFilter;

@Controller("newsAction")
@Scope("prototype")
public class NewsAction extends BaseAction<News> {

	private static final long serialVersionUID = 6731984335686062170L;
	
	@Resource(name="newsService")
	private NewsService newsService;
	
	private File filedata;   //上传时从页面传过来的文件
	private String filedataFileName;    //文件类型  
	private String filedataContentType;    //文件名
	
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
	
	public void uploadImg(){
		PrintWriter out = null;
		try {
			out = ServletActionContext.getResponse().getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("aaa="+filedata);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fileName = sdf.format(new Date())+"-" +filedataFileName;//文件名前加上精确到毫秒的时间，防止文件名重复
		String path = request.getSession().getServletContext().getRealPath("/")+"attached/";
		//文件保存目录URL
		String saveUrl  = request.getContextPath() + "/attached/";
		System.out.println(fileName);
		System.out.println(saveUrl);
		File f = new File(path);
//		// 如果指定的路径没有就创建
		if (!f.exists()) {
			f.mkdirs();
		}
		try {
			FileUtils.copyFile(filedata, new File(f,fileName));//文件拷贝
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject();  

		obj.put("err", 0);  

		obj.put("msg", saveUrl + fileName);  

		out.println(obj.toJSONString()); 
	}
	
	public void save(){
		Json json = new Json();
		User u = (User) session.getAttribute("userSession");
		if(model!=null){
			News n = new News();
			BeanUtils.copyProperties(model, n);
			n.setCreateTime(new Date());
			if(u!=null){
				n.setUserName(u.getName());//设置作者
				if(u.getUnit()!=null){
					u.setUnit(u.getUnit());
				}
			}
			try {
				newsService.save(n);
				json.setSuccess(true);
				json.setMsg("信息发布成功");
				json.setObj(n);
			} catch (Exception e) {
				json.setMsg("新建失败");
				e.printStackTrace();
			}
		}
		writeJson(json);
	}

	public NewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	public File getFiledata() {
		return filedata;
	}

	public void setFiledata(File filedata) {
		this.filedata = filedata;
	}

	public String getFiledataFileName() {
		return filedataFileName;
	}

	public void setFiledataFileName(String filedataFileName) {
		this.filedataFileName = filedataFileName;
	}

	public String getFiledataContentType() {
		return filedataContentType;
	}

	public void setFiledataContentType(String filedataContentType) {
		this.filedataContentType = filedataContentType;
	}

	
}
