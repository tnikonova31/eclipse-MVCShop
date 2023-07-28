package models;



import java.sql.*;
import java.util.HashMap;
import java.util.List;

public class ORM {
    static ORM object;
    static Connection connection;
    static Statement statement;
    static PreparedStatement preparedStatement;
    static StringBuilder sql = new StringBuilder(); // строка запроса SQL

    private ORM() throws SQLException {
        if (connection == null) {       	                      
            try {
    			Class.forName(Config.DRIVER);
    		} catch (ClassNotFoundException e1) {
    			e1.printStackTrace();
    		}//зарегистрировали драйвер
            String url = "jdbc:mysql://"+Config.SERVER+"/" + Config.DB;
            try {
    			connection = DriverManager.getConnection(url,Config.LOGIN,Config.PASSWORD);
    			statement = connection.createStatement();
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}         
        }                
    }

    /** Получаем единственный объект класса
     *
     * @return
     * @throws SQLException
     * @throws FileNotFoundException
     */
    public static ORM getInstance() throws SQLException {
        if (object == null) {
            object = new ORM();
        }
        return object;
    }

    /** Запрос SELECT
     *
     * @param fields какие поля требуется получить из БД
     * @param where условие выборки
     * @return
     * @throws SQLException
     */
    public ResultSet select(List <String> fields, String table,  String where) throws SQLException {
        sqlSelect(fields, table);    	
        addingWhere(where);        
        doSql();
        return preparedStatement.executeQuery();
    }
    
    // создание начала запроса SELECT, используется и при SELECT c INNER JOIN
    public StringBuilder sqlSelect(List <String> fields, String table) {
    	sql.append("SELECT ");
        for (int i = 0; i < fields.size(); i++) {
        	sql.append(fields.get(i)).append((i < fields.size() - 1 ? "," : ""));
        }
        sql.append(" FROM ").append(table);        
        return sql;
    }
   
    
    // Select id,title,price,user_id,count FROM goods INNER JOIN cart ON goods.good_id=cart.good_id    
    /** Запрос SELECT c INNER JOIN
    *
    * @param fields какие поля требуется получить из БД
    * @param tableFirst первая таблица для объединения
    * @param tableSecond вторая таблица для объединения
    * @param keyFirst ключ из первой таблицы
    * @param keySecond ключ из второй таблицы
    * @return
    * @throws SQLException
    */
   public ResultSet selectInnerJoin(List <String> fields, String tableFirst, String tableSecond, String keyFirst, String keySecond, String where) throws SQLException {
       sqlSelect(fields, tableFirst);
       sql.append(" INNER JOIN ").append(tableSecond).append(" ON ")
       .append(tableFirst).append(".").append(keyFirst).append("=")
       .append(tableSecond).append(".").append(keySecond);       
       addingWhere(where);      
       doSql();
       return preparedStatement.executeQuery();
   }

   
   // Запрос SELECT c LEFT JOIN
    
   public ResultSet selectLeftJoin(List <String> fields, String tableFirst, String tableSecond, String keyFirst, String keySecond, String where) throws SQLException {
   	   sqlSelect(fields, tableFirst);
       sql.append(" LEFT JOIN ").append(tableSecond).append(" ON ")
       .append(tableFirst).append(".").append(keyFirst).append("=")
       .append(tableSecond).append(".").append(keySecond);       
       addingWhere(where);       
       doSql();
       return preparedStatement.executeQuery();
   }
   
     
   /*SELECT с группировкой GROOP BY*/
   public ResultSet selectGroopBy(List <String> fields, String table, String groopBy) throws SQLException {
       sqlSelect(fields, table);    	
       addingGroopBy(groopBy);      
       doSql();
       return preparedStatement.executeQuery();
   }
   
   /** Запрос UPDATE
     *
     * @param table название таблицы
     * @param values значения для обновления
     * @param where условие
     * @return
     * @throws SQLException
     */
    public int update(String table, HashMap<String, String> values, String where) throws SQLException {
        String columns = "";
        sql.append("UPDATE ").append(table).append(" SET ");
        if (!values.isEmpty()) {
            int i = 0;
            for (var item : values.entrySet()) {
                columns = item.getKey() + "='" + item.getValue() + (i < values.size() - 1 ? "'," : "'");
                i++;
            }
            sql.append(columns);
        }
        addingWhere(where);       
        doSql();
        return preparedStatement.executeUpdate();
    }

    
    /** Запрос INSERT
     *
     * @param table название таблицы
     * @param values значения для вставки
     * @return
     * @throws SQLException
     */
    public int insert(String table, HashMap<String, String> values) throws SQLException {    	
    	String columns = "", sqlValues = "";
        sql.append("INSERT INTO ").append(table);
        if (!values.isEmpty()) {
            int i = 0;
            for (var item : values.entrySet()) {
                columns += item.getKey() + (i < values.size() - 1 ? "," : "");
                sqlValues += "'" + item.getValue() + (i < values.size() - 1 ? "'," : "'");
                i++;
            }
            sql.append(" (").append(columns).append(") VALUES (").append(sqlValues).append(");");           
        }        
        doSql();
        return preparedStatement.executeUpdate();
    }
    
    
     
    /** Запрос DELETE
     *
     * @param table название таблицы
     * @param where условие
     * @return
     * @throws SQLException
     */
    // DELETE FROM <имя таблицы > [WHERE <предикат>];
    public int delete(String table, String where) throws SQLException {
        sql.append("DELETE FROM ").append(table);
        addingWhere(where);
        doSql();
        return preparedStatement.executeUpdate();
    }

    /** Выполнение запроса, обнуление данных
     *
     * @throws SQLException
     */
    private static void doSql() throws SQLException {
    	try {
    		preparedStatement = connection.prepareStatement(sql.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}     
        sql.delete(0,sql.length()); // обнуляем sql запрос
    }

    /** Вставка условия в запросы
     *
     * @param where
     */
    public static void addingWhere(String where){
        if (!where.isEmpty()){
            sql.append(" WHERE ").append(where).append(";");  //.append(";") добавили ;
        }
    }
    
    public static void addingGroopBy(String groopBy){
        if (!groopBy.isEmpty()){
            sql.append(" GROUP BY ").append(groopBy);  //.append(";") добавили ;
        }
    }
}



