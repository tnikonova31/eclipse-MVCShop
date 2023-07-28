package models;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import models.ORM;
import models.objects.Good;

public class ModelCart {	

	public static List<Good>goodsCart = new ArrayList(); // коллекция товаров из корзины	
	public static List<String> sqlFields = new ArrayList<>(); // для SQL запросов
	
	/*Получение всех данных из корзины конкретного пользователя*/
	public static List<Good> getCartGoods(int userId) throws SQLException, ClassNotFoundException{				
		sqlFields.clear();		
		sqlFields.add(Config.SQL_FIELDS[0]);  //"*" запрос всех полей
		String where = "user_id=" + userId;
		
		try {
			ResultSet rs = ORM.getInstance()
					.selectInnerJoin(sqlFields,Config.TABLE_GOODS, Config.TABLE_CART, 
							SqlFields.id.toString(), SqlFields.good_id.toString(), where);
			goodsCart.clear(); // обнуляем данные предыдущего запроса
			while(rs.next()) {				
				goodsCart.add(new Good(rs.getInt("id"), 
						rs.getString("title"), 
						Integer.parseInt(rs.getString("price")),						
						rs.getString("currency"),						 
						rs.getString("image"),
						rs.getInt("count"),
						rs.getInt("user_id")));
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}				
		return goodsCart;		
	}
		
	/* Метод полностью удаляет товар из корзины  */
	public static boolean isDeleteGoodFromCart(int userId, int goodId) throws SQLException, ClassNotFoundException {
		String where = "good_id=" + goodId + " AND " + "user_id=" + userId;
		int result=ORM.getInstance().delete(Config.TABLE_CART, where ); 
		if(isCorrectResult(result)) { // проверяем было ли удаление
			return true;
		}
		return false;
	}
	
	/* Обновление количества товара при нажатии кнопок "-" или "+"*/	
	public static boolean isUpdateCountGood(String action, int userId, int goodId, int count) throws SQLException, ClassNotFoundException {
		int result=0; // результат увеличения/уменьшения
		
		HashMap<String, String> values = new HashMap<>();		
		String where = "good_id=" + goodId + " AND " + "user_id=" + userId;
		
		switch (action) {
		case "add":  // нажали на "+"			
			values.put(SqlFields.count.toString(), Integer.toString(count+1)); // количество увеличиваем
			result = ORM.getInstance().update(Config.TABLE_CART, values, where );	
		break;		
		case  "decline":  // нажали на "-"
			if(count>1) {
				values.put(SqlFields.count.toString(), Integer.toString(count-1));
				result = ORM.getInstance().update(Config.TABLE_CART, values, where);
			}else {
				result = ORM.getInstance().delete(Config.TABLE_CART, where);
			}
		break;
		}				
		return isCorrectResult(result);	
	}
	
	/*Проверка значения после обновления БД*/
	public static boolean isCorrectResult(int result) {
		if (result>0) {
			return true;
		} 
		return false;			
	}
}
