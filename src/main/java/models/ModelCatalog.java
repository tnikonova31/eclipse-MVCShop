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

public class ModelCatalog {
		
	public static List<Good>goods = new ArrayList(); // коллекция товаров из БД
	public static List<String> sqlFields = new ArrayList<>(); // для SQL запросов
	
	/*Получение всех товаров каталога из БД*/
	public static List<Good> getAllGoods() throws SQLException, ClassNotFoundException{				
		sqlFields.clear();		
		sqlFields.add(Config.SQL_FIELDS[0]);  // "*" запрос всех товаров			
		try {
			ResultSet rs = ORM.getInstance().select(sqlFields,Config.TABLE_GOODS, "" );
			goods.clear(); // обнуляем данные предыдущего запроса
			while(rs.next()) {				
				goods.add(new Good(rs.getInt("id"), 
						rs.getString("title"), 
						rs.getString("vendor_code"), 
						Integer.parseInt(rs.getString("price")),						
						rs.getString("currency"),
						Double.parseDouble(rs.getString("price"))*Config.DISCOUND,
						Double.parseDouble(rs.getString("weight")), 
						rs.getInt("size"), 
						rs.getString("metall"), 
						rs.getInt("sample"), 
						rs.getString("insertTo"), 
						rs.getString("image")));
	        }			
		} catch (SQLException e) {
			e.printStackTrace();
		}				
		return goods;	//возвращаем коллекцию со всеми товарами каталога	
	}
	
	/*Добавление товара в корзину из каталога*/	
	public static boolean addGood(int userId, int goodId) throws SQLException, ClassNotFoundException {		
		sqlFields.clear();
		sqlFields.add(SqlFields.id.toString()); // запрос id товара, который добавляем	
		sqlFields.add(SqlFields.count.toString()); // запрос count
		String where = "good_id=" + goodId + " AND " + "user_id=" + userId;
			
		ResultSet rs = ORM.getInstance().select(sqlFields,Config.TABLE_CART, where ); // запрос ID товара из корзины
		HashMap<String, String> values = new HashMap<>();
				
		if(rs.next()) {//если в корзине найден товар с данным id, то обновляем количество		
			int count = rs.getInt(SqlFields.count.toString());	// получаем количество, которое есть				
			values.put(SqlFields.count.toString(), Integer.toString(count+1));//готовим значение для обновления							
			if(ORM.getInstance().update(Config.TABLE_CART, values, where ) > 0) {//если запись успешно обновлена
				return true;
			}			
		}else {	// товара в корзине у данного пользователя нет
			values.put(SqlFields.user_id.toString(), Integer.toString(userId));
			values.put(SqlFields.good_id.toString(), Integer.toString(goodId));
			values.put(SqlFields.count.toString(), "1");
			
			if(ORM.getInstance().insert(Config.TABLE_CART, values) > 0) {//если запись успешно вставлена
				return true;
			}
		}
		return false;
	}		
}
