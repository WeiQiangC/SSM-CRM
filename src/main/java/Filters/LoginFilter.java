package Filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter({"*.do","*.jsp"})
public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse	resp = (HttpServletResponse) response;
		
		String path = req.getServletPath();
		//放行这两个不被过滤的
		if("/login.jsp".equals(path) || "/setting/user/login.do".equals(path)) {
			chain.doFilter(request, response);
		}else {
			HttpSession session = req.getSession();
			if(session.getAttribute("user") != null) {
				chain.doFilter(request, response);
			}else {
				
				/*
				 * 都使用的是绝对路径
				 * 转发使用的是一种特殊的绝对路径的使用方式，即前面不加/项目名，这种路径被称之为内部路径
				 *  /login.jsp
				 *  
				 *  重定向使用的是传统的绝对路径的写法，前面必须要以/项目名开头，后面跟具体的资源路径
				 *   /maven-crm/login.jsp
				 *   
				 *   使用重定向而不使用转发的原因：
				 *   使用转发地址栏的路径不会改变，只会停留在当前页面的路径
				 *   而使用重定向则地址栏的路径会发生改变
				 */
				//使用req.getContextPath进行动态的获取项目名
				resp.sendRedirect(req.getContextPath()+"/login.jsp");
			}
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
