package com.mem.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

@WebListener
public class LoginSessionvBindingListener implements HttpSessionBindingListener
{

	private String userName;

	private static int count;

	public static int getCount()
	{
		return count;
	}

	public LoginSessionvBindingListener()
	{
	}

	public LoginSessionvBindingListener(String userName)
	{
		this.userName = userName;
	}

	/**
	 * @see HttpSessionBindingListener#valueBound(HttpSessionBindingEvent)
	 */

	// ----------------------------�ϥΪ̵n�J--------------------------------
	public void valueBound(HttpSessionBindingEvent event)
	{
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();// ���oServlet����

		List onLineList = (List) application.getAttribute("onLineList");// �Nonlinelist�s�J���Ҥ�

		if (onLineList == null) // �p�G���Ҥ��S����list,�h�s�W�@�Өæs�J����
			{
				onLineList = new ArrayList<String>();
				application.setAttribute("onLineList", onLineList);
			}

		onLineList.add(this.userName); // �إ߷s����ɱNuserName�s�Jlist

		// �W�u�H�ƼW�[�æs�J����
		LoginSessionvBindingListener.count++;
		application.setAttribute("onlineCount", count);
	}

	/**
	 * @see HttpSessionBindingListener#valueUnbound(HttpSessionBindingEvent)
	 */
	// ----------------------------�ϥΪ̵n�X--------------------------------
	public void valueUnbound(HttpSessionBindingEvent event)
	{
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();
		List onLineList = (List) application.getAttribute("onLineList");// ���o���Ҥ���list
		onLineList.remove(this.userName); // �N��userNames�qlist����

		// �W�u�H�ƴ�֨æs�J����
		LoginSessionvBindingListener.count--;
		application.setAttribute("onlineCount", count);
	}

}
