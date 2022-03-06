package Services.workbench.activity;

import java.util.List;
import java.util.Map;

import Domain.Setting.User;
import Domain.Setting.workbench.activity.Activity;
import Vo.Activity.PageVo;
import Vo.Activity.PaginationVO;

public interface ActivityService {

	PaginationVO<Activity> pageList(PageVo vo);

	List<User> getUserList();

	boolean save(Activity activity);

	Map<String, Object> getUserListAndActivity(String id);

	boolean update(Activity activity);

	boolean delete(String[] id);

	Activity detail(String id);

}
