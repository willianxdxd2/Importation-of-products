package products;

import java.security.SecureRandom;

public abstract class Product {

	private String User;
	private String Product_name;
	private Double price;
	private Double Weight;
	private Boolean delivery_type;
	private Integer token;
	private Double initial_price;
	public Product(String user, String product_name, Double price, Double weight, Boolean delivery_type) {
		User = user;
		Product_name = product_name;
		this.price = price;
		initial_price = price;
		Weight = weight;
		this.delivery_type = delivery_type;
	}

	public Double getInitial_price() {
		return initial_price;
	}

	public void setInitial_price(Double initial_price) {
		this.initial_price = initial_price;
	}

	public void price_Weight() {
		//20 grams = 1 R$
		Double grams = this.getWeight() * 1000;
		
		Double per_gram = grams / 20;
		
		this.setPrice(per_gram + this.getPrice());
	}
	
	
	public Product() {

	}
	
	public Boolean getDelivery_type() {
		return delivery_type;
	}

	public void setDelivery_type(Boolean delivery_type) {
		this.delivery_type = delivery_type;
	}

	public String getUser() {
		return User;
	}
	public void setUser(String user) {
		User = user;
	}
	public String getProduct_name() {
		return Product_name;
	}
	public void setProduct_name(String product_name) {
		Product_name = product_name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getWeight() {
		return Weight;
	}
	public void setWeight(Double weight) {
		Weight = weight;
	}

	public void localCoin() {
	}
	public void digitaltoken(Boolean delivery_type) {
		if(delivery_type == true) {
			SecureRandom secureRandom = new SecureRandom();
			Integer token = secureRandom.nextInt(900000) + 100000;
			this.setToken(token);
			System.out.println(token);
		}else {
			throw new IllegalArgumentException("Token cannot be generated");
		}
	}

	public Integer getToken() {
		return token;
	}

	public void setToken(Integer token) {
		this.token = token;
	}

	public void pay(Double value, Boolean delivery_type) {
		}
	public void digitalpay(Double value, Boolean delivery_type, Integer token) {	
	}
}

