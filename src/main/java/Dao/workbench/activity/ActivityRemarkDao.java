package Dao.workbench.activity;

import java.util.List;

import Domain.Setting.workbench.activity.ActivityRemark;


public interface ActivityRemarkDao {

	int getCountByAids(String[] ids);

	int deleteByAids(String[] ids);

	List<ActivityRemark> getRemarkListByAid(String activityId);

	int deleteRemarkById(String id);

	int saveRemark(ActivityRemark ar);

	int updateRemark(ActivityRemark ar);

}
