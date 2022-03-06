package Dao.workbench.activity;

import java.util.List;

import Domain.Setting.User;
import Domain.Setting.workbench.activity.Activity;
import Vo.Activity.PageVo;

public interface ActivityDao {

	int getTotalByCondition(PageVo vo);

	List<Activity> getActivityListByCondition(PageVo vo);

	int save(Activity activity);

	Activity getById(String id);

	int update(Activity activity);

	int delete(String[] id);

	Activity detail(String id);

}
