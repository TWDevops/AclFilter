package com.e104.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AclFilter implements Filter {

	private static final String aclprefix = "Acl:";
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) req;
		String apiName = httpRequest.getContextPath().substring(1);
		String acls = System.getProperty(aclprefix + apiName);
		System.out.println(apiName + " " + acls);
		String apikey = httpRequest.getHeader("APIKEY");
		
		if (acls != null && acls.contains("\"" + apikey + "\""))
		{
			System.out.println("ok");
			chain.doFilter(req, resp);
		}
		else
		{
			System.out.println("not allowed");
			//HttpServletResponse httpResponse = (HttpServletResponse)resp;
			//httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "This api key is not allowed");
			
			chain.doFilter(req, resp);
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		System.out.println("AclFilter Init");
	}
	
}
