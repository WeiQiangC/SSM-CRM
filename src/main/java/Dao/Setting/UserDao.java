package Dao.Setting;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import Domain.Setting.User;

public interface UserDao {

	User login(Map<String, String> map);

	void updatePwd(@Param("id")String id,@Param("newPwd")String newPwd);

}
