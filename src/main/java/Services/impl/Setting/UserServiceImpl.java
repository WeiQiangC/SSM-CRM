package Services.impl.Setting;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Dao.Setting.UserDao;
import Domain.Setting.User;
import Exception.LoginException;
import Services.Setting.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDao userDao;
	
	@Override
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
		if(user.getExpireTime().compareTo(Utils.DateTimeUtil.getSysTime()) >0) {
			throw new LoginException("账号已失效");
		}
		if("0".equals(user.getLockState())) {
			throw new LoginException("账号已锁定");
		}
		return user;
	}

}
