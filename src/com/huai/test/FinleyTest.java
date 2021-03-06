package com.huai.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huai.service.LoginService;
import com.huai.service.serviceImpl.LoginServiceImpl;

public class FinleyTest {

	public static void main(String[] args) {
		//得到spring容器的对象
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:config/applicationContext.xml");
		//得到LoginService这个bean，即LoginService的对象
		LoginService loginService = context.getBean(LoginService.class);
		//调用LoginService对象的方法
		boolean flag = loginService.validateStudent("liangyihuai", "123", null);
		//打印结果
		System.out.println("flag = "+flag);
		
	}
}
