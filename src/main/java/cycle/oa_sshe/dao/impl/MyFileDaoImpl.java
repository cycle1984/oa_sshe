package cycle.oa_sshe.dao.impl;

import org.springframework.stereotype.Repository;

import cycle.oa_sshe.domain.MyFile;
import cycle.oa_sshe.base.BaseDaoImpl;
import cycle.oa_sshe.dao.MyFileDao;

@Repository("myFileDao")
public class MyFileDaoImpl extends BaseDaoImpl<MyFile> implements MyFileDao {


}
