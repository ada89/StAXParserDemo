package org.parser.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Room class - models a room
 * A room contains a name (string) 
 * and a list of rates
 * @author Adela B.
 */

public class Room {
	protected String room_name;
	public List<Rate> rates;
	
	public Room(List<Rate> rates, String room_name) {
		this.rates = new ArrayList<Rate>(rates);
		this.room_name = room_name;
	}

	public Room(Rate rate, String room_name) {
		//returns a list with one element
		this (Collections.singletonList(rate), room_name);
	}

	
	public void setRoomName(String room_name){
		this.room_name = room_name;
	}
	
	public String getRoomName() {
		return room_name;
	}
	
	public void setRates(List<Rate> rates){
		this.rates.clear();
		this.rates.addAll(rates);
	}
	
	public List<Rate> getRates() {
		//returns a read-only list
		return Collections.unmodifiableList(rates);
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
//		sb.append("room name " + room_name + '\n');
		sb.append(rates.toString());
		return sb.toString();
	}
	
}
