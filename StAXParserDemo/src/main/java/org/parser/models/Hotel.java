package org.parser.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Hotel class - models a hotel
 * A hotel contains an id (string) 
 * and a list of rooms
 * @author Adela B.
 */

public class Hotel {

	protected String hotel_id;
	public List<Room> rooms;
	protected Price minimum;
	protected Price maximum;
	
	public Hotel(List<Room> rooms, String hotel_id) {
		this.rooms = new ArrayList<Room>(rooms);
		this.hotel_id = hotel_id;
	}

	public Hotel(Room room, String hotel_id) {
		this (Collections.singletonList(room), hotel_id);
	}


	public void setHotelId(String hotel_id){
		this.hotel_id = hotel_id;
	}

	public String getHotelId() {
		return hotel_id;
	}

	public void setRooms(List<Room> rooms){
		this.rooms.clear();
		this.rooms.addAll(rooms);
	}

	public List<Room> getRooms(){
		return Collections.unmodifiableList(rooms);
	}
	
	public void setMinimumPrice(Price minimum){
		this.minimum = minimum;
	}
	
	public void setMaximumPrice(Price maximum){
		this.maximum = maximum;
	}

	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("Hotel id " + hotel_id + '\n');
		sb.append("minimum\n" + minimum.toString() + '\n');
		sb.append("maximum\n" + maximum.toString() + '\n');
		return sb.toString();
	}
}
