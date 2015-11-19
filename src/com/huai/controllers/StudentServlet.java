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

import net.sf.json.JSONObject;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.huai.beans.Student;
import com.huai.service.ScheduleService;
import com.huai.service.StudentService;
import com.huai.utils.ServletUtil;



@WebServlet(urlPatterns={"/student"})
public class StudentServlet extends HttpServlet{
	
	
	private StudentService studentService;
	
	@Override
	public void init() throws ServletException {
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		studentService = webAppContext.getBean(StudentService.class);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String operate = request.getParameter("operate");
		
		System.out.println("operate = "+operate);
		
		if("list".equals(operate)){
			list(request, response);
		}else if("add".equals(operate)){
			add(request, response);
		}else if("delete".equals(operate)){
			delete(request, response);
		}
	}

	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String courseId = (String)request.getSession().getAttribute(ServletUtil.COURSE_ID);
		List<Student> students = studentService.getStudentsInTheCourse(courseId);
		request.setAttribute("students", students);
		
		if(students!=null && students.size()>0){
			JSONObject jo = new JSONObject();
			jo.element("schedule", students);
			
			PrintWriter writer = response.getWriter();
			writer.write(jo.toString());
			writer.close();
		}else{
			//此课程中没有学生
		}
	}
	
	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("stuName");
		String sex = request.getParameter("sex");
		String studentNo = request.getParameter("stuNum");
		
		Student s = new Student();
		s.setName(name);
		s.setPassword("111111");//设为默认密码
		s.setSex(sex);
		s.setStudentNO(studentNo);
		
		studentService.addStudent(s, ServletUtil.COURSE_ID);
		response.sendRedirect("student?operate=list");
	}
	
	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String courseId = (String)request.getSession().getAttribute(ServletUtil.COURSE_ID);
		String studentNo = request.getParameter("stuNum");
		studentService.deleteStudentFromTheCourse(studentNo, courseId);
		response.sendRedirect("student?operate=list");
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
