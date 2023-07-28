package models;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import models.objects.User;

public class ModelRegistration {
	
	public static List<String> sqlFields = new ArrayList<>(); // для SQL запросов
	public static List<User> users = new ArrayList<>(); // коллекция зарегистрированных пользователей из БД
	
	/*получение всех зарегистрированных пользователей */
	public static List<User> getAllUsers() throws SQLException, ClassNotFoundException{				
		sqlFields.clear();		
		sqlFields.add(Config.SQL_FIELDS[0]);  //запрос всех товаров
			
		try {
			ResultSet rs = ORM.getInstance().select(sqlFields,Config.TABLE_USERS, "" );
			users.clear(); // обнуляем данные предыдущего запроса			
			while(rs.next()) {				
				users.add(new User( rs.getString("lastName"), 
						rs.getString("firstName"), 
						rs.getString("surName"),
						rs.getString("phoneUser"),
						rs.getString("email"),
						rs.getString("login"), 
						rs.getString("password")));
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}				
		return users;		
	}
	
	/* Проверка все ли поля заполнены в форме регистрации*/
	public static boolean isCorrectUserData(HashMap<String, String> userData) {
		for (var item : userData.values()) {                       
			if(item.equals("")) {   // если значение пустое         	
            	return false; // 
            }            
        }		
		if (isSuccessCheck(userData)) {
			return true;
		} 
		return false;
	}
	
	/*Проверка введённых данных регулярными выражениями*/
	public static boolean isSuccessCheck(HashMap<String, String> userData) {
		int flag =0;		
		Pattern pattern = null;		
		// ФИО объединяем, так как 2 правила на кириллицу и латиницу
		pattern = Pattern.compile(Config.RULE_RUS);
		String lastName = userData.get("lastName");
		String firstName = userData.get("firstName");
		String surName = userData.get("surName");
		
		if (pattern.matcher(lastName).matches() &&  // проверяем кириллицу
				pattern.matcher(firstName).matches() &&
				pattern.matcher(surName).matches()) {
			flag++;
		} else { // проверяем латиницу
			pattern = Pattern.compile(Config.RULE_ENG); 
			if (pattern.matcher(lastName).matches() &&  
					pattern.matcher(firstName).matches() &&
					pattern.matcher(surName).matches()) {
				flag++;
			} 
		} 
		
		pattern = Pattern.compile(Config.RULE_PRHONE);		
		if(pattern.matcher(userData.get("phoneUser")).matches()) {
			flag++;			
		}
		
		pattern = Pattern.compile(Config.RULE_EMAIL);
		if(pattern.matcher(userData.get("email")).matches()) {
			flag++;			
		}
				
		pattern = Pattern.compile(Config.RULE_LOGIN);
		if(pattern.matcher(userData.get("login")).matches()) {
			flag++;			
		}

		pattern = Pattern.compile(Config.RULE_PASSWORD);
		if(pattern.matcher(userData.get("password")).matches()) {
			flag++;			
		}
		
		if (flag==5) {
			return true;
		}		
		return false;		
	}
	
	/*Проверка дублирования данных при регистрации нового пользователя*/
	public static String dataDublicate(List<User> users, HashMap<String, String> userData) throws IOException {
		String dublicate = ""; // пусть такой пользователь не зарегистрирован
		
		for (var item : users) {  // проверка на дубликаты в БД
			
			if (userData.get("login").equals(item.getLogin())) {
				dublicate = "логин";
				break;			
			} else if (userData.get("phoneUser").equals(item.getPhoneUser())) {
				dublicate = "телефон";
				break;
			} else if (userData.get("email").equals(item.getEmail())) {
				dublicate = "email";
				break;
			} else if (userData.get("firstName").equals(item.getfirstName()) && userData.get("lastName").equals(item.getLastName()) && userData.get("surName").equals(item.getSurName())) {				
				dublicate = "ФИО";
				break;							
			}
		}		
		return dublicate;
	}
	
	/* добавление нового пользователя*/	
	public static boolean addNewUser(HashMap<String, String> values) throws SQLException, ClassNotFoundException {		
		int result = ORM.getInstance().insert(Config.TABLE_USERS, values);
		return ModelCart.isCorrectResult(result);
	}

}
