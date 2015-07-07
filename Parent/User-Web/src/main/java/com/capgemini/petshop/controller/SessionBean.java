package com.capgemini.petshop.controller;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionBean {
	
	private SessionBean() {
		
	}

   public static HttpSession getSession() {
      return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
   }

   public static HttpServletRequest getRequest() {
      return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
   }

   public static String getUserName() {
      HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
      if (session.getAttribute("username").toString() != null) {
      return session.getAttribute("username").toString();
      }else {
		return "GUEST";
	}
   }

   public static String getUserId() {
      HttpSession session = getSession();
      if (session != null) {
         return (String) session.getAttribute("userid");
      } else {
         return null;
      }   
   }
}
