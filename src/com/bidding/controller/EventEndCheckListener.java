package com.bidding.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;

import javax.servlet.annotation.WebListener;

import com.bidding.model.BdRedis;
import com.bidding.model.BiddingService;

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

			try {
				String event_no = (String) sctae.getValue();
				System.out.println("(EventEndCheckListener) get event_no:" + event_no);
				if (event_no == null) {
				}
				BdRedis bdr = new BdRedis();
				bdr.registerBdNo(event_no);

				ServletContext ctx = sctae.getServletContext();
				List<String> list = getLatestBdNo(event_no, 3);
				ctx.setAttribute("latestBd1", list.get(0));
				ctx.setAttribute("latestBd2", list.get(1));
				ctx.setAttribute("latestBd3", list.get(2));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void attributeReplaced(ServletContextAttributeEvent sctae) {

		if ("event_no".equals(sctae.getName())) {
			String event_no = (String) sctae.getValue();
			System.out.println("(EventEndCheckListener) get event_no:" + event_no);
			try {
			if (event_no == null) {
				System.out.println("event_no is null");
				return;
			}
			BdRedis bdr = new BdRedis();
			System.out.println("#1");
			bdr.registerBdNo(event_no);
			System.out.println("#2");
			ServletContext ctx = sctae.getServletContext();
			System.out.println("#3");
			List<String> list = getLatestBdNo(event_no, 3);
			System.out.println("#4");
			if (list.size() < 3) {
			}
			ctx.setAttribute("latestBd1", list.get(0));
			ctx.setAttribute("latestBd2", list.get(1));
			ctx.setAttribute("latestBd3", list.get(2));
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	public List<String> getLatestBdNo(String event_no, Integer latestNum) {
		BiddingService bdSvc = new BiddingService();
		String bdNo = event_no;
		System.out.println("bdNo"+bdNo);
		List<String> latest = new ArrayList<String>();
		for (int j = 1; j <= latestNum; j++) {
			Integer i = (Integer.valueOf(bdNo.substring(1)) - j);

			String latest1 = "000000" + String.valueOf(i);
			if (bdSvc.getOne("B" + latest1) != null) {
				latest1 = "E" + latest1.substring(latest1.length() - 6);
				System.out.println("(EventEndCheckListener)Epno:" + latest1);
				latest.add(latest1);
			} else {
				latestNum++;
				if(latestNum>6) {
					break;
				}
				System.out.println("latestNum:"+latestNum);
			}
		}
		return latest;
	}

	@Override
	public void attributeRemoved(ServletContextAttributeEvent sctae) {

		String event_no = (String) sctae.getValue();
		System.out.println("(EventEndCheckListener) Ctx remove event_no:" + event_no);
	}

}
