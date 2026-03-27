package products;

import java.util.Objects;

import Entities.Enum.productState;
import Entities.Enum.productToCountry;
import externalService.ImportationExpenses;
import externalService.RegionTax;

public class Pjapan extends Product implements ImportationExpenses,RegionTax {

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
	public Pjapan(String user, String product_name, Double price, Double weight, Boolean delivery_type,
			productState state, productToCountry tocountry) {
		super(user, product_name, price, weight, delivery_type);
		this.state = state;
		this.tocountry = tocountry;
	}
	@Override
	public void regiontax() {
		Double regiontax=this.getPrice() * 0.05 + this.getPrice();
		this.setPrice(regiontax); 
	// Japan tax is 5% (simulating a real taxation)	
	}
	@Override
	public void pay(Double value,Boolean delivery_type) {
		if(this.getDelivery_type() == false && value >= this.getPrice()) {
		
			price_Weight(); //taxation per 20 grams
			regiontax(); //default Japan tax
			External_transport(); //Airplane Transport Kerosone tax
			Internal_transport(); //Car Transport tax
			Double newvalue = this.getPrice() - value;
			
			System.out.println("Your debt was payed successfully! \n new balance is:" + newvalue + "R$");
			localCoin();
			System.out.println("借金は無事に返済されました！\n ニューバランスは次のとおりです。 " + newvalue + "¥");
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
			Double newvalue = this.getPrice() - value;
			
			
			System.out.println("Your debt was payed successfully! \n new balance is:" + newvalue  +"R$");
			localCoin();
			System.out.println("借金は無事に返済されました！\n ニューバランスは次のとおりです。 " + newvalue + "¥");
			
		}else {
			throw new IllegalArgumentException("ERROR: Tried normal payment on digital payment\n"
					+ "Or invalid balance");
	}
	}
	@Override
	public void Internal_transport() {
		
		Integer gasoline =5500 / 12;
		Double TotalGasoline = gasoline * 5.70;//gasoline Japan price converted to R$
		Double taxGasoline = TotalGasoline * 0.02 + this.getPrice();
		this.setPrice(taxGasoline);
		//Maximum amount of gasoline needed to cross Japan (with margin of error)
		//consumer gasoline tax is 2% 
		//using real world values
	}
	@Override
	public void External_transport() {
		Double distance= 18500.0 / 1000;
		Double airplane_fuel_total = distance * 5.5;//5.5 tons of kerosene per 1000 km
		Double ConversionToL = airplane_fuel_total * 1250;
		Double kerosone_price = ConversionToL * 4.0;
		
		Double tax_fuel = kerosone_price * 0.001 + this.getPrice();
		this.setPrice(tax_fuel);  ;
		// tax of value 0,1% of value for Kerosone fuel
		//simulating a real world example with real information
	}
	@Override
	public void localCoin() {
		this.setLocalcoin(this.getPrice() / 0.033);;
		
	}
	public Double getLocalcoin() {
		return localcoin;
	}
	public void setLocalcoin(Double localcoin) {
		this.localcoin = localcoin;
	}
	@Override
	public String toString() {
		
		if(this.getDelivery_type() == true) {
			return "Owner: " + this.getUser() + "\n Product: " + this.getProduct_name() + "\n InitialPrice: " + this.getInitial_price() + "\n PLUS |Weight R$1 tax per (20g)|Japan tax 5%|FinalPrice: " + this.getPrice()
			+ "\n Weight: " + this.getWeight() + "\n Current State: " + this.getState() + "\n From brazil to" + this.getTocountry() +
			" \n所有者: " + this.getUser() +  "\n 製品：" + this.getProduct_name() + "\n 初期価格: " + this.getInitial_price() + "\n }プラス | 灯油税 0.1% | ガソリン税 2% | 重量税 1レアル/20g | 日本税 5% | 最終価格:" + this.getPrice() + "\n 重さ： " + this.getWeight()
			+ "\n 現在の状態: " + this.getState() + "\n ブラジルから: " + this.getTocountry();
		}
		
		
		return "Owner: " + this.getUser() + "\n Product: " + this.getProduct_name() + "\n InitialPrice: " + this.getInitial_price() + "\n PLUS |0,1% Kerosone tax|Gasoline tax 2%|Weight R$1 tax per (20g)|Japan tax 5%|FinalPrice: " + this.getPrice()
		+ "\n Weight: " + this.getWeight() + "\n Current State: " + this.getState() + "\n From brazil to" + this.getTocountry() +
		" \n 所有者: " + this.getUser() +  "\n 製品：" + this.getProduct_name() + "\n 初期価格: " + this.getInitial_price() + "\n プラス | 重量税 1レアル/20g | 日本税 5% | 最終価格:" + this.getPrice() + "\n 重さ： " + this.getWeight()
		+ "\n 現在の状態: " + this.getState() + " ブラジルから: " + this.getTocountry();
	}

	
}
