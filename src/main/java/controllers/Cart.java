package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.ModelCart; //импорт модели


@WebServlet("/Cart")
public class Cart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Main.setEncoding(request, response);	
		
		String action = request.getParameter("act");  //получение action из POST запроса
		String userId = request.getParameter("userId");  //получение user_id из POST запроса
		String goodId = request.getParameter("goodId");  //получение good_id из POST запроса 
		String count = request.getParameter("count");  //получение count из POST запроса 
				
		switch (action) {
		case "add", "decline":   // нажатие "-" или "+" у количества товара
			if (userId != null && goodId != null && count != null) {
				try {
					if(!ModelCart.isUpdateCountGood(action, Integer.parseInt(userId), Integer.parseInt(goodId), Integer.parseInt(count))) {
						response.getWriter().print("Ошибка!");
					}				
				} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}			
		break;
		
		case "delete": // нажатие "удалить" у товара
			if(userId != null && goodId!=null) {
				try {
					if(ModelCart.isDeleteGoodFromCart(Integer.parseInt(userId), Integer.parseInt(goodId))) {
						response.getWriter().print("Товар удалён из корзины!");
					}else {
						response.getWriter().print("Ошибка!");
					}				
				} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}			 
		break;		
		}
	}
}
