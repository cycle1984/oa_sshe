package cycle.oa_sshe.domain;

import java.io.InputStream;
import java.io.Serializable;

/**
 * 附件信息(下载信息)
 * @author jyj
 *
 */
public class MyFile implements Serializable {

	private static final long serialVersionUID = 5693024496262736196L;

	private Integer id;
	private String fileName;//文件名
//	private String filePath;//文件存储路径
	
	private Document document;//所属公文
	
	//用于配置下载属性
	private InputStream inputStreamxxx;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public InputStream getInputStreamxxx() {
		return inputStreamxxx;
	}

	public void setInputStreamxxx(InputStream inputStreamxxx) {
		this.inputStreamxxx = inputStreamxxx;
	}
	
}
