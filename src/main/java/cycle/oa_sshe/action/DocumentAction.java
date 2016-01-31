package cycle.oa_sshe.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cycle.oa_sshe.base.BaseAction;
import cycle.oa_sshe.domain.Document;
import cycle.oa_sshe.domain.MyFile;
import cycle.oa_sshe.domain.SignInfo;
import cycle.oa_sshe.domain.Unit;
import cycle.oa_sshe.domain.User;
import cycle.oa_sshe.domain.easyui.Grid;
import cycle.oa_sshe.domain.easyui.Json;
import cycle.oa_sshe.utils.HqlFilter;
import cycle.oa_sshe.utils.MyUtils;

@Controller("documentAction")
@Scope("prototype")
public class DocumentAction extends BaseAction<Document> {
	
	private static final long serialVersionUID = 2936464321591483416L;
	
	 private File file;   //上传时从页面传过来的文件
	 private String fileContentType;    //文件类型  
	 private String fileFileName;    //文件名
	 private String fileNewNames;//获得修改后的附件名称拼接字符串
	 
	 private InputStream inputStream;//输入流

	 private Integer fileId;
	/**
	 * 已发公文列表
	 */
	public void publishListGrid(){
		Grid grid = new Grid();
		User user = (User) session.getAttribute("userSession");//获得session中的用户
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		if(!user.isAdmin()){//非管理员查询单位所发布的信息
			hqlFilter.addFilter("QUERY_t#publishUnit.id_I_EQ", String.valueOf(user.getUnit().getId()));
		}
		Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);//获取年份
        int month=cal.get(Calendar.MONTH);//获取月份，从0算起，也就是一月份是0
        int day=cal.get(Calendar.DATE);//获取日
		//SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
		//String da = dateFormater.format(new Date());
		String da = (year-1)+"-"+month+1+"-"+day;//过滤条件，当前年-1
		hqlFilter.addFilter("QUERY_t#createdatetime_D_GE", da);//只查询一年内的发文记录
		grid.setTotal(documentService.countByFilter(hqlFilter));//总记录数
		List<Document> list = documentService.findByFilter(hqlFilter,page,rows);
		for (Document document : list) {
			List<SignInfo> ls = signInfoService.find("from SignInfo where document.id="+document.getId());
			String tempName = "";
			if(ls!=null){
				int i = 0;
				for (SignInfo si : ls) {
					if(si.getState()){
						i++;
					}
				}
				tempName="已签收单位"+i+"个，未签收单位"+(ls.size()-i)+"个";
			}
			document.setSignInfoString(tempName);
		}
		grid.setRows(list);//获得当前页显示的数据
		writeJson(grid);
	}
	
	public void save(){
		Json json = new Json();
		Document d = new Document();
		BeanUtils.copyProperties(model, d); 
		d.setCreatedatetime(new Date());//创建时间
		User user = (User) session.getAttribute("userSession");
		if(user!=null){//设置发文人和发文单位
			d.setPublishUserName(user.getName());
			if(user.getUnit()!=null){
				d.setPublishUnit(user.getUnit());
			}
		}
		try {
			
			documentService.save(d);//保存公文信息
			
			String[] fileN = fileNewNames.split(",,,");//将传过来的文件名字符串转成文件名数组
			for (String fs : fileN) {
				MyFile myFile = new MyFile();
				myFile.setFileName(fs);
				myFile.setFilePath("D:/upload");
				myFile.setDocument(d);
				myFileService.save(myFile);//附件信息
			}
			
			//签收信息表
			Integer[] unitIds = MyUtils.string2Integer(ids);
			for (Integer integer : unitIds) {
				SignInfo si = new SignInfo();
				si.setSignUnit(unitService.getById(integer));//设置应签收的单位
				si.setDocument(d);//关联对应的公文
				if(user!=null){
					si.setPublishUserName(user.getName());//发布人姓名
					if(user.getUnit()!=null){
						si.setPublishUnitName(user.getUnit().getName());//发布人所属单位
						if(integer==user.getUnit().getId()){//如果发文单位也属于收文单位
							si.setState(true);
							si.setSignDate(new Date());
							si.setSignUnit(user.getUnit());
							si.setSignUserName("本单位发布");
						}
					}
				}
				signInfoService.save(si);
			}
			json.setSuccess(true);
			json.setMsg("公文发布成功！");
		} catch (Exception e) {
			json.setMsg("公文发布失败！");
		}
		
		
		
		writeJson(json);
	}
	
	/**
	 * 删除
	 */
	public void delete(){
		Json json = new Json();
		User user = (User) session.getAttribute("userSession");
		Integer[] docIds = MyUtils.string2Integer(ids);
		try {
			if(user!=null){
				for (Integer integer : docIds) {
					Document document = documentService.getById(integer);
					if("admin".equals(user.getLoginName())){//超级管理员,能删除任何公文
						Set<MyFile> myFiles = document.getMyFiles();//获得公文管理的附件，需要把附件一起删除
						for (MyFile myFile : myFiles) {//删除硬盘里的附件
							File f = new File(myFile.getFilePath()+"/"+myFile.getFileName());
							if(f.exists()){
								f.delete();
							}
							myFileService.delete(myFile);//删除附件对应数据库内容
						}
						Set<SignInfo> signInfos = document.getSignInfos();//获得签收信息列表,需要删除
						for (SignInfo signInfo : signInfos) {
							//signInfoService.delete(signInfo);
						}
						documentService.delete(document);//删除公文
					}else{//非超级管理员，只能删除属于自己单位发布的公文
						if(user.getUnit().getId().equals(document.getPublishUnit())){//判断用户是否属于公文的发文单位
							Set<MyFile> myFiles = document.getMyFiles();//获得公文管理的附件，需要把附件一起删除
							for (MyFile myFile : myFiles) {//删除硬盘里的附件
								File f = new File(myFile.getFilePath()+"/"+myFile.getFileName());
								if(f.exists()){
									f.delete();
								}
								myFileService.delete(myFile);//删除附件对应数据库内容
							}
							Set<SignInfo> signInfos = document.getSignInfos();//获得签收信息列表,需要删除
							for (SignInfo signInfo : signInfos) {
								//signInfoService.delete(signInfo);
							}
							documentService.delete(document);//删除公文
						}else{
							json.setMsg("删除失败！您没有权限删除不属于自己单位的公文！");
						}
					}
				}
				
				json.setSuccess(true);
				json.setMsg("成功删除【" + docIds.length + "】条数据！");
			}else{
				json.setMsg("删除失败！您还没有登录或登录已经失效！");
			}
		} catch (Exception e) {
			json.setMsg("删除失败");
			e.printStackTrace();
		}
		writeJson(json);
	}
	
	/**
	 * 附件上传处理方法
	 * @throws Exception
	 */
	public void uploadFile() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fileName = sdf.format(new Date())+"-" +fileFileName;//文件名前加上精确到毫秒的时间，防止文件名重复
		String filePath ="D:/upload";//附件存储路径
		// 写到指定的路径中
		File f = new File(filePath);
		// 如果指定的路径没有就创建
		if (!f.exists()) {
			f.mkdirs();
		}
		try {
			FileUtils.copyFile(file, new File(f,fileName));//文件拷贝 
			//返回ajax信息
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 附件下载
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String fileDown() throws UnsupportedEncodingException{
		MyFile myFile = myFileService.getById(fileId);
		User user = (User) session.getAttribute("userSession");
		Boolean sign = false;//是否能签收
		if(!user.isAdmin()){
			if(myFile.getDocument().getPublishUnit()!=null){
				if(user.getUnit().getId().equals(myFile.getDocument().getPublishUnit().getId())){
					sign=true;//如果是发文单位，可以下载
				}else{
					Set<SignInfo> signInfos = myFile.getDocument().getSignInfos();
					for (SignInfo signInfo : signInfos) {
						Unit u = signInfo.getSignUnit();
						if(u.getId().equals(user.getUnit().getId())){
							sign=true;//如果是收文单位，可以下载
						}
					}
				}
			}else{
				Set<SignInfo> signInfos = myFile.getDocument().getSignInfos();
				for (SignInfo signInfo : signInfos) {
					Unit u = signInfo.getSignUnit();
					if(u.getId().equals(user.getUnit().getId())){
						sign=true;//如果是收文单位，可以下载
					}
				}
			}
		}else{
			sign=true;//管理员 可以下载
		}
		//只有超级管理员admin、属于发文单位的用户、收文单位的用户才能下载该文件
		if(sign){
			String fileName = myFile.getFileName();
			int i =fileName.indexOf("-");
			fileFileName = java.net.URLEncoder.encode(fileName.substring(i+1, fileName.length()), "UTF-8");//写出到硬盘的文件名（去掉时间前缀）
			
			File f = new File(myFile.getFilePath()+"/"+myFile.getFileName());
			try {
				inputStream = new FileInputStream(f);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return "download";
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileNewNames() {
		return fileNewNames;
	}

	public void setFileNewNames(String fileNewNames) {
		this.fileNewNames = fileNewNames;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	
	
	
}
