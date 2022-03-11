package Controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import Domain.setting.Dept;
import Domain.setting.User;
import Exception.LoginException;
import Services.Setting.UserService;
import Utils.DateTimeUtil;
import Utils.MD5Util;
import Utils.UUIDUtil;

//setting模块
@Controller
@RequestMapping("/setting")
public class UserController {
	
	@Autowired
	UserService userService;	
	
	@RequestMapping("/qx/user/getUserTotal")
	@ResponseBody
	public Integer getUserTotal() {
		return userService.getUserTotal();
	}
	
	@RequestMapping("/qx/user/getUserList")
	@ResponseBody
	public Object getUserList(String pageNo,String pageSize) {
		List<User> uList = new ArrayList<User>();
		uList = userService.getUserList(pageNo,pageSize);
		return uList;
	}
	
	
	@RequestMapping("/dept/delete")
	@ResponseBody
	public Object delete(String[] id) {
		Boolean flag = userService.delete(id);
		JSONObject json = new JSONObject();
		json.put("success", flag);
		return json.toString();
	}
	
	@RequestMapping("/dept/editUpdate")
	@ResponseBody
	public Object editUpdate(Dept dept) {
		Boolean flag = userService.editUpdate(dept);
		JSONObject json = new JSONObject();
		json.put("success", flag);
		return json.toString();
	}
	
	@RequestMapping("/dept/getEditDept")
	@ResponseBody
	public Object getEditDept(String id) {
		Dept dept = userService.getEditDept(id);
		return dept;
	}
	
	@RequestMapping("/dept/createDept")
	@ResponseBody
	public Object createDept(Dept dept) {
		dept.setId(UUIDUtil.getUUID());
		dept.setCreateTime(DateTimeUtil.getSysTime());
		Boolean flag = userService.createDept(dept);
		JSONObject json = new JSONObject();
		json.put("success", flag);
		return json.toString();
	}
	
	@RequestMapping("/dept/getTotal")
	@ResponseBody
	public Integer getTotal() {
		return userService.getTotal();
	}
	
	@RequestMapping("/dept/pageList")
	@ResponseBody
	public Object pageList(String pageNo,String pageSize) {
		List<Dept> dList = userService.pageList(pageNo,pageSize);
		return dList;
	}
	
	//登录方法
	@RequestMapping("/user/login")
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
	
	//修改个人密码
	@RequestMapping("/user/updatePwd")
	@ResponseBody
	public Object updatePwd(String oldPwd,String newPwd,HttpServletRequest request) {
		JSONObject json = new JSONObject();
		User user = (User) request.getSession().getAttribute("user");
		if(!MD5Util.getMD5(oldPwd).equals(user.getLoginPwd())) {
			json.put("success", false);
		}else {
			userService.updatePwd(user.getId(),MD5Util.getMD5(newPwd));
			json.put("success", true);
		}
		return json.toString();
	}
}
