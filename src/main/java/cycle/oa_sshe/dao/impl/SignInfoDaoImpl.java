package cycle.oa_sshe.dao.impl;

import org.springframework.stereotype.Repository;

import cycle.oa_sshe.base.BaseDaoImpl;
import cycle.oa_sshe.dao.SignInfoDao;
import cycle.oa_sshe.domain.SignInfo;


@Repository("signInfoDao")
public class SignInfoDaoImpl extends BaseDaoImpl<SignInfo> implements
		SignInfoDao {

}
