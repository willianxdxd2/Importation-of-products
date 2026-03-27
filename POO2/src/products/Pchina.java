package products;

import java.util.Objects;

import Entities.Enum.productState;
import Entities.Enum.productToCountry;
import externalService.ImportationExpenses;
import externalService.RegionTax;

public class Pchina extends Product implements ImportationExpenses,RegionTax{

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
	private productState state;
	private productToCountry tocountry;
	
	
	public void regiontax() {
		Double regiontax=this.getPrice() * 0.04 + this.getPrice();
		this.setPrice(regiontax); 
	// China tax is 4% (simulating a real taxation)	
	}
	@Override
	public void pay(Double value,Boolean delivery_type) {
		if(this.getDelivery_type() == false && value >= this.getPrice()) {
		
			price_Weight(); //taxation per 20 grams
			regiontax(); //China tax
			External_transport(); //Airplane Transport Kerosone tax
			Internal_transport(); //Car Transport tax
			Double newvalue =this.getPrice()- value;
			
			System.out.println("Your debt was payed successfully! \n new balance is:" + newvalue + "CN¥");
			localCoin();
			System.out.println("您的债务已成功还清！\n 您的新余额为: " + newvalue + "CN¥（已转换）");
			
			
		}else {
			throw new IllegalArgumentException("ERROR:Tried digital payment on normal payment \n"
					+ "Or invalid balance");
		}}
	@Override
	public void digitalpay(Double value,Boolean delivery_type,Integer token) {
		if(this.getDelivery_type() == true && value >= this.getPrice() && Objects.equals(this.getToken(), token)) {
			//using default taxation for digital service
			regiontax();
			price_Weight();
			Double newvalue = this.getPrice()- value;
			
			System.out.println("Your debt was payed successfully! \n new balance is:" + newvalue  +"CN¥(Already converted)");
			System.out.println("您的债务已成功还清！\n 您的新余额为: " + newvalue + "CN¥（已转换）");
			
		}else {
			throw new IllegalArgumentException("ERROR: Tried normal payment on digital payment\n"
					+ "Or invalid balance");
	}
	}
	@Override
	public void Internal_transport() {
		
		Integer gasoline =5200 / 12;
		Double TotalGasoline = gasoline * 5.0;//gasoline China price converted to R$
		Double taxGasoline = TotalGasoline * 0.06 + this.getPrice();
		this.setPrice(taxGasoline);
		//Maximum amount of gasoline needed to cross China (with margin of error)
		//consumer gasoline tax is 4% (simulating taxation)
		//using real world values
	}
	@Override
	public void External_transport() {
		Double distance= 18000.0 / 1000;
		Double airplane_fuel_price = distance * 5.5;//5.5 tons of kerosene per 1000km
		Double ConversionToL = airplane_fuel_price * 1250;
		Double kerosone = ConversionToL * 4.5;
		Double tax_fuel = kerosone * 0.0025 + this.getPrice();
		this.setPrice(tax_fuel);  ;
		// tax of value 0,2% of value for Kerosone fuel
		//simulating a real world example with real information
	}
	@Override
	public void localCoin() {
		this.setPrice(this.getPrice() / 0.70);
		
	}
	@Override
	public String toString() {


		if(this.getDelivery_type() == true) {
			return "Owner: " + this.getUser() + "\n Product: " + this.getProduct_name() + "\n InitialPrice: " + this.getInitial_price() + "\n PLUS |Weight R$1 tax per (20g)|China tax 5%|FinalPrice: " + this.getPrice()
			
			+ "\n Weight: " + this.getWeight() + "\n Current State: " + this.getState() + "\n From brazil to" + this.getTocountry() +
			" \n 所有者: " + this.getUser() +  "\n 产品：" + this.getProduct_name() + "\n 初始价格 : " + this.getInitial_price() + "\n }加 | 煤油税 0.25% | 汽油税 6% | 重量税：1雷亚尔/20克 | 日本税率4% | 最终价格：" + this.getPrice() + "\n 重量： " + this.getWeight()
			+ "\n 目前状态： " + this.getState() + "\n 来自巴西: " + this.getTocountry();
		}
		return "Owner: " + this.getUser() + " Product: " + this.getProduct_name() + "InitialPrice: " + this.getInitial_price() + "PLUS |0,25% Kerosone tax|Gasoline tax 6%|Weight R$1 tax per (20g)|China tax 4%|FinalPrice: " + this.getPrice()
		+ " Weight: " + this.getWeight() + " Current State: " + this.getState() + " From brazil to" + this.getTocountry() +
		" \n所有者: " + this.getUser() +  "\n 产品：" + this.getProduct_name() + "\n 初始价格: " + this.getInitial_price() + "\n 增量 | 0.25% 煤油税 | 汽油税 6% | 每20克含税1雷亚尔 | 中国税率4% |最终价格：" + this.getPrice() + "\n 重量： " + this.getWeight()
		+ "\n 当前状态: " + this.getState() + "\n 从巴西到： " + this.getTocountry();
	}
	public Pchina(String user, String product_name, Double price, Double weight, Boolean delivery_type,
			productState state, productToCountry tocountry) {
		super(user, product_name, price, weight, delivery_type);
		this.state = state;
		this.tocountry = tocountry;
	}
}
