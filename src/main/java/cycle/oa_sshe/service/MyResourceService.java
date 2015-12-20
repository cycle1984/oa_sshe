package cycle.oa_sshe.service;

import java.util.Collection;

import cycle.oa_sshe.domain.MyResource;
import cycle.oa_sshe.base.BaseService;

public interface MyResourceService extends BaseService<MyResource>{

	Collection<String> getAllMyResourceUrls();

}
