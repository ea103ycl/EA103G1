package com.painter.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.painter.model.PainterService;
import com.painter.model.PainterVO;
import com.painter_tag.model.*;
import com.painter_tag_map.model.PainterTagMapService;
import com.painter_tag_map.model.PainterTagMapVO;

//@WebServlet("/painter/TagGetPic") //mark by YCL
public class TagGetPic extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		res.setCharacterEncoding("UTF-8");

		PrintWriter out = res.getWriter();
		String action = req.getParameter("action");

		if ("tagGetPic".equals(action)) { // 要找出最熱門tag 最新tag
			
			String tag_desc = req.getParameter("searchBar1");
			PainterTagService ptSvc = new PainterTagService();
			List<Integer> list = ptSvc.getPicbyTag(tag_desc);
			 
			
			if(list.isEmpty()) {
				String errorMsgs=new String("No Data,Please confirm your search : "+tag_desc+" ?");
				req.setAttribute("errorMsgs",errorMsgs);
			}else {
				req.setAttribute("ptrnoList", list);
			}
			
			RequestDispatcher view = req.getRequestDispatcher("/frontend/front_index/front_index.jsp");
			view.forward(req, res);
			return;
		}

		if ("getMostLiked".equals(action)) {
			
			String rank = req.getParameter("rank");
			PainterService pSvc = new PainterService();
			Integer totalPic = pSvc.getPicCount();
			Integer rankBegin=Integer.valueOf(rank);
			if (rankBegin > totalPic) {
				return;
//				out.print("<div class=\"grid-item\"><h1>資料不足</h1></div>");
			}
			List<PainterVO> list = pSvc.getMostLiked(rankBegin,rankBegin+5);

			for (PainterVO pVO : list) {
				out.print("<div class=\"grid-item\">" 
			+ "<img src=\"/G1/painter/ShowImage?ptr_no="+pVO.getPtr_no()+"\"/>"
			+ "</div>");
			}
		}

		if ("searchByTag".equals(action)) {
			String srtag = req.getParameter("srtag");
			PainterTagService ptSvc = new PainterTagService();
			List<PainterTagVO> list = ptSvc.fuzzySearch(srtag);
			JSONObject jsonOb = new JSONObject();
			int i = 0;
			for (PainterTagVO ptVO : list) {
				if (i < 12) {
					i++;
					String result=ptVO.getTag_desc();

					try {
						jsonOb.put("bubble" + i, result);
					} catch (JSONException e) {
						throw new RuntimeException("(TagGetPic) searchByTag error:" + e.getMessage());
					}
				} else {
					break;
				}
			}
			out.println(jsonOb);
		}
	}
}
