package cycle.oa_sshe.dao.impl;

import org.springframework.stereotype.Repository;

import cycle.oa_sshe.domain.Document;
import cycle.oa_sshe.base.BaseDaoImpl;
import cycle.oa_sshe.dao.DocumentDao;

@Repository("documentDao")
public class DocumentDaoImpl extends BaseDaoImpl<Document> implements
		DocumentDao {


}
