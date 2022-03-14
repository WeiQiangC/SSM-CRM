package Services.Setting;

import java.util.List;

import Domain.setting.Dept;
import Domain.setting.DeptType;
import Domain.setting.User;
import Exception.LoginException;

public interface UserService {

	public User login(String loginAct, String loginPwd) throws LoginException;

	public void updatePwd(String id, String newPwd);

	public List<Dept> pageList(String pageNo, String pageSize);

	public Boolean createDept(Dept dept);

	public Dept getEditDept(String id);

	public Boolean editUpdate(Dept dept);

	public Boolean delete(String[] id);

	public Integer getTotal();

	public List<User> getUserList(String pageNo, String pageSize);

	public Integer getUserTotal();

	public List<DeptType> getTotalDeptType();

	public Boolean saveUser(User user);

	public Boolean deleteUser(String[] id);

	public List<User> searchUser(String userName, String deptName, String lockStatu, String startTime, String endTime);

	public User getUserById(String id);

	public boolean detailUpdate(User user);

	public String getDeptByName(String deptName);
	
	
	
}
