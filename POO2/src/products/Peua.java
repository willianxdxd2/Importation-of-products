package products;

import java.util.Objects;

import Entities.Enum.productState;
import Entities.Enum.productToCountry;
import externalService.ImportationExpenses;
import externalService.RegionTax;

public class Peua extends Product implements ImportationExpenses,RegionTax{

	private productState state;
	private productToCountry tocountry;
	private Double localcoin = this.getInitial_price();

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
	public Peua(String user, String product_name, Double price, Double weight, Boolean delivery_type,
			productState state, productToCountry tocountry) {
		super(user, product_name, price, weight, delivery_type);
		this.state = state;
		this.tocountry = tocountry;
	}
	public Peua(){
	}
	public void regiontax() {
		Double regiontax=(this.getPrice() * 0.010) + this.getPrice();
		this.setPrice(regiontax); 
	// EUA tax is 3% (simulating a real taxation)	
	}
	@Override
	public void pay(Double value,Boolean delivery_type) {
		if(this.getDelivery_type() == false && value >= this.getPrice()) {
		
			price_Weight(); //taxation per 20 grams
			regiontax(); // EUA tax
			External_transport(); //Airplane Transport Kerosone tax
			Internal_transport(); //Car Transport tax
			Double newvalue = value - this.getPrice();
			
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
			Double newvalue =value - this.getPrice();
			
			System.out.println("Your debt was payed successfully! \n new balance is:" + newvalue +"R$");
			
		}else {
			throw new IllegalArgumentException("ERROR: Tried normal payment on digital payment\n"
					+ "Or invalid balance");
	}
	}
	@Override
	public void Internal_transport() {
		
		Integer gasoline =4500 / 12;
		Double TotalGasoline = gasoline * 4.35;//gasoline EUA price converted to R$
		Double taxGasoline = TotalGasoline * 0.02 + this.getPrice();
		this.setPrice(taxGasoline);
		//Maximum amount of gasoline needed to cross EUA (with margin of error)
		//consumer gasoline tax is 1% (simulating taxation)
		//using real world values
	}
	@Override
	public void External_transport() {
		Double distance= 7700.0 / 1000;
		Double airplane_fuel_price = distance * 5.5;//5.5 tons of kerosene per 1000km
		Double ConversionToL = airplane_fuel_price * 1250;
		Double kerosone = ConversionToL * 4.0;
		Double tax_fuel = kerosone * 0.002 + this.getPrice();
		this.setPrice(tax_fuel);  ;
		// tax of value 0,2% of value for Kerosone fuel
		//simulating a real world example with real information
	}
	public Double getLocalcoin() {
		return localcoin;
	}
	public void setLocalcoin(Double localcoin) {
		this.localcoin = localcoin;
	}
	@Override
	public void localCoin() {
		this.setLocalcoin((this.getPrice() / 5));
		
	}

	@Override
	public String toString() {
		
		if(this.getDelivery_type() == true) {
			return "Owner: " + this.getUser() + "\n Product: " + this.getProduct_name() + "\n InitialPrice: " + this.getInitial_price() + " PLUS |Weight R$1 tax per (20g)|EUA tax 3%| \n FinalPrice: " + this.getPrice() +
					"\n On local coin: " + this.getLocalcoin() + "\n Weight: " + this.getWeight() + "\n Current State: " + this.getState() + "\n From brazil to " + this.getTocountry();
			}
		return "Owner: " + this.getUser() + "\n Product: " + this.getProduct_name() + "\n InitialPrice: " + this.getInitial_price() + " PLUS |0,2% Kerosone tax|Gasoline tax 2%|Weight R$1 tax per (20g)|EUA tax 3%| \n FinalPrice: " + this.getPrice()
		+ "\n On local coin: " + this.getLocalcoin() + "\n Weight: " + this.getWeight() + "\n Current State: " + this.getState() + "\n From brazil to: " + this.getTocountry();
		
	}}