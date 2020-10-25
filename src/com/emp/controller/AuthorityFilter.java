package com.emp.controller;

import java.io.*;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;

import com.emp.model.Available_Func_Service;
import com.emp.model.Available_Func_VO;

public class AuthorityFilter implements Filter {

	private FilterConfig config;

	public void init(FilterConfig config) {
		this.config = config;
	}

	public void destroy() {
		config = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response, 
			FilterChain chain)
			throws ServletException, IOException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		HttpSession session = req.getSession();
		String emp_no = (String) session.getAttribute("emp_no");

		if (emp_no == null || emp_no.length() == 0) {
			res.sendRedirect(req.getContextPath() + "/backend/emp/login.jsp");
			return;
		}

		String location = req.getRequestURI();
		
		String a = req.getContextPath() + "/backend/emp/test_power.jsp";				
		String b = req.getContextPath() + "/backend/emp/test_power.jsp";
		String c = req.getContextPath() + "/backend/emp/test_power.jsp";
		String d1 = req.getContextPath() + "/backend/material/addMat.jsp";
		String d2 = req.getContextPath() + "/backend/material/listOneMat.jsp";
		String d3 = req.getContextPath() + "/backend/material/listAllMat.jsp";
		String e = req.getContextPath() + "/backend/emp/test_power.jsp";
		String f = req.getContextPath() + "/backend/emp/test_power.jsp";	
		String g = req.getContextPath() + "/backend/emp/test_power.jsp";
		String h = req.getContextPath() + "/backend/emp/test_power.jsp";
		String i = req.getContextPath() + "/backend/emp/test_power.jsp";
		String j = req.getContextPath() + "/backend/emp/test_power.jsp";
		String k = req.getContextPath() + "/backend/emp/test_power.jsp";
		String l1 = req.getContextPath() + "/backend/emp/addEmp.jsp";
		String l2 = req.getContextPath() + "/backend/emp/listOneEmp.jsp";
		String l3 = req.getContextPath() + "/backend/emp/listAllEmp.jsp";

		String funcList = (String)session.getAttribute("funcList");
		
//		Available_Func_Service AvaFuncSvc = new Available_Func_Service();
//		List<Available_Func_VO> list = AvaFuncSvc.getOneEmpFunc(emp_no);
//
//		StringBuffer sb = new StringBuffer();
//		for (Available_Func_VO available_Func_VO : list) {
//			String x = available_Func_VO.getFuncId();
//			sb.append(x);
//		}
//
//		String funcList = sb.toString();


		if (funcList.contains("A") && location.equals(a)) {
			chain.doFilter(request, response);
		} else if (funcList.contains("B") && location.equals(b)) {
			chain.doFilter(request, response);
		} else if (funcList.contains("C") && location.equals(c)) {
			chain.doFilter(request, response);
		} else if(funcList.contains("D") && (location.equals(d1)||
				location.equals(d2)||location.equals(d3))){
			chain.doFilter(request, response);			
		} else if (funcList.contains("E") && location.equals(e)) {
			chain.doFilter(request, response);
		} else if (funcList.contains("F") && location.equals(f)) {
			chain.doFilter(request, response);
		} else if (funcList.contains("G") && location.equals(g)) {
			chain.doFilter(request, response);
		} else if (funcList.contains("H") && location.equals(h)) {
			chain.doFilter(request, response);
		} else if (funcList.contains("I") && location.equals(i)) {
			chain.doFilter(request, response);
		} else if (funcList.contains("J") && location.equals(j)) {
			chain.doFilter(request, response);
		} else if (funcList.contains("K") && location.equals(k)) {
			chain.doFilter(request, response);
		} else if(funcList.contains("L") && (location.equals(l1)||
			location.equals(l2)||location.equals(l3))){
			chain.doFilter(request, response);
		} else {
			res.sendRedirect(req.getContextPath() + "/backend/emp/index.jsp");
		}
	}
}
