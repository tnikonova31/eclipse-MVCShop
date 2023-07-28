package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ModelMain {
		
	public static List<String> sqlFields = new ArrayList<>(); // для SQL запросов
		
	/*Получение имени и отчества для авторизованного пользователя*/
	public static String getUser(int id) throws SQLException, ClassNotFoundException {
		sqlFields.clear();
		sqlFields.add(SqlFields.firstName.toString()); // запрос имени	
		sqlFields.add(SqlFields.surName.toString()); // запрос отчества
		
		ResultSet rs = ORM.getInstance().select(sqlFields,Config.TABLE_USERS, "user_id=" + id ); // запрос имени и отчества по ID
		String userFS = "";	
		if(rs.next()) {//если user найден с данным id		
			userFS = rs.getString(SqlFields.firstName.toString()) + " " +  rs.getString(SqlFields.surName.toString());
		}
		return userFS;
	}
	
	/* проверка открыта ли сессия*/
	public static boolean isSessionOpen(HttpSession session, String attribute) {
		if (session.getAttribute(attribute)!=null) {
			return true;
		}
		return false;
	}
}
