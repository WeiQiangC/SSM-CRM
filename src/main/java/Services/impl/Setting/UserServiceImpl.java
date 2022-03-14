package Services.impl.Setting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Dao.Setting.UserDao;
import Domain.setting.Dept;
import Domain.setting.DeptType;
import Domain.setting.User;
import Exception.LoginException;
import Services.Setting.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDao userDao;
	
	@Override
	public boolean detailUpdate(User user) {
		return userDao.detailUpdate(user);
	}
	
	@Override
	public String getDeptByName(String deptName) {
		return userDao.getDeptByName(deptName);
	}
	@Override
	public User getUserById(String id) {
		return userDao.getUserById(id);
	}
	
	@Override
	public List<User> searchUser(String userName, String deptName, String lockStatu, String startTime, String endTime) {
		return userDao.searchUser(userName,deptName,lockStatu,startTime,endTime);
	}
	
	@Override
	public Boolean deleteUser(String[] id) {
		return userDao.deleteUser(id);
	}
	
	@Override
	public Boolean saveUser(User user) {
		Boolean flag = true;
		int count = userDao.saveUser(user);
		if(count != 1) {
			flag = false;
		}
		return flag;
	}
	
	@Override
	public List<DeptType> getTotalDeptType() {
		return userDao.getTotalDeptType();
	}
	
	@Override
	public Integer getUserTotal() {
		return userDao.getUserTotal();
	}
	
	@Override
	public List<User> getUserList(String pageNo, String pageSize) {
		int start = Integer.valueOf(pageNo);
		 int number = Integer.valueOf(pageSize);
		 start = (start - 1)*number;
		return userDao.getUserListByLimt(start,number);
	}
	
	@Override
	public Integer getTotal() {
		List<Dept> dList = userDao.getTotal();
		return dList.size();
	}
	
	@Override
	public Boolean delete(String[] id) {
		
		return userDao.delete(id);
	}
	
	@Override
	public Boolean editUpdate(Dept dept) {
		Boolean flag = true;
		int count = userDao.editUpdate(dept);
		if(count != 1) {
			flag = false;
		}
		return flag;
	}
	
	@Override
	public Dept getEditDept(String id) {
		return userDao.getEditDept(id);
	}
	
	@Override
	public Boolean createDept(Dept dept) {
		Boolean flag = true;
		int count = userDao.createDept(dept);
		if(count != 1) {
			flag = false;
		}
		return flag;
	}
	
	public List<Dept> pageList(String pageNo, String pageSize) {
		 int start = Integer.valueOf(pageNo);
		 int number = Integer.valueOf(pageSize);
		 start = (start - 1)*number; 
		 return userDao.pageList(start,number);
	}
	
	public void updatePwd(String id, String newPwd) {
		userDao.updatePwd(id,newPwd);
	}
	
	public User login(String loginAct, String loginPwd) throws LoginException {
		//封装条件
		Map<String, String> map = new HashMap<String, String>();
		map.put("loginAct", loginAct);
		map.put("loginPwd", loginPwd);
		User user = userDao.login(map);
		
		//抛出异常类
		if(user == null) {
			throw new LoginException("账号密码错误");
		}
		if(user.getExpireTime().compareTo(Utils.DateTimeUtil.getSysTime()) <0) {
			throw new LoginException("账号已失效");
		}
		if("0".equals(user.getLockState())) {
			throw new LoginException("账号已锁定");
		}
		return user;
	}

}
