package com.huai.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huai.utils.RoleUtil;
import com.huai.utils.ServletUtil;

import net.sf.json.JSONObject;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.huai.beans.Schedule;
import com.huai.service.ScheduleService;

@WebServlet(urlPatterns={"/schedule"})
public class ScheduleServlet extends HttpServlet {
	
	private ScheduleService scheduleService;
		
	@Override
	public void init() throws ServletException {
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext webAppContext = WebApplicationContextUtils
				.getWebApplicationContext(servletContext);
		scheduleService = webAppContext.getBean(ScheduleService.class);
	}	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String operate = request.getParameter("operate");
		System.out.println("operate = "+operate);
//		request.setCharacterEncoding("utf-8");
		
		//查询课程进度信息
		if("getSchedule".equals(operate)){
			int courseID = Integer.parseInt(request.getParameter("courseID"));	
			List<Schedule> schedule = scheduleService.getScheduleByCourseId(courseID);
			
			if(schedule!=null && schedule.size()>0){
				JSONObject jo = new JSONObject();
				jo.element("schedule", schedule);
				
				PrintWriter writer = response.getWriter();
				writer.write(jo.toString());
				writer.close();
			}else{
				//未查询到课程进度信息
				//To be continued---
				
			}
		}else if("addSchedule".equals(operate)){
			//添加课程进度
			
			
		}else if("deleteSchedule".equals(operate)){
			//删除课程进度
						
			
		}
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	

}