package cycle.oa_sshe.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

@Controller("homeAction")
@Scope("prototype")
public class HomeAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5449497689553666108L;

	public String index() {
		return SUCCESS;
	}

	public String west() {
		return SUCCESS;
	}

	public String main() {

		return SUCCESS;
	}
	
	public String login(){
		return SUCCESS;
	}
}
