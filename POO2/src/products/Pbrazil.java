package products;

import java.util.Objects;

import Entities.Enum.productState;
import Entities.Enum.productToCountry;
import externalService.ImportationExpenses;
import externalService.RegionTax;

public class Pbrazil extends Product implements ImportationExpenses,RegionTax {

	public Pbrazil(String user, String product_name, Double price, Double weight, Boolean delivery_type,
			productState state, productToCountry tocountry) {
		super(user, product_name, price, weight, delivery_type);
		this.state = state;
		this.tocountry = tocountry;
	}
	private productState state;
	private productToCountry tocountry;
	private Double localcoin = this.getInitial_price();
	public Double getLocalcoin() {
		return localcoin;
	}

	public void setLocalcoin(Double localcoin) {
		this.localcoin = localcoin;
	}

	@Override
	public void regiontax() {
		Double regiontax=this.getPrice() * 0.08 + this.getPrice();
		this.setPrice(regiontax); 
	// Brazil tax is 8% (simulating a real taxation)	
	}
	
	public productState getState() {
		return state;
	}
	public void setState(productState state) {
		this.state = state;
	}
	public productToCountry getTocountry() {
		return tocountry;
	}
	public void setTocountry(productToCountry tocountry) {
		this.tocountry = tocountry;
	}

	
	public Pbrazil() {
	}
	@Override
	public void pay(Double value,Boolean delivery_type) {
		if(this.getDelivery_type() == false && value >= this.getPrice()) {
		
			price_Weight(); //taxation per 10 grams
			regiontax(); // Brazil tax
			Internal_transport(); //Car Transport tax
			Double newvalue = value - this.getPrice() ;
			System.out.println("Your debt was payed successfully! \n new balance is:" + newvalue + "R$");
			
			
		}else {
			throw new IllegalArgumentException("ERROR:Tried digital payment on normal payment \n"
					+ "Or invalid balance");
		}
	}
	@Override
	public void digitalpay(Double value,Boolean delivery_type,Integer token) {
		if(this.getDelivery_type() == true && value >= this.getPrice() && Objects.equals(this.getToken(), token)) {
			//using default taxation for digital service
			regiontax();
			price_Weight();
			Double newvalue = value - this.getPrice();
			System.out.println("Your debt was payed successfully! \n new balance is:" + newvalue  + "R$");
			
		}else {
			throw new IllegalArgumentException("ERROR: Tried normal payment on digital payment\n"
					+ "Or invalid balance");
	}
	}
	@Override
	public void Internal_transport() {
		
		Integer gasoline =4500 / 12;
		Double TotalGasoline = gasoline * 6.0;//gasoline Brazil price converted to R$
		Double taxGasoline = TotalGasoline * 0.03 + this.getPrice();
		this.setPrice(taxGasoline);
		//Maximum amount of gasoline needed to cross Brazil (with margin of error)
		//consumer gasoline tax is 3% (simulating taxation)
		//using real world values
	}
	@Override
	public void External_transport() {
		//already on Brazil no external transport needed
		
	}
	public void price_Weight() {
		//20 grams = 1 R$
		Double grams = this.getWeight() * 1000;
		
		Double per_gram = grams / 10;
		
		this.setPrice(per_gram + this.getPrice());
	}
	@Override
	public String toString() {
		
		if(this.getDelivery_type() == true) {
			return "Owner: " + this.getUser() + "\n Product: " + this.getProduct_name() + "\n InitialPrice: " + this.getInitial_price() + "PLUS |Weight R$1 tax per (10g)|Brazil tax 8%|FinalPrice: " + this.getPrice()
			+ "\n Weight: " + this.getWeight() + "\n Current State: " + this.getState() + "\n From brazil to" + this.getTocountry() +
			" \nUsuário: " + this.getUser() +  "\n Produto：" + this.getProduct_name() + "\n Preço Inicial: " + this.getInitial_price() + " Aumento | Querosene 0.0% | Gasolina taxação 3% | Peso 1R$ Por /10g | Brasil taxação 8% |Preço final:" + this.getPrice() + "\n Peso： " + this.getWeight()
			+ "\n Situação: " + this.getState() + "\n País: " + this.getTocountry();
		}
		
		
		return "Owner: " + this.getUser() + "\n Product: " + this.getProduct_name() + "\n InitialPrice: " + this.getInitial_price() + " PLUS |0,0% Kerosone tax|Gasoline tax 3%|Weight R$1 tax per (10g)|Brazil tax 8%|FinalPrice: " + this.getPrice() +  "\n Weight: " + this.getWeight() + "\n Current State: " + this.getState() + "\n From brazil to" + this.getTocountry() +
		" \nUsuário: " + this.getUser() +  "\n Produto： " + this.getProduct_name() + "\n Preço incial: " + this.getInitial_price() + " Aumento | Peso 1R$ por /10g | Gasoline taxação 5% |Preço final:" + this.getPrice() + "\n Peso:  " + this.getWeight()
		+ "\n Situação: " + this.getState() + "\n País: " + this.getTocountry();
	}
	
	
	
	
}
