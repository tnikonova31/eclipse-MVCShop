package controllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.ModelAvtorization;
import models.ORM;

@WebServlet("/Avtorization")
public class Avtorization extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static String login = "";
	static String password = "";   

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Main.setEncoding(request, response);
		login = request.getParameter("login"); // получение логина и пароля из формы
		password = request.getParameter("password");		
		int userId = ModelAvtorization.isfindAvtorization(login, password);// проверка логина и пароля в БД зарегистрированных пользователей
	 		
 		if( userId != -1) {  // пользователя нашли в БД зарегистрированных пользователях
 			// создаём сессию пользователя
 			HttpSession session = request.getSession();
	        session.setAttribute("userId", String.valueOf(userId)); // в сессии храним id авторизованного пользователя	        
	        int access = ModelAvtorization.getUserAccess(userId); // получаем уровень прав доступа авторизованного пользователя
	        session.setAttribute("access", String.valueOf(access)); // в сессии храним права доступа пользователя	        
	        response.getWriter().print("Авторизация выполнена. Добро пожаловать!");
	        
	        try {
	        	// после авторизации в корзину пользователя перекидываются товары,
	        	// которые он добавил до авторизации
	        	ModelAvtorization.addGoodsAfterAvtorisation(userId);
			} catch (ClassNotFoundException | SQLException e) {			
				e.printStackTrace();
			}	        
		} else { // не нашли логин или пароль в БД
 			response.getWriter().print("Неверный логин или пароль!"); 			
 		}		
	}	

}