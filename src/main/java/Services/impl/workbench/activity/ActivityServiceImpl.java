package Services.impl.workbench.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Dao.Setting.UserDao;
import Dao.workbench.activity.ActivityDao;
import Dao.workbench.activity.ActivityRemarkDao;
import Domain.setting.User;
import Domain.workbench.activity.Activity;
import Domain.workbench.activity.ActivityRemark;
import Services.workbench.activity.ActivityService;
import Vo.Activity.PageVo;
import Vo.Activity.PaginationVO;

@Service("activityService")
public class ActivityServiceImpl implements ActivityService {
	
	@Autowired
	private ActivityRemarkDao activityRemarkDao;
	@Autowired
	private ActivityDao activityDao;
	@Autowired
	private UserDao userDao;
	
	public Boolean deleteRemark(String id) {
		Boolean flag = true;
		int count = activityRemarkDao.deleteRemarkById(id);
		if(count != 1) {
			flag =false;
		}
		return flag;
	}
	
	public Boolean updateRemark(ActivityRemark remark) {
		Boolean flag = true;
		int count = activityRemarkDao.updateRemark(remark);
		if(count != 1) {
			flag =false;
		}
		return flag;
	}
	
	public Boolean saveRemark(ActivityRemark remark) {
		Boolean flag = true;
		int count = activityRemarkDao.saveRemark(remark);
		if(count != 1) {
			flag =false;
		}
		return flag;
	}
	
	public List<ActivityRemark> getRemarkListByAid(String activityId) {
		return activityRemarkDao.getRemarkListByAid(activityId);
	}
	
	public Activity detail(String id) {
		return activityDao.detail(id);
	}
	
	public boolean delete(String[] id) {
		Boolean flag = true;
		int count =  activityDao.delete(id);
		if(count != id.length) {
			flag = false;
		}
		return flag;
	}
	
	public boolean update(Activity activity) {
		Boolean flag  = true;
		int count =  activityDao.update(activity);
		if (count != 1) {
			flag = false;
		}
		return flag;
	}
	
	public Map<String, Object> getUserListAndActivity(String id) {
		List<User> uList = userDao.getUserList();
		Activity a = activityDao.getById(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uList", uList);
		map.put("a", a);
		return map;
	}
	
	public boolean save(Activity activity) {
		boolean flag = true;
		int count = activityDao.save(activity);
		if(count != 1) {
			flag = false;
		}
		return flag;
	}
	
	public List<User> getUserList() {
		List<User> uList = userDao.getUserList();
		return uList;
	}
	
	public PaginationVO<Activity> pageList(PageVo vo) {
		int total = activityDao.getTotalByCondition(vo);
		List<Activity> dataList =  activityDao.getActivityListByCondition(vo);
		PaginationVO<Activity> pVo = new PaginationVO<Activity>();
		pVo.setTotal(total);
		pVo.setDataList(dataList);
		return pVo;
	}
}
