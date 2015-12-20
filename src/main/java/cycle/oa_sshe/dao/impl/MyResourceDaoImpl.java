package cycle.oa_sshe.dao.impl;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import cycle.oa_sshe.domain.MyResource;
import cycle.oa_sshe.base.BaseDaoImpl;
import cycle.oa_sshe.dao.MyResourceDao;

@Repository("myResourceDao")
public class MyResourceDaoImpl extends BaseDaoImpl<MyResource> implements
		MyResourceDao {

	@SuppressWarnings("unchecked")
	@Override
	public Collection<String> getAllMyResourceUrls() {
		return getCurrentSession().createQuery("SELECT DISTINCT r.url FROM MyResource r where r.url IS NOT NULL").list();
	}

}
