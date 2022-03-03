package Controller;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import Domain.Setting.User;
import Exception.LoginException;
import Services.Setting.UserService;
import Utils.MD5Util;

@Controller
@RequestMapping("/setting")
public class UserController {
	
	@Autowired
	UserService userService;	
	
	@RequestMapping("/user/login.do")
	@ResponseBody
	public Object login(String loginAct,String loginPwd,HttpServletRequest request) {
		loginPwd = MD5Util.getMD5(loginPwd);
		JSONObject json = new JSONObject();
		try {
			User user = userService.login(loginAct,loginPwd);
			request.getSession().setAttribute("user", user);
			json.put("success", true);
		} catch (LoginException e) {
			e.printStackTrace();
			String msg = e.getMessage();
			json.put("msg", msg);
			json.put("success", false);
			return json.toString();
		}
		return json.toString();
	}
}
