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
		//��װ����
		Map<String, String> map = new HashMap<String, String>();
		map.put("loginAct", loginAct);
		map.put("loginPwd", loginPwd);
		User user = userDao.login(map);
		
		//�׳��쳣��
		if(user == null) {
			throw new LoginException("�˺��������");
		}
		if(user.getExpireTime().compareTo(Utils.DateTimeUtil.getSysTime()) >0) {
			throw new LoginException("�˺���ʧЧ");
		}
		if("0".equals(user.getLockState())) {
			throw new LoginException("�˺�������");
		}
		return user;
	}

}
