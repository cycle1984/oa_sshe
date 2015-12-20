package cycle.oa_sshe.dao;

import java.util.Collection;

import cycle.oa_sshe.domain.MyResource;
import cycle.oa_sshe.base.BaseDao;

public interface MyResourceDao extends BaseDao<MyResource> {

	Collection<String> getAllMyResourceUrls();

}
