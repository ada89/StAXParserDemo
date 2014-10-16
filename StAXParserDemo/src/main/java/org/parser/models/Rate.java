package org.parser.models;

/**
 * Rate class - models a rate
 * A rate contains a name (string) 
 * and a Price object
 * @author Adela B.
 */

public class Rate {
	protected String rate_name; 
	public Price prices;
	protected boolean hasCreditCard;
	protected boolean isCancellable;
	protected boolean isPrivate;
	
	public Rate(Price prices, String rate_name){
		this.prices = prices;
		this.rate_name = rate_name;
	}

	public void setRateName(String rate_name) {
		this.rate_name = rate_name;
	}

	public String getRateName(){
		return rate_name;
	}

	public void setPrice(Price prices) {
		this.prices = prices;
	}

	public Price getPrice(){
		return prices;
	}

	public String toString(){
		StringBuffer sb = new StringBuffer();
//		sb.append("rate name " + rate_name + '\n');
		sb.append(prices.toString());
		return sb.toString();
	}
}
