package models.objects;


public class Good {
    private int id; // ID товара
    private String title; // название
    private String vendorCode; // артикул
    private int price; // стоимость
    private int oldPrice; // стоимость до скидки
    private String currency; // валюта
    private double weight; // минимальный вес
    private int size; // размер
    private String metall; // металл
    private int sample; //проба
    private String insertTo; // вставка
    private String image; //название файла с фото 
    
    private int count; // количество товара в корзине
	private int userId; // кто добавил в корзину товар
    
    // конструктор для создания товара с полной информацией
    public Good(int id, String title, String vendorCode, int price, 
    		String currency, double oldPrice, double weight, 
    		int size, String metall, int sample, String insertTo, String image) {
        this.id = id;
        this.title = title;
        this.vendorCode = vendorCode;
        this.price = price;
        this.oldPrice = (int) oldPrice;
        this.currency = currency;
        this.weight = weight;
        this.size = size;
        this.metall = metall;
        this.sample = sample;
        this.insertTo = insertTo;
        this.image = image;
    }      
      
    // конструктор для создания товара для добавления в корзину
	public Good(int id, String title, int price, String currency, String image, int count, int userId) {
		this.id = id;
		this.title = title;
		this.price = price;
		this.currency = currency;
		this.image = image;
		this.count = count;
		this.userId = userId;
	}


	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public int getId() { return id; }

    public String getTitle() { return title; }

    public String getVendorCode() { return vendorCode; }

    public int getPrice() { return price; }
    public int getOldPrice() { return oldPrice; }

    public String getCurrency() { return currency; }

    public double getWeight() { return weight; }

    public int getSize() { return size; }

    public String getMetall() { return metall; }

    public int getSample() { return sample; }

    public String getInsertTo() { return insertTo; }

    public String getImage() { return image; }

    @Override
    public String toString() {
        return "Good{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                ", weight=" + weight +
                ", size=" + size +
                ", metall='" + metall + '\'' +
                ", sample=" + sample +
                ", insertTo='" + insertTo + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}