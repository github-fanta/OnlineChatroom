package com.liq.action;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet{

	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		//设置字符集
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		
		//获取方法
		String methodName = req.getParameter("method");
		if (methodName == null || methodName =="") {
			methodName = "execute";
		}
		
		//反射执行方法
		Class c = this.getClass();
		try {
			Method m = c.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			String resultPage = (String) m.invoke(this, req, res);
			if (resultPage != null && resultPage != "") {
				req.getRequestDispatcher(resultPage).forward(req, res);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
