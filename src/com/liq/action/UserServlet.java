package com.liq.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.liq.service.UserService;
import com.liq.service.UserServiceImpl;
import com.liq.vo.User;

public class UserServlet extends BaseServlet{

	private UserService userService = new UserServiceImpl();
	
	public String login(HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, String[]> map = request.getParameterMap();
		User existUser = null;
		User user = new User();
		try {
			BeanUtils.populate(user, map);
			existUser = userService.login(user);
			if (existUser == null) {
				request.setAttribute("msg", "用户名称或密码错误！");
				return "/index.jsp";
			}
			
			//同一个浏览器的后来登录者，需要将之前的session销毁，不然会指向之前登录的session,理解为清空此浏览器的session
			request.getSession().invalidate();
			//同一个登录者不同浏览器。需要将之前自己的session销毁
			Map<User, HttpSession> userMap = (Map<User, HttpSession>) getServletContext().getAttribute("userMap");
			if (userMap.containsKey(existUser)) {//此处出现了比较两个对象，需要重写hashCode()和equals()方法
				userMap.get(existUser).invalidate();
			}
			
			request.getSession().setAttribute("existUser", existUser);
			ServletContext servletContext = getServletContext();
			String sourceMessage = "";
			if (servletContext.getAttribute("message") != null) {
				sourceMessage += servletContext.getAttribute("message");
			}
			sourceMessage += "欢迎"+"<font color='gray'>"+existUser.getUsername()+"走进了聊天室！"+"</font><br>";
			
			servletContext.setAttribute("message", sourceMessage);
			//重定向
			response.sendRedirect(request.getContextPath()+"/main.jsp");
			
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Kicking user from chatroom
	 * @return
	 * @throws IOException 
	 */
	public String kick(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String id = request.getParameter("id");
		User user = new User();
		user.setId(Integer.parseInt(id));
		Map<User, HttpSession> userMap = (Map<User, HttpSession>) getServletContext().getAttribute("userMap");

		User userA = new User();
		userA.setId(1);
		if (userMap.containsKey(user)) {
			//销毁对应session
			userMap.get(user).invalidate();
			userMap.get(userA).invalidate();
		}
	
		User u = userService.findUserById(id);
		String message = getServletContext().getAttribute("message")+ "<font color='gray'>"+u.getUsername()+"被踢下线</font><br>";
		getServletContext().setAttribute("message", message);
		//重定向页面
		response.sendRedirect(request.getContextPath()+"/main.jsp");
		return null;
	}
	
	/**
	 * Show all the chatting words in chatroom
	 * @return
	 * @throws IOException 
	 */
	public String getMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String message = (String) getServletContext().getAttribute("message");
		if (message != null) {
			response.getWriter().println(message);
		}
		return null;
	}
	
	public String sendMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String id = request.getParameter("id");
		User user = new User();
		user.setId(Integer.parseInt(id));
		Map<User, HttpSession> userMap = (Map<User, HttpSession>) getServletContext().getAttribute("userMap");
		if (!userMap.containsKey(user)) {
			//request.getSession().invalidate();
			return null;
		}
		String message = getServletContext().getAttribute("message").toString();
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		String face = request.getParameter("face")+" 说： ";
		String color = request.getParameter("color");
		String content = request.getParameter("content");
		String sendTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
		message += "<font color='blue'> "+from+" </font>"+" 对  "+"<font color='blue'> "+to+" </font>"+face+"说："
		+" <font color='#"+color+"'>"+content+"</font>"+"("+sendTime+")<br>";
		getServletContext().setAttribute("message", message);
		getMessage(request, response);
		return null;
	}
	
	/**
	 * 退出登录
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String exit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath()+"/index.jsp");
		return null;
	}
	
	/**
	 * 验证是否是在线用户
	 * @param request
	 * @param response
	 * @return 1：正常在线 2：非正常在线
	 * @throws IOException 
	 */
	public String check(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//map中的session就是existUser的引用，如果map中的session被销毁了，也就是对应user的session被销毁了
		User existUser =  (User) request.getSession().getAttribute("existUser");
		if (existUser == null) {
			response.getWriter().print(1);
		}
		return null;
	}
}
