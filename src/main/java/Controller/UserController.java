package Controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	@RequestMapping("/qx/user/detailUpdate")
	@ResponseBody
	public boolean detailUpdate(User user,HttpServletRequest request) {
		user.setLoginPwd(MD5Util.getMD5(user.getLoginPwd()));
		user.setEditTime(DateTimeUtil.getSysTime());
		user.setDeptno(userService.getDeptByName(user.getDeptno()));
		if ("启用".equals(user.getLockState())) {
			user.setLockState("1");
		}else if("锁定".equals(user.getLockState())) {
			user.setLockState("0");
		}else {
			user.setLockState("1");
		}
		return userService.detailUpdate(user);
	}
	
	@RequestMapping("/qx/user/detail")
	public Object detail(String id,Model model) {
		User u = userService.getUserById(id);
		if("0".equals(u.getLockState())) {
			u.setLockState("锁定");
		}else {
			u.setLockState("启用");
		}
		model.addAttribute("u",u);
		return "/settings/qx/user/detail.jsp";
	}
	
	@RequestMapping("/qx/user/searchUser")
	@ResponseBody
	public Object searchUser(String userName,String deptName,String lockStatu,String startTime,String endTime) {
		if ("启用".equals(lockStatu)) {
			lockStatu = "1";
		}else if ("锁定".equals(lockStatu)) {
			lockStatu = "0";
		}
		List<User> uList = userService.searchUser(userName,deptName,lockStatu,startTime,endTime);
		return uList;
	}
	
	@RequestMapping("/qx/user/deleteUser")
	@ResponseBody
	public boolean deleteUser(String[] id) {
		Boolean flag = userService.deleteUser(id);
		return flag;
	}
	
	@RequestMapping("/qx/user/saveUser")
	@ResponseBody
	public boolean saveUser(User user,HttpServletRequest request) {
		user.setId(UUIDUtil.getUUID());
		if("启动".equals(user.getLockState())) {
			user.setLockState("1");
		}else if("锁定".equals(user.getLockState())) {
			user.setLockState("0");
		}else {
			user.setLockState("1");
		}
		user.setCreateBy(((User)request.getSession().getAttribute("user")).getName());
		user.setCreateTime(DateTimeUtil.getSysTime());
		Boolean flag = userService.saveUser(user);
		return flag;
	}
	
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
