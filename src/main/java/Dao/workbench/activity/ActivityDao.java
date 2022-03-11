package Dao.workbench.activity;

import java.util.List;

import Domain.setting.User;
import Domain.workbench.activity.Activity;
import Domain.workbench.activity.ActivityRemark;
import Vo.Activity.PageVo;

public interface ActivityDao {

	int getTotalByCondition(PageVo vo);

	List<Activity> getActivityListByCondition(PageVo vo);

	int save(Activity activity);

	Activity getById(String id);

	int update(Activity activity);

	int delete(String[] id);

	Activity detail(String id);

	List<ActivityRemark> getRemarkListByAid(String activityId);

}
