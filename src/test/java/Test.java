import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import Controller.ActivityController;
import Domain.Setting.workbench.activity.Activity;
import Services.workbench.activity.ActivityService;
import Utils.MD5Util;
import Vo.Activity.PageVo;
import Vo.Activity.PaginationVO;

public class Test {

	@Autowired
	ActivityService activityService;
	
	@org.junit.Test
	public void test() {
		ActivityController activityController = new ActivityController();
		PageVo vo = new PageVo();
		vo.setPageNo(1);
		vo.setPageSize(4);
		vo.setSkipCount(0);
		PaginationVO<Activity> pVo= activityService.pageList(vo);
		JSONObject json = new JSONObject();
		json.put("data", pVo);
		System.out.println(json.toString());
	}
}
