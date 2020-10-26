package com.bidding.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import com.bidding.model.BdRedis;

/**
 * Application Lifecycle Listener implementation class EventEndCheckListener
 *
 */
@WebListener
public class EventEndCheckListener implements ServletContextAttributeListener {


	public EventEndCheckListener() {
	}

	public void attributeAdded(ServletContextAttributeEvent sctae) {

		if ("event_no".equals(sctae.getName())) {
			String event_no = (String) sctae.getValue();
			System.out.println("(EventEndCheckListener) get event_no:" + event_no);
			BdRedis bdr = new BdRedis();
			bdr.registerBdNo(event_no);
			
			ServletContext ctx=sctae.getServletContext();  
			List<String> list = getLatestBdNo(event_no,3);
			ctx.setAttribute("latestBd1", list.get(0));
			ctx.setAttribute("latestBd2", list.get(1));
			ctx.setAttribute("latestBd3", list.get(2));
		}
	}

	public void attributeReplaced(ServletContextAttributeEvent sctae) {

		if ("event_no".equals(sctae.getName())) {
			String event_no = (String) sctae.getValue();
			System.out.println("(EventEndCheckListener) get event_no:" + event_no);
			BdRedis bdr = new BdRedis();
			bdr.registerBdNo(event_no);
			
			ServletContext ctx=sctae.getServletContext();  
			List<String> list = getLatestBdNo(event_no,3);
			if(list.size()<3) {
				return;
			}
			ctx.setAttribute("latestBd1", list.get(0));
			System.out.println(list.get(0));
			ctx.setAttribute("latestBd2", list.get(1));
			System.out.println(list.get(1));
			ctx.setAttribute("latestBd3", list.get(2));
			System.out.println(list.get(2));
		}
	}

	public List<String> getLatestBdNo(String event_no,Integer lastNum) {

		String bdNo = event_no;
		List<String> latest = new ArrayList<String>();
		for (int j = 1; j <= lastNum; j++) {
			Integer i = (Integer.valueOf(bdNo.substring(1)) - j);
			
			String latest1 = "000000" + String.valueOf(i);
			latest1 = "E" + latest1.substring(latest1.length() - 5);
			latest.add(latest1);
		}
		return latest;
	}

}
