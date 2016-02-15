package cycle.oa_sshe.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cycle.oa_sshe.base.BaseAction;
import cycle.oa_sshe.domain.LoginLog;

@Controller("newsAction")
@Scope("prototype")
public class NewsAction extends BaseAction<LoginLog> {

	private static final long serialVersionUID = 6731984335686062170L;
	
	

}
