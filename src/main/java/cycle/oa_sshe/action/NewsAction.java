package cycle.oa_sshe.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

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
	
	private File upload;  
    private String uploadContentType;  
    private String uploadFileName;
	
    
    //跳转到已发布信息页面
    public String myGridJsp(){
    	return "myList";
    }
	/**
	 * 分页自己查询信息资讯
	 * Grid
	 */
	public void myGrid(){
		Grid grid = new Grid();
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		
		User user = (User) session.getAttribute("userSession");
		if(user!=null){
			if(!user.isAdmin()){//不是管理员，只查询本单位
				hqlFilter.addFilter("QUERY_t#unit.id_I_EQ", String.valueOf(user.getUnit().getId()));
			}
		}
		
		grid.setTotal(newsService.countByFilter(hqlFilter));//总记录数
		grid.setRows(newsService.findByFilter(hqlFilter,page,rows));//获得当前页显示的数据
		writeJson(grid);
	}
	
	
	/**
	 * 分页查询信息资讯
	 * Grid
	 */
	public void grid(){
		Grid grid = new Grid();
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		
		grid.setTotal(newsService.countByFilter(hqlFilter));//总记录数
		grid.setRows(newsService.findByFilter(hqlFilter,page,rows));//获得当前页显示的数据
		writeJson(grid);
	}
	
	public void newsJSON10(){//取出10条信息
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		hqlFilter.addSort("createTime");
		hqlFilter.addOrder("desc");
		List<News> newsList = newsService.findByFilter(hqlFilter, 0, 10);
		writeJson(newsList);
	}
	
	public void uploadImg() throws IOException{
		
		
		  	response = ServletActionContext.getResponse();  
	        response.setCharacterEncoding("UTF-8");  
	        PrintWriter out = response.getWriter();  
	  
	        // CKEditor提交的很重要的一个参数  
	        String callback = ServletActionContext.getRequest().getParameter("CKEditorFuncNum");   
	          
	        String expandedName = "";  //文件扩展名  
	        if (uploadContentType.equals("image/pjpeg") || uploadContentType.equals("image/jpeg")) {  
	            //IE6上传jpg图片的headimageContentType是image/pjpeg，而IE9以及火狐上传的jpg图片是image/jpeg  
	            expandedName = ".jpg";  
	        }else if(uploadContentType.equals("image/png") || uploadContentType.equals("image/x-png")){  
	            //IE6上传的png图片的headimageContentType是"image/x-png"  
	            expandedName = ".png";  
	        }else if(uploadContentType.equals("image/gif")){  
	            expandedName = ".gif";  
	        }else if(uploadContentType.equals("image/bmp")){  
	            expandedName = ".bmp";  
	        }else{  
	            out.println("<script type=\"text/javascript\">");    
	            out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",''," + "'文件格式不正确（必须为.jpg/.gif/.bmp/.png文件）');");   
	            out.println("</script>");  
	        }  
	          
	        if(upload.length() > 6000*1024){  
	            out.println("<script type=\"text/javascript\">");    
	            out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",''," + "'文件大小不得大于6000k');");   
	            out.println("</script>");  
	        }  
	          
	          
	        String uploadPath = ServletActionContext.getServletContext()     
	                .getRealPath("/img/postImg"); 
	        System.out.println(uploadPath);
	        String fileName = java.util.UUID.randomUUID().toString();  //采用时间+UUID的方式随即命名  
	        fileName += expandedName;  
			File f = new File(uploadPath);
//		// 如果指定的路径没有就创建
		if (!f.exists()) {
			f.mkdirs();
		}
		try {
			FileUtils.copyFile(upload, new File(f,fileName));//文件拷贝
			 
		} catch (IOException e) {
			e.printStackTrace();
		}  
	          
	        // 返回“图像”选项卡并显示图片  
	        out.println("<script type=\"text/javascript\">");    
	        out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + "img/postImg/" + fileName + "','')");    
	        out.println("</script>");  
		
//		PrintWriter out = null;
//		try {
//			out = ServletActionContext.getResponse().getWriter();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println("aaa="+filedata);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//		String fileName = sdf.format(new Date())+"-" +filedataFileName;//文件名前加上精确到毫秒的时间，防止文件名重复
//		String path = request.getSession().getServletContext().getRealPath("/")+"attached/";
//		//文件保存目录URL
//		String saveUrl  = request.getContextPath() + "/attached/";
//		System.out.println(fileName);
//		System.out.println(saveUrl);
//		File f = new File(path);
////		// 如果指定的路径没有就创建
//		if (!f.exists()) {
//			f.mkdirs();
//		}
//		try {
//			FileUtils.copyFile(filedata, new File(f,fileName));//文件拷贝
//			 
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		JSONObject obj = new JSONObject();  
//
//		obj.put("err", 0);  
//
//		obj.put("msg", saveUrl + fileName);  
//
//		out.println(obj.toJSONString()); 
	}
	
	public void save(){
		Json json = new Json();
		User user = (User) session.getAttribute("userSession");
		if(model!=null){
			News news = new News();
			BeanUtils.copyProperties(model, news);
			news.setCreateTime(new Date());
			if(user!=null){
				news.setUserName(user.getName());//设置作者
				if(user.getUnit()!=null){
					news.setUnit(user.getUnit());
				}
			}
			try {
				newsService.save(news);
				json.setSuccess(true);
				json.setMsg("信息发布成功");
				json.setObj(news);
			} catch (Exception e) {
				json.setMsg("新建失败");
				e.printStackTrace();
			}
		}
		writeJson(json);
	}
	
	public void edit(){
		Json json = new Json();
		User u = (User) session.getAttribute("userSession");
		if(model.getId()!=null){
			News n = newsService.getById(model.getId());
			BeanUtils.copyProperties(model, n,new String[] { "createdatetime","userName","unit" });
			n.setCreateTime(new Date());
			if(u!=null&&!"admin".equals(u.getLoginName())){
				n.setUserName(u.getName());//设置作者
				if(u.getUnit()!=null){
					u.setUnit(u.getUnit());
				}
			}
			try {
				newsService.save(n);
				json.setSuccess(true);
				json.setMsg("信息修改成功");
				json.setObj(n);
			} catch (Exception e) {
				json.setMsg("修改失败");
				e.printStackTrace();
			}
		}
		writeJson(json);
	}
	
	public String newsDetails(){
		News n = newsService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(n);
		return "newsDetails";
	}
	
	/**
	 * 删除一批对象
	 */
	public void delete() {
		Json json = new Json();
	
		String hql = "delete News n where n.id in (";
		String[] nids = ids.split(",");
		for (int i = 0; i < nids.length; i++) {
			if (i > 0) {
				hql += ",";
			}
			hql += "'" + nids[i] + "'";
		}
		hql += ")";
		try {
			int num = newsService.executeHql(hql);
			json.setSuccess(true);
			json.setMsg("成功删除【" + num + "】条数据！");
			// json.setObj(o);
		} catch (Exception e) {
			e.printStackTrace();
			json.setMsg("删除失败，也许权限组还存在用户");
		}
		writeJson(json);
	}

	public NewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}


	
}
