package cycle.oa_sshe.service.impl;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cycle.oa_sshe.domain.MyResource;
import cycle.oa_sshe.base.BaseServiceImpl;
import cycle.oa_sshe.dao.MyResourceDao;
import cycle.oa_sshe.service.MyResourceService;

@Service("myResourceService")
public class MyResourceServiceImpl extends BaseServiceImpl<MyResource>
		implements MyResourceService {

	@Resource(name="myResourceDao")
	private MyResourceDao myResourceDao;
	
	/**
	 * 获取所拥有权限的所有URL
	 */
	@Override
	public Collection<String> getAllMyResourceUrls() {
		
		return myResourceDao.getAllMyResourceUrls();
	}

}
