package Services.Setting;

import Domain.Setting.User;
import Exception.LoginException;

public interface UserService {

	public User login(String loginAct, String loginPwd) throws LoginException;

	public void updatePwd(String id, String newPwd);
}
