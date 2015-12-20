package cycle.oa_sshe.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import cycle.oa_sshe.domain.User;
import cycle.oa_sshe.base.BaseServiceImpl;
import cycle.oa_sshe.dao.UserDao;
import cycle.oa_sshe.service.UserService;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{


	@Resource(name="userDao")
	private UserDao userDao;
	
	//根据登陆名和密码登录
	@Override
	public User login(String loginName, String pwd) {
		
		String hql = "from User u where u.loginName=:loginName and u.pwd=:pwd";
		Map<String,Object> params = new HashMap<String,Object>();
		String pwdMD5 = DigestUtils.md5Hex(pwd);//将输入的密码转成md5
		//将参数注入
		params.put("pwd",pwdMD5);
		params.put("loginName",loginName);
		
		User user = userDao.getByHql(hql, params);//根据参数查询
		
		if(user!=null){
			return user;
		}else{
			return null;
		}
	}

	//注册功能里的根据登陆名称查询是否存在
	@Override
	public User searchByLoginName(String loginName) {
		String hql = "from User u where u.loginName = :loginName";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginName", loginName);
		User user = userDao.getByHql(hql, params);
		if(user!=null){
			return user;
		}else{
			return null;
		}
	}



}
