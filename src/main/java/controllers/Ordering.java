package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.ModelAdmin;
import models.ModelOrdering;
import models.ORM;


@WebServlet("/Ordering")
public class Ordering extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	public static List<String> sqlFields = new ArrayList<>(); // для SQL запросов

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String orderId = request.getParameter("orderId"); //получение данных из POST запроса
		String status = request.getParameter("status");
		
		if (orderId !=null && status !=null) {
			try {
				if(ModelAdmin.isChangeStatusOrder(Integer.parseInt(orderId), status)) { // статус заказа успешно изменён
					response.getWriter().print("true");
				} else {
					response.getWriter().print("false");
				}
			} catch (NumberFormatException | SQLException | IOException e) {				
				e.printStackTrace();
			}
		} else {
			response.getWriter().print("false");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Main.setEncoding(request, response);
						
		String userId = request.getParameter("userId"); //получение данных из POST запроса
		String firstName = request.getParameter("firstName");  
		String lastName = request.getParameter("lastName");  
		String phoneUser = request.getParameter("phoneUser"); 
		String email = request.getParameter("email");   
		
		if (userId !=null && firstName != null && lastName != null && phoneUser != null && email !=null) {			
						
			try {
				// проверка введённых данных при оформлении заказа
				if (ModelOrdering.isCorrectDataUser(Integer.parseInt(userId), firstName, lastName, phoneUser, email)) {					
					 
					// Код ниже иногда выдаёт ошибку "Can not issue executeUpdate() or executeLargeUpdate() for SELECTs"
					/*if (ModelOrdering.isUpdateOrders(Integer.parseInt(userId)) // таблица заказов обновлена
							&& ModelOrdering.isUpdateOrdersInfo(Integer.parseInt(userId))
							&& ModelOrdering.isUpdateCart(Integer.parseInt(userId))) { 
						response.getWriter().print("true");
					} else {
						response.getWriter().print("false");
					}*/
					
					// поэтому проверка поэтапно
					// если все товары успешно обновились и удалились из корзины
					if (ModelOrdering.isUpdateOrders(Integer.parseInt(userId))){
						if (ModelOrdering.isUpdateOrdersInfo(Integer.parseInt(userId))) {
							if (ModelOrdering.isUpdateCart(Integer.parseInt(userId))) {
								response.getWriter().print("true");
							}
						}
					} else {
						response.getWriter().print("false");
					}					
				} else {
					response.getWriter().print("false");
				}
			} catch (SQLException | IOException e) {				
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			}		
		} else {
			response.getWriter().print("false");
		}		
	}

}
