package models.objects;

import java.util.Date;

public class Order {
	 private int orderId; // ID заказа
	 private int  userId; // ID пользователя
	 private String statusOrder; // статус заказа: оформлен, доставлен
	 
	 private int  goodId; // ID товара
	 private int count; // количество товаров в заказе
	 private int price; // цена товара
	 private Date dateCreated;// дата создания заказа	
	 private String image; // изображение товара
	 
	 
	 // конструктор для вставки в таблицу orders
	 public Order(int orderId, int userId, String statusOrder) {
	        this.orderId = orderId;
	        this.userId = userId;
	        this.statusOrder = statusOrder;
	    }  
	 
	// конструктор для вставки в БД при оформлении заказа пользователем
		public Order(int orderId, int goodId, int count, int price, Date dateCreated) {
		        this.orderId = orderId;
		        this.goodId = goodId;
		        this.count = count;
		        this.price = price;
		        this.dateCreated = dateCreated;
		 }  
		    
	// конструктор для вывода обобщённой информации о заказе из таблиц orders и orders_info
		public Order(int orderId, int userId, String statusOrder, int goodId,
				 int count, int price, Date dateCreated, String image) {
	        this.orderId = orderId;
	        this.userId = userId;
	        this.statusOrder = statusOrder;
	        this.goodId = goodId;
	        this.count = count;
	        this.price = price;
	        this.dateCreated = dateCreated;
	        this.image=image;
	    }
		
	 	 

		
	    public int getOrderId() {
			return orderId;
		}

		public void setOrderId(int orderId) {
			this.orderId = orderId;
		}

		public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		public String getStatusOrder() {
			return statusOrder;
		}

		public void setStatusOrder(String statusOrder) {
			this.statusOrder = statusOrder;
		}

		public int getGoodId() {
			return goodId;
		}

		public void setGoodId(int goodId) {
			this.goodId = goodId;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public int getPrice() {
			return price;
		}

		public void setPrice(int price) {
			this.price = price;
		}

		public Date getDateCreated() {
			return dateCreated;
		}

		public void setDateCreated(Date dateCreated) {
			this.dateCreated = dateCreated;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		@Override
	    public String toString() {
	        return "Order{" +
	                "orderid=" + orderId +
	                ", userId='" + userId + '\'' +
	                ", statusOrder=" + statusOrder + '}';
	    }
	

}
