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
import javax.servlet.http.HttpSession;

import models.Config;
import models.ModelAdmin;
import models.ModelAvtorization;
import models.ModelCart;	//импорт модели корзины
import models.ModelCatalog; //импорт модели каталога
import models.ModelMain;
import models.ModelRegistration;
import models.ORM;



@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static Integer userId; // id пользователя с открытой сессией
	public static Integer access; //   права доступа пользователя с открытой сессией: 0 - user, 1 -admin
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setEncoding(request, response);
		userId=0;
		access=0;
		
		HttpSession session = request.getSession();
		setHeaderParametrs(session, request); // установка параметров заголовка, получение userID из сессии
				
		var include = request.getParameter("action");	// получаем параметр action		
		var orderSuccess = request.getParameter("isOrdering");	// получаем  флаг оформлен заказ или нет
		var orderId = request.getParameter("orderId");	// получаем параметр 
		
		if(include != null) {	
			switch (include) {
				case "catalog": 
					try {
						request.setAttribute("goods", ModelCatalog.getAllGoods()); //передача в view всех товаров
					} catch (ClassNotFoundException | SQLException e) {					
						e.printStackTrace();
					}
					break;
					
				case "cart": 
					try {
						request.setAttribute("cart", ModelCart.getCartGoods(userId));  //передача в view корзины с товарами пользователя с заданным userId							
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}					
					break;		
				case "avtorization": 
					if(userId > 0) { // если пользователь авторизовался 
						request.setAttribute("ordersInfo", ModelAvtorization.getOrdersInfo(userId));// //передача в view заказов пользователя при авторизации
					}										
					break;		
				case "ordering": 
					if(orderSuccess != null) { // флаг пришёл 
						request.setAttribute("isOrdering", orderSuccess);// при успешном оформлении заказа true						
					} else {
						request.setAttribute("isOrdering", "false");// флаг не пришёл, значит заказ ещё не оформляли
					}										
					break;					
				case "orderInfo": 
					if(userId > 0 ) { // если пользователь авторизовался 
						if (orderId !=null) { // если у пользователя есть заказы							
							request.setAttribute("orderInfo", ModelAvtorization.getOrderInfo(userId, Integer.parseInt(orderId)));// отправляем в представление данные по его заказам
						} else if(access == 1) { // если администратор
							request.setAttribute("usersOrders", ModelAdmin.getAllUsersOrders());// отправляем в представление данные по всем заказам							
						}						
					}					
				break;
				case "exit":						
					if (ModelMain.isSessionOpen(session, "userId")) { // сессия у пользователя открыта
						session.invalidate();// при нажатиии на "Выход" сессии юзера чистятся.
						include = "mainPage"; // уходим на главную страницу
						request.setAttribute("login", "Вход");// устанавливаем снова вход
						userId=0; // обнуляем id						
					} else {
						include = "mainPage"; // уходим на главную страницу при закрытой сессии
					}						
					break;							
				case "usersAdmin":
					try {
						request.setAttribute("users", ModelRegistration.getAllUsers());
					} catch (ClassNotFoundException | SQLException e) {					
						e.printStackTrace();
					}					
				break;					
			}
			request.setAttribute("content",include + ".jsp");//подготовили переменную content в которой хранится название вьюшки для вывода актуальной страницы
		} else {
			if (access ==1) { // авторизовался admin
				try {  // отправляем товары из БД
					request.setAttribute("goods", ModelCatalog.getAllGoods());
				} catch (ClassNotFoundException | SQLException e) {					
					e.printStackTrace();
				}
				request.setAttribute("content", "catalog.jsp");//открываем ему каталог				
			} else {
				// удаляем данные из корзины предыдущего неавторизованного пользователя
				if (userId == 0) {
					String where = "user_id=" + userId;			
					try {
						ORM.getInstance().delete(Config.TABLE_CART, where);
					} catch (SQLException e) {					
						e.printStackTrace();
					}
				}				
				request.setAttribute("content","mainPage.jsp");//подготовили переменную content в которой хранится название вьюшки для вывода актуальной страницы
			}			
		}
		
		request.setAttribute("userId", userId);// при открытой сессии id > 0, отправляем iD во все представления		
		request.getRequestDispatcher("WEB-INF/views/index.jsp").forward(request, response);		
	}

	
	public static void setEncoding(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");		
	}
	
	/*Выбор параметров в заголовке: Вход или привествие пользователю*/
	public static void setHeaderParametrs(HttpSession session, HttpServletRequest request){		
		if (ModelMain.isSessionOpen(session, "userId")) {			
			String login ="";			
			userId = Integer.parseInt((String) session.getAttribute("userId"));
			access = Integer.parseInt((String) session.getAttribute("access"));
			try {
				login = ModelMain.getUser(userId); // получаем имя пользователя
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			if (access != 1) {
				request.setAttribute("login", "Добро пожаловать, " + login);// приветствие в заголовке пользователю
			} else {
				request.setAttribute("login", "Режим администратора");// приветствие в заголовке пользователю
			}		
		}else {
			request.setAttribute("login", "Вход");// просто кнопка Вход
		}		
	}



}
