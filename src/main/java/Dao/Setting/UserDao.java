package Dao.Setting;

import java.util.Map;

import Domain.Setting.User;

public interface UserDao {

	User login(Map<String, String> map);

}
