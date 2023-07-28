package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import models.objects.Order;

public class ModelOrdering {
	
	public static List<String> sqlFields = new ArrayList<>(); // для SQL запросов
	public static int resultUpdateOrders = 0; // результат обновления таблицы заказов orders
	public static boolean isUpdateOrdersInfo; // результат обновления таблицы заказов orders_info
	public static int orderId = 0; // номер только что созданного заказа пользователя
	public static List<Order> orderInfo = new ArrayList(); // коллекция с информацией по заказам
	
	/* Проверка корректности данных для оформления заказа*/
	public static boolean isCorrectDataUser (int userId, String firstName, String lastName, String phoneUser, String email) throws SQLException {
		sqlFields.clear();
		sqlFields.add(Config.SQL_FIELDS[0]); // запрос всех данных	
		String where = "user_id=" + userId;		
		ResultSet rs = ORM.getInstance().select(sqlFields,Config.TABLE_USERS, where ); // запрос данных пользователя по id				
		if(rs.next()) {		
			if ( rs.getString("firstName").equals(firstName) && rs.getString("lastName").equals(lastName)
					&& rs.getString("phoneUser").equals(phoneUser) && rs.getString("email").equals(email) ) {			
				return true;
			}	
		}		
		return false;
	}	
		
	/*Обновление данных в таблице orders*/
	public static boolean isUpdateOrders(int userId) throws SQLException {
		HashMap<String, String> values = new HashMap<>();
		values.put(SqlFields.user_id.toString(), Integer.toString(userId));
		values.put(SqlFields.status.toString(), SqlFields.оформлен.toString());
		// получили результат обновления таблицы заказов
		resultUpdateOrders = ORM.getInstance().insert(Config.TABLE_ORDERS, values);
		return ModelCart.isCorrectResult(resultUpdateOrders);
	}
	
	/*Обновление данных в таблице orders_info  из таблицы заказов по userID */
	public static boolean isUpdateOrdersInfo(int userId) throws SQLException, ClassNotFoundException {
		// если таблица orders успешно обновлена
		if(resultUpdateOrders > 0) {
			orderId = getOrderId(userId); // получаем ID только что созданного заказа			
			if (orderId > 0) { // если с id заказа всё в порядке 
				isUpdateOrdersInfo = isUpdateOrders(userId, orderId);
				if (isUpdateOrdersInfo) {  // обновляем таблицу orders_info
					return true;
				}			
			}
		}		
		return false;
	}
	
	/*Получение id последнего заказа пользователя*/	
	public static int getOrderId(int userId) throws SQLException {		
		sqlFields.clear();
		sqlFields.add(Config.SQL_FIELDS[2]); // запрос MAX(order_id)		
		String where = "user_id=" + userId;	
		// получаем Id последнего заказа пользователя
		//	SELECT MAX(order_id) FROM `orders` WHERE user_id =1
		// иногда выбрасывает SQLException
		ResultSet rs = ORM.getInstance().select(sqlFields,Config.TABLE_ORDERS, where ); // запрос данных пользователя по id				
		if(rs.next()) {
			orderId = rs.getInt("MAX(order_id)");					
		}		
		return orderId;
	}
	
	/* Обновление таблицы с подродбной информацией о заказах orders_info*/
	public static boolean isUpdateOrders(int userId, int orderId) throws SQLException, ClassNotFoundException {
		orderInfo = getUserCartForOrder(userId);// из корзины достаем товары
		int countSuccess = 0;
		int result;
		HashMap<String, String> values = new HashMap<>();			
		for(var item : orderInfo) {			
			values.put(SqlFields.order_id.toString(), String.valueOf(orderId));		
			values.put(SqlFields.good_id.toString(), String.valueOf(item.getGoodId()));
			values.put(SqlFields.count.toString(), String.valueOf(item.getCount()));
			values.put(SqlFields.price.toString(), String.valueOf(item.getPrice()));
			values.put(SqlFields.date_created.toString(), String.valueOf(item.getDateCreated()));			
			// без дополнительно переменной иногда выбрасывает SQLException
			// Can not issue executeUpdate() or executeLargeUpdate() for SELECTs
			result = ORM.getInstance().insert(Config.TABLE_ORDERS_INFO, values);
			if(result > 0) {//если запись успешно обновлена
				countSuccess ++;
			}
		}		
		// если успешно вставили все строки в таблицу order_info
		if(countSuccess == orderInfo.size()) {//если запись успешно обновлена	
			return true;
		}				
		return false;
	}
	
	/*Получение данных из таблиц товаров и корзины для вставки в таблицу orders_info*/	
	public static List<Order> getUserCartForOrder(int userId){
		sqlFields.clear();		
		sqlFields.add(SqlFields.good_id.toString());  //запрос good_id
		sqlFields.add(SqlFields.count.toString());  //запрос  count
		sqlFields.add(SqlFields.price.toString());  //запрос price
		String where = "user_id=" + userId; // которые есть в таблице корзины неоформленных товаров			
		try {
			//	SELECT `good_id`, `count`, `price` FROM `cart` LEFT JOIN decorations ON cart.good_id=decorations.id WHERE user_id =1 
			ResultSet rs = ORM.getInstance().selectLeftJoin(sqlFields,Config.TABLE_CART, Config.TABLE_GOODS, SqlFields.good_id.toString(), SqlFields.id.toString(), where);
			orderInfo.clear(); // обнуляем данные предыдущего запроса			
			LocalDate date = LocalDate.now();  // получаем текущую дату				
			while(rs.next()) {				
				orderInfo.add(new Order(userId,
					rs.getInt("good_id"),
					rs.getInt("count"),
					rs.getInt("price"),
					java.sql.Date.valueOf(date)));						
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}				
		return orderInfo;
	}
	
	/*Удалениие данных из таблицы cart*/
	public static boolean isUpdateCart(int userId) throws SQLException {
		// если таблицы oreders и orders_info успешно обновлены
		if (isUpdateOrdersInfo) {
			String where = "user_id=" + userId;
			// без дополнительной переменной иногда выбрасывает SQLException
			int result = ORM.getInstance().delete(Config.TABLE_CART, where);
			if( result> 0) {
				orderId = 0;// обнуляем номер заказа
				return true;
			}			
		}
		return false;
	}
}
