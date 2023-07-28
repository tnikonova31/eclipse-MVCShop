package controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.ModelCatalog; //импорт модели


@WebServlet("/Catalog")
public class Catalog extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Main.setEncoding(request, response);
		String userId = request.getParameter("userId");  //получение userId из POST запроса при клике на кнопку "Купить"
		String goodId = request.getParameter("goodId");  //получение goodId из POST запроса
		if(userId != null && goodId != null) {			
			try {
				if(ModelCatalog.addGood(Integer.parseInt(userId), Integer.parseInt(goodId))) {
					response.getWriter().print("Товар добавлен в корзину!");
				}else {
					response.getWriter().print("Ошибка!");
				}				
			} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			} 
		}
	}
}
