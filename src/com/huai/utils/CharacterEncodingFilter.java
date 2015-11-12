package com.huai.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 设置字符集编码
 * @ClassName: CharacterEncodingFilter 
 * @Description: TODO
 * @author LiangYH
 */
public class CharacterEncodingFilter implements Filter {

	protected FilterConfig filterConfig = null;
	protected String encoding = "";
	
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.encoding = filterConfig.getInitParameter("encoding");
	}


	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		if (encoding != null) {
			// 设置request和response的编程格
			request.setCharacterEncoding(this.encoding);
			response.setCharacterEncoding("utf-8");
		}
		// 继续执行下一个过滤器
		filterChain.doFilter(request, response);
	}

	public void destroy() {
		filterConfig = null;
		encoding = null;
	}
}