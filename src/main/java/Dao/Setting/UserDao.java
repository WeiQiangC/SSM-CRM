package Dao.Setting;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import Domain.setting.Dept;
import Domain.setting.DeptType;
import Domain.setting.User;

public interface UserDao {

	User login(Map<String, String> map);

	void updatePwd(@Param("id")String id,@Param("newPwd")String newPwd);

	List<User> getUserList();

	List<Dept> pageList(@Param("start")int start, @Param("number")int number);

	int createDept(Dept dept);

	Dept getEditDept(String id);

	int editUpdate(Dept dept);

	Boolean delete(String[] id);

	List<Dept> getTotal();

	List<User> getUserListByLimt(@Param("start")int start, @Param("number")int number);

	Integer getUserTotal();

	List<DeptType> getTotalDeptType();


}
