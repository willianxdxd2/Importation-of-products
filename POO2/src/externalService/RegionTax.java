package externalService;

import products.Product;

public interface RegionTax {

	public void regiontax();
	
	public void pay(Double value,Boolean delivery_type);	
	
	public void digitalpay(Double value,Boolean delivery_type, Integer token);
	
}
