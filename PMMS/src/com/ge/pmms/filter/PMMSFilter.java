package com.ge.pmms.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ge.pmms.utils.PMMSConstants;

public class PMMSFilter implements Filter{
	
	//private FilterConfig filterConfig;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpreq = (HttpServletRequest) req;
		HttpServletResponse httpresp = (HttpServletResponse) resp;
		
		String urlpath = httpreq.getRequestURI();
		
		if(urlpath.contains(PMMSConstants.SHOW_PAGE_URL) || urlpath.contains("validate") || urlpath.contains("restful") || urlpath.contains("front")){
			chain.doFilter(req, resp);
		}else{
			HttpSession session = httpreq.getSession();
			
			if(session.getAttribute(PMMSConstants.USER_SESSION_DATA) == null){
				httpresp.sendRedirect("showpage");
			}else{
				chain.doFilter(req, resp);
			}
		}
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//this.filterConfig = filterConfig;
	}
	
	@Override
	public void destroy() {
		
	}

}
