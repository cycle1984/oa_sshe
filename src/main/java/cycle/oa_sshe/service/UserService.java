package cycle.oa_sshe.service;

import cycle.oa_sshe.domain.User;
import cycle.oa_sshe.base.BaseService;

public interface UserService extends BaseService<User>{

	public User login(String loginName,String pwd);//使用账号密码登录

	public User searchByLoginName(String loginN);
}
