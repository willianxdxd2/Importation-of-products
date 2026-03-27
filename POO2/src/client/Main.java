package client;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.ArrayList;

import Entities.Enum.productState;
import Entities.Enum.productToCountry;
import products.Pbrazil;
import products.Pchina;
import products.Peua;
import products.Pjapan;
import products.Product;

public class Main {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Welcome to unique showcase");
		System.out.println("How many products do you wanna put in?");
		int n = sc.nextInt();
		
		ArrayList<Product> prlist = new ArrayList<>();
		for (int i = 0 ; i < n; i++ ) {
			sc.nextLine();
			System.out.println("Enter the User: ");
			String user = sc.nextLine();
			System.out.println("Enter product Name: ");
			String productname = sc.nextLine();
			System.out.println("Enter product Price: (R$)");
			Double price = sc.nextDouble();
			System.out.println("Enter product Weight: (KGs)");
			Double weight = sc.nextDouble();
			sc.nextLine();
			System.out.println("Gonna use Digital payment? Yes/No");
			String r = sc.nextLine();
		
			Boolean delivery_type = null;
			if(r.equals("Yes")) {
			delivery_type = true;
		}else {
			delivery_type = false;
		}
			System.out.println("Current status of the product |(1) Arrived | (2) Requested | (3) Arriving | ");
			Integer rep = sc.nextInt();
		
			productState state;
			switch (rep) {
			
			case(1):
				state = productState.Arrived;
				break;
			case(2):
				state = productState.Requested;
				break;
			case(3):
				state = productState.Arriving;
				break;
			default:
				throw new IllegalArgumentException("Invalid Option ");
			}
			
			System.out.println("Insert country product destination |(1)Brazil | (2)EUA | (3)Japan | (4)China|");
			Integer crep = sc.nextInt();
			
			productToCountry country;
			switch(crep) {
			
			case(1):
				country = productToCountry.Brazil;
				Product Pbrazil= new Pbrazil(user,productname,price,weight,delivery_type,state,country);
				prlist.add(Pbrazil);
				break;
			case(2):
				country = productToCountry.EUA;
				Product Peua= new Peua(user,productname,price,weight,delivery_type,state,country);
				prlist.add(Peua);
				break;
			case(3):
				country = productToCountry.Japan;
				Product Pjapan = new Pjapan(user,productname,price,weight,delivery_type,state,country);
				prlist.add(Pjapan);
				break;
				
			case(4):
				country = productToCountry.China;
				Product Pchina = new Pchina(user,productname,price,weight,delivery_type,state,country);
				prlist.add(Pchina);
				break;
				
			}
				for(Product p : prlist) {
					if(p.getDelivery_type()) {
						
						
						System.out.println("Enter your money quantity");
						Double value = sc.nextDouble();
						
						System.out.println("Enter token for DigitalPayment: ");
						p.digitaltoken(p.getDelivery_type());
						int token = sc.nextInt();
						
						p.digitalpay(value, p.getDelivery_type(), token);
						
					}else {
						System.out.println("Enter your money quantity: ");
						
						Double value = sc.nextDouble();
						
						p.pay(value, p.getDelivery_type());
				}
				p.localCoin();
			
				System.out.println(p.toString());
				
			
			}
		
	}
	}
}
