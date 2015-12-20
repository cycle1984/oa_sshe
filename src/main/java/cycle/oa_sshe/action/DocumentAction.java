package cycle.oa_sshe.action;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cycle.oa_sshe.domain.Document;
import cycle.oa_sshe.domain.MyFile;
import cycle.oa_sshe.domain.SignInfo;
import cycle.oa_sshe.domain.User;
import cycle.oa_sshe.base.BaseAction;
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
		grid.setTotal(documentService.countByFilter(hqlFilter));//总记录数
		grid.setRows(documentService.findByFilter(hqlFilter,page,rows));//获得当前页显示的数据
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
					}
				}
				
				
			}
			json.setSuccess(true);
			json.setMsg("公文发布成功！");
		} catch (Exception e) {
			json.setMsg("公文发布失败！");
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
	
}
