package Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import Domain.Setting.User;
import Domain.Setting.workbench.activity.Activity;
import Domain.Setting.workbench.activity.ActivityRemark;
import Services.workbench.activity.ActivityService;
import Utils.DateTimeUtil;
import Utils.UUIDUtil;
import Vo.Activity.PageVo;
import Vo.Activity.PaginationVO;

@Controller
@RequestMapping("/workbench/activity")
public class ActivityController {
	
	@Autowired
	private ActivityService activityService;

	@RequestMapping("/deleteRemark")
	@ResponseBody
	public Object deleteRemark(String id) {
		Boolean flag = activityService.deleteRemark(id);
		JSONObject json = new JSONObject();
		json.put("success",flag);
		return json.toString();
	}
	
	@RequestMapping("/updateRemark")
	@ResponseBody
	public Object updateRemark(ActivityRemark remark,HttpServletRequest request) {
		remark.setEditTime(DateTimeUtil.getSysTime());
		remark.setEditBy(((User)request.getSession().getAttribute("user")).getName());
		remark.setEditFlag("1");
		Boolean flag = activityService.updateRemark(remark);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", flag);
		map.put("ar", remark);
		return map;
	}
	
	
	@RequestMapping("/saveRemark")
	@ResponseBody
	public Object saveRemark(ActivityRemark remark,HttpServletRequest request) {
		remark.setId(UUIDUtil.getUUID());
		remark.setCreateTime(DateTimeUtil.getSysTime());
		remark.setCreateBy(((User)request.getSession().getAttribute("user")).getName());
		remark.setEditFlag("0");
		Boolean flag = activityService.saveRemark(remark);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", flag);
		map.put("ar", remark);
		return map;
	}
	
	@RequestMapping("/getRemarkListByAid")
	@ResponseBody
	public Object getRemarkListByAid(String activityId) {
		List<ActivityRemark> aList = activityService.getRemarkListByAid(activityId);
		return aList;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(String[] id) {
		System.out.println(id.toString());
		boolean flag = activityService.delete(id);
		JSONObject json = new JSONObject();
		json.put("success", flag);
		return json.toString();
		
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public Object update(Activity activity,HttpServletRequest request) {
		activity.setEditTime(DateTimeUtil.getSysTime());
		activity.setEditBy(((User)request.getSession().getAttribute("user")).getName());
		boolean flag = activityService.update(activity);
		JSONObject json = new JSONObject();
		json.put("success", flag);
		return json.toString();
		
	}
	
	@RequestMapping("/getUserListAndActivity")
	@ResponseBody
	public Object getUserListAndActivity(String id) {
		Map<String, Object> map =  activityService.getUserListAndActivity(id);
		return map;
		
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Object save(Activity activity,HttpServletRequest request) {
		activity.setId(UUIDUtil.getUUID());
		activity.setCreateTime(DateTimeUtil.getSysTime());
		activity.setCreateBy(((User)request.getSession().getAttribute("user")).getName());
		boolean flag = activityService.save(activity);
		JSONObject json = new JSONObject();
		json.put("success", flag);
		return json.toString();
	}
	
	
	@RequestMapping("/getUserList")
	@ResponseBody
	public Object getUserList() {
		List<User> uList = activityService.getUserList();
		return uList;
		
	}
	
	@RequestMapping("/pageList")
	@ResponseBody
	public Object pageList(PageVo vo) {
		vo.setSkipCount((vo.getPageNo() - 1)*vo.getPageSize());
		PaginationVO<Activity> pVo= activityService.pageList(vo);
		return pVo;
	}	
	
	@RequestMapping("/detail")
	public String detail(String id,Model model) {
		Activity a =  activityService.detail(id);
		model.addAttribute("a", a);
		return "/workbench/activity/detail.jsp";
		
	}
	
	
	
}
