package cycle.oa_sshe.dao.impl;

import org.springframework.stereotype.Repository;

import cycle.oa_sshe.domain.User;
import cycle.oa_sshe.base.BaseDaoImpl;
import cycle.oa_sshe.dao.UserDao;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {


}
