package models;

public class Config {
	
	// настройки подключения к серверу
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String SERVER = "127.0.0.1:3307";
    public static final String DB = "decorations";
    public static final String LOGIN = "root";
    public static final String PASSWORD = "";
    
    // названия таблиц в БД
    public static final String TABLE_GOODS = "decorations"; //таблица товаров со всей информацией
    public static final String TABLE_CART = "cart"; // таблица с корзиной неоформленных товаров
    public static final String TABLE_USERS = "users"; // таблица зарегистрированных пользователей
    public static final String TABLE_ORDERS = "orders"; // таблица с заказами
    public static final String TABLE_ORDERS_INFO = "orders_info"; //таблица с подробной информацией о заказах
    
    // Сложные параметры sql запросов, которых нет в enum
    public static final String [] SQL_FIELDS = {"*", "SUM(count*price) AS sum", "MAX(order_id)",
    		"users.user_id", "orders.order_id", "orders_info.count", "orders_info.price", "DISTINCT orders.order_id"}; // для запросов SQL
    
    // для запроса всех заказов для пользователя. Соединение нескольких таблиц
    public static final String SQL_FIELD_FOR_ORDERS = "  AND orders.order_id=orders_info.order_id "
    		+ "AND orders_info.good_id=decorations.id " // соединение таблиц по полям
    		+ "ORDER BY orders.order_id DESC"; // сортировка заказов с информацией в обратном порядке
    
    // для запроса всех заказов всех пользователей для администратора. Соединение нескольких таблиц
    public static final String SQL_FIELD_FOR_ADMIN_ORDERS = " AND users.user_id=orders.user_id " // ключи соединения таблиц
    		+ "AND orders.order_id=orders_info.order_id "
    		+ "ORDER BY order_id"; // сортировка по номеру заказа
    	   
    
    // ID неавторизованного пользователя
    public static final int USER_ID_WITHOUT_AUTH = 0;
    
    //Параметры расчёта "мнимой" скидки в карточке товаров
    public static final double DISCOUND = 1.35;	// для расчёта цены до скидки в карточке товара	
    
    // Регулярные выражения для проверки данных при регистрации    
    public static final String RULE_RUS = "^([А-ЯЁ]{1}[а-яё]{1,23}(\\-[А-ЯЁ]{1}[а-яё]{1,23})?)$";
    public static final String RULE_ENG = "^([A-Z]{1}[a-z]{1,23}(\\-[A-Z]{1}[a-z]{1,23})?)$" ;
	public static final String RULE_PRHONE = "^8[0-9]{10}$";	
	public static final String RULE_EMAIL = "^[A-Za-z0-9!#$%&'*+\\=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+\\=?^_`{|}~-]+)*\\@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$";
	public static final String RULE_LOGIN = "^[A-Za-z]+([-_]?[a-z0-9]){4,}$";
	public static final String RULE_PASSWORD = "^[A-Za-z0-9]+([a-z0-9]){4,}$";    
}
