package models.objects;

public class User {
	private int id;  // id пользователя
	private String lastName; //фамилия
	private String firstName; // имя
	private String surName; // отчество	
	private String phoneUser; // телефон
	private String login; // логин
	private String email; // почта
	private String password; // пароль
	
	// Конструктор для создания объекта с полной информацией о пользователе
	public User(String lastName, String firstName, String surName, String phoneUser, String email, String login,
			  String password) {		
		this.login = login;
		this.firstName = firstName;
		this.lastName = lastName;
		this.surName = surName;
		this.login = login;
		this.phoneUser = phoneUser;
		this.password = password;
		this.email = email;
	}
	
	// Конструктор для создания объекта с ФИО пользователя для администратора
	public User(String lastName, String firstName, String surName) {		
		this.lastName = lastName;
		this.firstName = firstName;
		this.surName = surName;		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}
	
	public String getfirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	
	public String getPhoneUser() {
		return phoneUser;
	}
	
	public void setPhoneUser(String phoneUser) {
		this.phoneUser = phoneUser;
	}
	
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		
		return "User{"+
				"id="+id+				
				",userName='"+login+
				",phone='"+phoneUser+"}";
	}
}