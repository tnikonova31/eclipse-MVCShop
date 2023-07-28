package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import models.objects.InfoAdmin;

public class ModelAdmin {
	
	public static List<String> sqlFields = new ArrayList<>(); // для SQL запросов
		
	public static List<InfoAdmin> getAllUsersOrders() {
		// SELECT DISTINCT orders.order_id, date_created, lastName, firstName, 
			//surName, phoneUser, status FROM users, orders, orders_info 
			//WHERE users.access=0 AND users.user_id=orders.user_id 
			//AND orders.order_id=orders_info.order_id ORDER BY order_id;
		
		// добавление полей для сложного запроса
		List<InfoAdmin> usersOrdersForAdmin = new ArrayList<>();
		sqlFields.clear();
		sqlFields.add(Config.SQL_FIELDS[7]); // DISTINCT orders.order_id
		sqlFields.add(SqlFields.date_created.toString());		
		sqlFields.add(SqlFields.lastName.toString());
		sqlFields.add(SqlFields.firstName.toString());
		sqlFields.add(SqlFields.surName.toString());
		sqlFields.add(SqlFields.phoneUser.toString());
		sqlFields.add(SqlFields.status.toString());		
		
		// формирование строки с названиями таблиц
		StringBuilder tables = new StringBuilder();
		tables.append("`").append(Config.TABLE_USERS).append("`,")
		.append("`").append(Config.TABLE_ORDERS).append("`,")
		.append("`").append(Config.TABLE_ORDERS_INFO).append("`");
		
		// условия объединения таблиц
		StringBuilder where = new StringBuilder(); 
		where.append("users.access=0")
		.append(Config.SQL_FIELD_FOR_ADMIN_ORDERS);
				
		try {
			ResultSet rs = ORM.getInstance().select(sqlFields, tables.toString(), where.toString());
			while(rs.next()){usersOrdersForAdmin.add(new InfoAdmin(
					rs.getInt(SqlFields.order_id.toString()),
					rs.getDate(SqlFields.date_created.toString()),
					rs.getString(SqlFields.lastName.toString()),
					rs.getString(SqlFields.firstName.toString()),
					rs.getString(SqlFields.surName.toString()),
					rs.getString(SqlFields.phoneUser.toString()),
					rs.getString(SqlFields.status.toString())));
			}				
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		return usersOrdersForAdmin;
	}
	
	/*Изменение статуса заказа*/
	public static boolean isChangeStatusOrder(int orderId, String status) throws SQLException {
		HashMap<String, String> values = new HashMap<>();		
		String where = "order_id=" + orderId;		
		if(status.equals("1")){
			values.put(SqlFields.status.toString(), SqlFields.доставлен.toString()); 
		} else {
			values.put(SqlFields.status.toString(), SqlFields.оформлен.toString());
		}
		int result = ORM.getInstance().update(Config.TABLE_ORDERS, values, where );
		return ModelCart.isCorrectResult(result);
	}
}
