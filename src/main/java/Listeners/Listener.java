package Listeners;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import Domain.setting.Dept;
import Domain.setting.DeptType;
import Services.Setting.UserService;

@WebListener
public class Listener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("config/applicationContext.xml");
		UserService userService = (UserService) ctx.getBean("userService");
		List<DeptType> dList = userService.getTotalDeptType();
		sce.getServletContext().setAttribute("deptType", dList);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
