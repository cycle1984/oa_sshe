package cycle.oa_sshe.service;

import cycle.oa_sshe.domain.Role;
import cycle.oa_sshe.base.BaseService;

public interface RoleService extends BaseService<Role> {

	Role getByName(String name);

}
