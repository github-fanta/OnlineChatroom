package com.liq.listener;

import java.util.HashMap;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;

import com.liq.vo.User;

/**
 * 
 * @author liq
 *
 */
public class MyServletContextListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		HashMap<User, HttpSession> userMap = new HashMap<User, HttpSession>();
		sce.getServletContext().setAttribute("userMap", userMap);		
	}

}
