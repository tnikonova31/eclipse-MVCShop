package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.objects.Good;
import models.objects.Order;

import java.util.HashMap;

public class ModelAvtorization {
			
	public static List<String> sqlFields = new ArrayList<>(); // для SQL запросов
	public static List<Good>goodsCart = new ArrayList(); // коллекция товаров из корзины
	
	/* Получение информации о заказах пользователя при входе в систему*/
	public static List<Order> getOrdersInfo(int userId){
		List<Order> ordersInfo = new ArrayList(); // коллекция с информацией о заказах для пользователя
		sqlFields.clear();		
		sqlFields.add(Config.SQL_FIELDS[0]);  // "*" запрос всех данных
		
		StringBuilder table = new StringBuilder();  // соединение названий 3-х таблиц
		table.append("`").append(Config.TABLE_ORDERS).append("`,")
		.append("`").append(Config.TABLE_ORDERS_INFO).append("`,")
		.append("`").append(Config.TABLE_GOODS).append("`");
		
		StringBuilder where = new StringBuilder(); // добавление условий соединения таблиц
		where.append("user_id=").append(userId)		// после условия where
		.append(Config.SQL_FIELD_FOR_ORDERS);
				
		try {
			// SELECT * FROM `orders`, orders_info,decorations WHERE user_id=1 
				//AND orders.order_id=orders_info.order_id AND orders_info.good_id=decorations.id;
			ResultSet rs = ORM.getInstance().select(sqlFields, table.toString(), where.toString());
			while(rs.next()) {
				ordersInfo.add(new Order(rs.getInt("order_id"),
					rs.getInt("user_id"),
					rs.getString("status"),
					rs.getInt("good_id"),
					rs.getInt("count"),					
					rs.getInt("price"),
					rs.getDate("date_created"),
					rs.getString("image")));			
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return ordersInfo;
	}
		
	/*Проверка есть ли введённые логин и пароль в БД*/
	public static int isfindAvtorization(String login, String password) {
		sqlFields.clear();		
		sqlFields.add(SqlFields.user_id.toString());  			
		try {
			// SELECT user_id FROM `users` WHERE (login=6 AND password=7);
			StringBuilder where = new StringBuilder()
					.append("(login='").append(login).append("' AND ")
					.append("password='").append(password).append("');");
			ResultSet rs = ORM.getInstance().select(sqlFields,Config.TABLE_USERS, where.toString() );
			if (rs.next()) {				
				return rs.getInt("user_id");  // если нашли, то вернём id пользователя
			} 			
		} catch (SQLException e) {
			e.printStackTrace();
		}					
		return -1; // пользователя с данными логином и паролем не нашли
	}
	
	/*получение из БД права доступа пользователя*/
	public static int getUserAccess(int userId) {
		sqlFields.clear();		
		sqlFields.add(SqlFields.access.toString());  //запрос access			
		try {
			StringBuilder where = new StringBuilder()
					.append("user_id=").append(userId);					
			ResultSet rs = ORM.getInstance().select(sqlFields,Config.TABLE_USERS, where.toString() );
			if (rs.next()) {
				return rs.getInt("access");  // если нашли, то вернём уровень доступа: 0 или 1
			} 			
		} catch (SQLException e) {
			e.printStackTrace();
		}					
		return -1;
	}
	
		
	/*Методы для перекидывания товаров в корзину пользователя, которые он добавил до авторизации */
	
	/*Получение данных из корзины с userId=0*/
	public static void addGoodsAfterAvtorisation(Integer userId) throws ClassNotFoundException, SQLException {
		List<Good>goodsCartBefore = new ArrayList(); // корзина неавторизованного пользователя
		goodsCartBefore = ModelCart.getCartGoods(Config.USER_ID_WITHOUT_AUTH);	// получение корзины товаров для userId=0	
		
		if (!goodsCartBefore.isEmpty()) { // если корзина неавторизованного пользователяне не пустая
			List<Good>goodsCartAfter = new ArrayList(); // корзина авторизованного пользователя
			goodsCartAfter = ModelCart.getCartGoods(userId); //получение корзины товаров для уже зарегистрированного пользователя
			
			for(var i=0; i< goodsCartBefore.size(); i++) { // ищем совпадения
				for(var goodAfter : goodsCartAfter) {
					if (goodsCartBefore.get(i).getId() == goodAfter.getId()) { // если есть одинаковые ID товара										
						updateCountGoodAfterAuth(userId,goodsCartBefore.get(i).getId()); // обновляем количество в корзине после авторизации
						deleteGoodBeforeAuth(Config.USER_ID_WITHOUT_AUTH, goodsCartBefore.get(i).getId()); // удаляем неавторизованные значения в корзине
						break;
					}
				}
			}
			updateUserId(userId); // меняем у оставшихся товаров в корзине userId с 0 на значение авторизованного userId
		}		
	}
	
	/*Обновление количества товара - добавление из неавторизованной корзины */
	public static void updateCountGoodAfterAuth(int userId, int goodId) throws SQLException {
		HashMap<String, String> values = new HashMap<>();
		String where = "good_id=" + goodId + " AND " + "user_id=" + userId;						
		values.put(SqlFields.count.toString(), Integer.toString(newCount(userId, goodId) + newCount(Config.USER_ID_WITHOUT_AUTH, goodId))); // количество увеличиваем			
		ORM.getInstance().update(Config.TABLE_CART, values, where);	// UPDATE `cart` SET `count`='10' WHERE (user_id=1 AND good_id=1);	
	}
	
		
	// считаем, новое количество в корзине для одинаковых товаров
	public static int newCount (int userId, int goodId) throws SQLException {
		sqlFields.clear();
		sqlFields.add(SqlFields.count.toString()); // запрос count		
		int result=0; // результат 
		String where = "good_id=" + goodId + " AND " + "user_id=" + userId;		
		ResultSet rs = ORM.getInstance().select(sqlFields,Config.TABLE_CART, where ); // запрос ID товара из корзины				
		if(rs.next()) {
			result = rs.getInt("count");
		}
		return result;
	}
	
	/*Удаляем товар из неавторизованной корзины */
	public static void deleteGoodBeforeAuth(int userId, int goodId) throws SQLException {
		String where = "good_id=" + goodId + " AND " + "user_id=" + userId;
		ORM.getInstance().delete(Config.TABLE_CART, where);
	}
	
	/* Обновление userId в корзине*/	
	public static void updateUserId(int userId) throws SQLException, ClassNotFoundException {
		int result=0; // результат операции
		HashMap<String, String> values = new HashMap<>();	
		String where = "user_id=" + Config.USER_ID_WITHOUT_AUTH;		
		values.put(SqlFields.user_id.toString(), String.valueOf(userId)); // меняем userId с 0 на авторизованный 
		result = ORM.getInstance().update(Config.TABLE_CART, values, where );				
	}
	
	/* Получение информации об одном заказе пользователя */
	public static List<Order> getOrderInfo(int userId, int orderId){
		List<Order> ordersInfo = getOrdersInfo(userId);
		// фильтруем информацию stream по номеру заказа
		return ordersInfo.stream().filter(item -> item.getOrderId() == orderId).toList();
	}	
}
