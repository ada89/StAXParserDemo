package org.parser.models;

/**
 * Price class - models a price
 * 
 * @author Adela B.
 */

public class Price {
	public double price;
	public String currency;
	public String breakfast;

	public void setPrice(double price){
		this.price = price;
	}

	public double getPrice() {
		return price;
	}

	public void setCurrency(String currency){
		this.currency = currency;
	}

	public String getCurrency() {
		return currency;
	}

	public void setBreakfast(String breakfast) {
		this.breakfast = breakfast;
	}

	public String getBreakfast(){
		return breakfast;
	}

	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("price " + price + '\n');
		sb.append("currency " + currency + '\n');
		if(breakfast != null) {
			sb.append("breakfast " + checkBreakfast(breakfast) + '\n');
		} else {
			sb.append("breakfast not specified\n");
		}
		return sb.toString();
	}
	
	public String checkBreakfast(String breakfast) {

		switch(breakfast) {

		case "false": 
			return "no";

		case "true":
			return "yes";

		default:
			break;
		}

		return breakfast;
	}
}
