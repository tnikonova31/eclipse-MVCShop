package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Config;
import models.ModelRegistration;
import models.SqlFields;
import models.objects.User;


@WebServlet("/Registration")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Main.setEncoding(request, response);		
		// добавление параметров при регистрации в HashMap пользователя
		HashMap<String, String> userData = new HashMap <>();
		userData.put("lastName", request.getParameter("lastName"));
		userData.put("firstName", request.getParameter("firstName"));
		userData.put("surName", request.getParameter("surName"));
		userData.put("phoneUser", request.getParameter("phoneUser"));
		userData.put("email", request.getParameter("email"));
		userData.put("login", request.getParameter("login"));
		userData.put("password", request.getParameter("password"));
		String goodAnswer = "Вы успешно зарегистрированы!";
			
			if (ModelRegistration.isCorrectUserData(userData)) { // все поля формы регистрации заполнены и проверены regx
				try {
					List<User> users = ModelRegistration.getAllUsers();
					if (users.size()>0) { // если есть зарегистрированные пользователи
						String dublicate = ModelRegistration.dataDublicate(users, userData);
						if (dublicate.equals("")) {  // если нет дубликата
														
							if (ModelRegistration.addNewUser(userData)) {
								response.getWriter().print(goodAnswer);
							} else {
								response.getWriter().print("Ошибка добавления пользователя");
							}	
						} else {
							response.getWriter().print("Пользователь с такими данными уже существует: " + dublicate);
						}
					} else {  // регистрация самого первого пользователя
						if (ModelRegistration.addNewUser(userData)) {
							response.getWriter().print(goodAnswer);							
						} else {
							response.getWriter().print("Ошибка добавления пользователя");
						}
					}										
				} catch (ClassNotFoundException | SQLException e) {					
					e.printStackTrace();
				}						
			} else {
				response.getWriter().print("Пожалуйста, заполните все поля. Проверьте правильность введённых данных!");
			}
	}	
}
