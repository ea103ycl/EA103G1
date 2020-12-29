package com.follow.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.follow.model.FollowService;
import com.follow.model.FollowVO;
import com.lv.model.LvService;
import com.lv.model.LvVO;
import com.mem.model.MemService;
import com.mem.model.MemVO;
import com.painter.model.PainterService;
import com.painter.model.PainterVO;
import com.painter_act.model.PainterActService;
import com.painter_act.model.PainterActVO;

public class FollowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		
		String action = req.getParameter("action");
		
		//��ajax
		if(action.contentEquals("update")) {
			
			res.setCharacterEncoding("UTF-8"); //���g�|�ýX
			res.setContentType("application/json");
			List<String> errorMsgs = new LinkedList<String>();
		    JSONArray jsonArray = new JSONArray(); //object���X
		    JSONObject jsonObject = null; //�C��object
		    
			try {
				/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/
				MemVO memVO = (MemVO)req.getSession().getAttribute("memVO");
				String sid = (String)req.getParameter("sid"); //�ϥΪ̥ثe�b����|�����D����
				String f_mem_id = (String)req.getParameter("f_mem_id"); //�ϥΪ̭n���`OR�������`����H
				
				System.out.println("====== �ϥ� ajax ���� �l�� ========");
				System.out.println("session�����ϥΪ̡i" + ( memVO != null ? memVO.getMem_id() : "NULL" ) + "�j�A�ثe���b�s���i" + sid + "�j���D���A�ín�D���`/�������`�i" + f_mem_id + "�j");
				
				if(memVO == null) {
					errorMsgs.add("please reLogin");
				}
				
				if (!errorMsgs.isEmpty()) {
					
					for(String msg :errorMsgs) {
						jsonObject = new JSONObject();
						jsonObject.put("errorMsgs", msg);
						jsonArray.put(jsonObject);
					}
										
					System.out.println("���~�T�� Object to JSON: " + jsonArray.toString());
					res.getWriter().write(jsonArray.toString());
					return; //�O�o���_�{��
				}
				
				/*************************** 2.�}�l�s�W���  **********************/
				FollowService followSvc = new FollowService();
				String mem_id = memVO.getMem_id(); // �|���s��
				
				//���P�_�O�_�w�����`��Ʀs�b
				Integer followdInd = followSvc.getCnt(mem_id, f_mem_id);
				System.out.println("��s�e�A�t�Τ� �i" + ( followdInd == 0 ? "�S��" : "��" ) + "�j" + mem_id + "���`" + f_mem_id + "�����");
				
				System.out.println("=========================���DAO�B�z");
				//��s���(dao���|�P�_�n�R��or�s�W)
				FollowVO followVO = new FollowVO();
				followVO.setMem_id(mem_id);
				followVO.setF_mem_id(f_mem_id);
				followSvc.update(followVO);
				System.out.println("=========================DAO�B�z����");
				
				//���P�_�O�_�w�����`��Ʀs�b
				followdInd = followSvc.getCnt(mem_id, f_mem_id);
				System.out.println("��s��A�t�Τ� �i" + ( followdInd == 0 ? "�S��" : "��" ) + "�j" + mem_id + "���`" + f_mem_id + "�����");
				
				//�P�_�ϥΪ̷�e�O�_�b�n���`/�������`��H���D���W
				Boolean updateSidDataInd = (f_mem_id.contentEquals(sid));
				System.out.println( "�ϥΪ̭n�D���ʪ����`��H�P��e�D���i" + updateSidDataInd + ( updateSidDataInd ? "�P�@�H" : "���P�H" ) + "�j" );
				
				//�P�_�ϥΪ̷�e�O�_�b�ۤv���D���W
				Boolean updateOwnerDataInd = (mem_id.contentEquals(sid));
				System.out.println( "�ϥΪ̻P��e�D���i" + updateOwnerDataInd + ( updateOwnerDataInd ? "�P�@�H" : "���P�H" ) + "�j" );
				
				//�Y�O�h�����ʷ�e�D�������`�H�ơB�����ơB���{�O
				Integer newFollowCnt = 0;
				Integer newFansCnt = 0;
				Integer newLv = 0;
				LvVO newLvVO = new LvVO();  
				if(updateSidDataInd || updateOwnerDataInd) {
					//������e�D���|�������`�H��
					newFollowCnt = followSvc.getFollowCnt(sid);
					System.out.println( "��e�D�����w���`�H���ܧ�" + newFollowCnt );
					
					//������e�D���|���������H��
					newFansCnt = followSvc.getFansCnt(sid);
					System.out.println( "��e�D���������H���ܧ�" + newFansCnt );
					
					//������e�D���|�������{�O����
					MemService memSvc = new MemService();
					newLv = memSvc.findByPrimaryKey(sid).getLv();
					
					//����lv
					LvService lvSvc = new LvService();
					newLvVO = lvSvc.findByPrimaryKey(newLv);
					System.out.println("��e�D�������{�O�����ܧ�" + newLv + "�A" + newLvVO.getLv_nm() + "�A�ӵ��ů����Ƴֶ̤�" + newLvVO.getF_cnt() + "�H");
					
				}
				
				jsonObject = new JSONObject();
				jsonObject.put("followdInd", followdInd); //���ʫ᪺�l�ܪ��p
				jsonObject.put("mem_id", mem_id);
				jsonObject.put("sid", sid);
				jsonObject.put("f_mem_id", f_mem_id); //�����ϥΪ̭n�D�l��/�����l�ܪ���H
//				jsonObject.put("updateSidDataInd", updateSidDataInd); //�O�_�n���ʷ�e�D���|�����������
				jsonObject.put("updateOwnerDataInd", updateOwnerDataInd); //�O�_�n���ʷ�e�D���|�����������(�ϥΪ̦b�ۤv�����W���ʪ����p)
				jsonObject.put("newFollowCnt", newFollowCnt); //��e�D���|�����̷s���`�H��
				jsonObject.put("newFansCnt", newFansCnt); //��e�D���|�����̷s�����H��
				jsonObject.put("newLv", newLv); //��e�D���|�����̷s���{�O����
				jsonObject.put("newLvNm",newLvVO.getLv_nm()); //��e�D���|�����̷s���{�O�W��
				jsonObject.put("newLvFCnt", newLvVO.getF_cnt()); //��e�D���|�����̷s���{�O���e
				jsonArray.put(jsonObject);
//				res.getWriter().write(jsonArray.toString());
				
				//�Ǫ��d�Ҫ��g�k
				res.setCharacterEncoding("UTF-8"); //�ݸ�e�ݺ����ۦP�s�X
				res.setContentType("application/json");
				PrintWriter out = res.getWriter();
				out.write(jsonArray.toString());
				out.flush();
				out.close();
								
			}catch(Exception e) {
				errorMsgs.add("�L�k���ʸ��:" + e.getMessage());
				System.out.println("���~�T��:" + e.fillInStackTrace().getMessage());
				
				try {
					for(String msg :errorMsgs) {
						jsonObject = new JSONObject();
						jsonObject.put("errorMsgs", msg);					
						jsonArray.put(jsonObject);
					}									
					System.out.println("���~�T�� Object to JSON: " + jsonArray.toString());
					res.getWriter().write(jsonArray.toString()); //�󦹮ɷ|return
				} catch (JSONException e1) {
					e1.printStackTrace();
					System.out.println("���~�T��:" + e1.fillInStackTrace().getMessage());
				}
			}
		}
		
		
		if(action.contentEquals("removeFans")) {
			
			res.setContentType("application/json");
			List<String> errorMsgs = new LinkedList<String>();
		    JSONArray jsonArray = new JSONArray(); //object���X
		    JSONObject jsonObject = null; //�C��object
		    
			try {
				/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/
				MemVO memVO = (MemVO)req.getSession().getAttribute("memVO");
				String fans_mem_id = (String)req.getParameter("fans_mem_id"); //�ϥΪ̭n����������ID
				
				System.out.println("====== �ϥ� ajax ���� �l�� ========");
				System.out.println("session�����ϥΪ̡i" + ( memVO != null ? memVO.getMem_id() : "NULL" ) + "�j�A�n�D���������i" + fans_mem_id + "�j");
				
				if(memVO == null) {
					errorMsgs.add("please reLogin");
				}
				
				if (!errorMsgs.isEmpty()) {
					
					for(String msg :errorMsgs) {
						jsonObject = new JSONObject();
						jsonObject.put("errorMsgs", msg);
						jsonArray.put(jsonObject);
					}
										
					System.out.println("���~�T�� Object to JSON: " + jsonArray.toString());
					res.getWriter().write(jsonArray.toString());
					return; //�O�o���_�{��
				}
				
				/*************************** 2.�}�l�s�W���  **********************/
				FollowService followSvc = new FollowService();
				String mem_id = memVO.getMem_id(); // �|���s��
				
				System.out.println("=========================���DAO�B�z");
				FollowVO followVO = new FollowVO();
				followVO.setMem_id(fans_mem_id);
				followVO.setF_mem_id(mem_id);
				followSvc.update(followVO);
				System.out.println("=========================DAO�B�z����");
				Integer followdInd = followSvc.getCnt(fans_mem_id, mem_id);
				System.out.println("��s��A�t�Τ� �i" + ( followdInd == 0 ? "�S��" : "��" ) + "�j" + fans_mem_id + "���`" + mem_id + "�����");
				
				//������e�D���|���������H��
				Integer newFansCnt = followSvc.getFansCnt(mem_id);
				System.out.println( "��e�D���������H���ܧ�" + newFansCnt );
					
				//������e�D���|�������{�O����
				MemService memSvc = new MemService();
				Integer newLv = memSvc.findByPrimaryKey(mem_id).getLv();
				
				//����lv
				LvService lvSvc = new LvService();
				LvVO newLvVO = lvSvc.findByPrimaryKey(newLv);
				System.out.println("��e�D�������{�O�����ܧ�" + newLv + "�A" + newLvVO.getLv_nm() + "�A�ӵ��ů����Ƴֶ̤�" + newLvVO.getF_cnt() + "�H");
				
				jsonObject = new JSONObject();
				jsonObject.put("fans_mem_id", fans_mem_id); //��������������ID
				jsonObject.put("newFansCnt", newFansCnt); //�̷s�����H��
				jsonObject.put("newLv", newLv); //�̷s���{�O����
				jsonObject.put("newLvNm", newLvVO.getLv_nm()); //��e�D���|�����̷s���{�O�W��
				jsonObject.put("newLvFCnt", newLvVO.getF_cnt()); //��e�D���|�����̷s���{�O���e

				jsonArray.put(jsonObject);
				res.getWriter().write(jsonArray.toString());
								
			}catch(Exception e) {
				errorMsgs.add("�L�k���ʸ��:" + e.getMessage());
				System.out.println("���~�T��:" + e.fillInStackTrace().getMessage());
				
				try {
					for(String msg :errorMsgs) {
						jsonObject = new JSONObject();
						jsonObject.put("errorMsgs", msg);					
						jsonArray.put(jsonObject);
					}									
					System.out.println("���~�T�� Object to JSON: " + jsonArray.toString());
					res.getWriter().write(jsonArray.toString()); //�󦹮ɷ|return
				} catch (JSONException e1) {
					e1.printStackTrace();
					System.out.println("���~�T��:" + e1.fillInStackTrace().getMessage());
				}
			}
		}
		
	}

}
