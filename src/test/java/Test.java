import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import Controller.ActivityController;
import Domain.workbench.activity.Activity;
import Services.workbench.activity.ActivityService;
import Utils.MD5Util;
import Utils.UUIDUtil;
import Vo.Activity.PageVo;
import Vo.Activity.PaginationVO;

public class Test {

	@Autowired
	ActivityService activityService;
	
	@org.junit.Test
	public void test() {
		
		System.out.println(MD5Util.getMD5("123"));
	}
}
