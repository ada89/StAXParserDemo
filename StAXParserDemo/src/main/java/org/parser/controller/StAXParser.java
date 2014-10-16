package org.parser.controller;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.parser.models.Hotel;
import org.parser.models.Price;
import org.parser.models.Rate;
import org.parser.models.Room;

/**
 * StAX parser - parses the XML file
 * @author Adela B.
 */

public class StAXParser {

	static final String HOTEL = "hotel";
	static final String HOTEL_ID = "id";
	static final String ROOM = "room";
	static final String NAME = "name";
	static final String RATE = "rate";
	static final String PRICE = "price";
	static final String TOTAL = "total";
	static final String CURRENCY = "currency";
	static final String BREAKFAST = "breakfast";

	/**
	 *
	 * <br />
	 * The method creates a new XMLInputFactory, sets up a 
	 * new eventReader and starts reading the XML document
	 * 
	 * @param xmlFile stream used for reading the XML file
	 * @return the final list of hotels parsed from the XML
	 */

	public List<Hotel> readFromXML(InputStream xmlFile)
			throws FileNotFoundException, XMLStreamException {
		//root component of the StAX parser
		XMLInputFactory factory = XMLInputFactory.newInstance();
		//cursor api - provides a cursor style API for parsing XML.
		XMLStreamReader reader = null;
		try {
			//create an XMLStreamReader
			reader = factory.createXMLStreamReader(xmlFile);
			return readDocument(reader);
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	/**
	 *
	 * <br />
	 * The method checks for availavilityResponse element and 
	 * if this exists it parses it
	 *
	 * @param reader stream used for reading the XML file
	 * @return the final list of hotels parsed from the XML
	 */

	private List<Hotel> readDocument(XMLStreamReader reader)
			throws XMLStreamException {

		while (reader.hasNext()) {

			//the next() call moves the cursor to the next "event" in the XML. 
			int eventType = reader.next();

			switch (eventType) {

			//
			case XMLStreamReader.START_ELEMENT:
				if (reader.getLocalName().
						equalsIgnoreCase("availabilityResponse")) {
					return readHotels(reader);
				}
				break;

			case XMLStreamReader.END_ELEMENT:
				break;
			}
		}
		throw new XMLStreamException("Premature end of file");
	}

	/**
	 *
	 * <br />
	 * The method checks for hotel element and if this 
	 * exist it adds it to the list of hotels
	 *
	 * @param reader stream used for reading the XML file
	 * @return the final list of hotels parsed from the XML
	 */

	private List<Hotel> readHotels(XMLStreamReader reader)
			throws XMLStreamException {

		List<Hotel> hotelList = new ArrayList<Hotel>();

		while (reader.hasNext()) {

			int eventType = reader.next();

			switch (eventType) {

			case XMLStreamReader.START_ELEMENT:
				if (reader.getLocalName().
						equalsIgnoreCase(HOTEL)) {
					hotelList.add(readHotel(reader));
				}
				break;

			case XMLStreamReader.END_ELEMENT:
				return hotelList;
			}
		}
		throw new XMLStreamException("Premature end of file");
	}

	/**
	 *
	 * <br />
	 * The method sets the id (attribute of the hotel element) 
	 * for a hotel, checks for room element and 
	 * if this exists sets it as room for the hotel
	 *
	 * @param reader stream used for reading the XML file
	 * @return a hotel object containing only minimum and 
	 * 		   maxmimum price
	 */

	private Hotel readHotel(XMLStreamReader reader) 
			throws XMLStreamException {

		ArrayList<Room> rooms = new ArrayList<Room>();
		Hotel hotel = new Hotel(rooms, "");
		hotel.setHotelId(reader.getAttributeValue(null, HOTEL_ID));

		while (reader.hasNext()) {

			int eventType = reader.next();

			switch (eventType) {

			case XMLStreamReader.START_ELEMENT:
				if (reader.getLocalName().equalsIgnoreCase(ROOM)) {
					hotel.setRooms(readRooms(reader));
				}

			case XMLStreamReader.END_ELEMENT:
				if (reader.getLocalName().equalsIgnoreCase(HOTEL)){
					setMaximumPrice(hotel);
					setMinimumPrice(hotel);
					return hotel;
				}
			}
		}
		throw new XMLStreamException("Premature end of file");
	}

	/**
	 *
	 * <br />
	 * The method checks for room element and if this exists
	 * it adds it to the rooms list
	 *
	 * @param reader stream used for reading the XML file
	 * @return the list of rooms for a hotel 
	 */

	private List<Room> readRooms(XMLStreamReader reader)
			throws XMLStreamException {

		List<Room> rooms = new ArrayList<Room>();
		//adds only the first room  in the hotel 
		rooms.add(readRoom(reader));
		
		while (reader.hasNext()) {

			int eventType = reader.next();

			switch (eventType) {

			case XMLStreamConstants.START_ELEMENT:
				if (reader.getLocalName().
						equalsIgnoreCase(ROOM))
					rooms.add(readRoom(reader));

			case XMLStreamConstants.END_ELEMENT:
				if (reader.getLocalName().
						equalsIgnoreCase(HOTEL))
					return rooms;
			}
		}
		throw new XMLStreamException("Premature end of file");
	}

	/**
	 *
	 * <br />
	 * The method sets the name (attribute of the room element)
	 * of the room, checks for rate element and if this exists
	 * sets it as rate for the room
	 *
	 * @param reader stream used for reading the XML file
	 * @return a room object 
	 */

	private Room readRoom(XMLStreamReader reader) 
			throws XMLStreamException {

		ArrayList<Rate> rates = new ArrayList<Rate>();
		Room room = new Room(rates, "");
		room.setRoomName(reader.getAttributeValue(null, NAME));

		while (reader.hasNext()) {

			int eventType = reader.next();

			switch (eventType) {

			case XMLStreamConstants.START_ELEMENT:
				if (reader.getLocalName().
						equalsIgnoreCase(RATE)) {
					room.setRates(readRates(reader));
				}

			case XMLStreamConstants.END_ELEMENT:
				if (reader.getLocalName().
						equalsIgnoreCase(ROOM))
					return room;
			}
		}
		throw new XMLStreamException("Premature end of file");
	}

	/**
	 *
	 * <br />
	 * The method checks for rate element and if this exists
	 * it adds it to the rates list
	 *
	 * @param reader stream used for reading the XML file
	 * @return the list of rates for a room of a hotel 
	 */

	private List<Rate> readRates(XMLStreamReader reader)
			throws XMLStreamException {

		List<Rate> rates = new ArrayList<Rate>();
		//adds only the first rate in the room
		rates.add(readRate(reader));

		while (reader.hasNext()) {

			int eventType = reader.next();

			switch (eventType) {
			case XMLStreamConstants.START_ELEMENT:
				if (reader.getLocalName().
						equalsIgnoreCase(RATE)) {
					rates.add(readRate(reader));
				}

			case XMLStreamConstants.END_ELEMENT:
				if(reader.getLocalName().
						equalsIgnoreCase(ROOM)){
					return rates;
				}
			}
		}
		throw new XMLStreamException("Premature end of file");
	}


	/**
	 *
	 * <br />
	 * The method sets the name (attribute of the rate element)
	 * of the rate, checks for price element and if this exists
	 * sets it as price of the rate
	 *
	 * @param reader stream used for reading the XML file
	 * @return a rate object 
	 */

	private Rate readRate(XMLStreamReader reader) 
			throws XMLStreamException {

		Price price = new Price();
		Rate rate = new Rate(price, "");
		rate.setRateName(reader.getAttributeValue(null, NAME));

		while (reader.hasNext()) {

			int eventType = reader.next();

			switch (eventType) {

			case XMLStreamConstants.START_ELEMENT:

				if (reader.getLocalName().
						equalsIgnoreCase(PRICE)) {
					rate.setPrice(readPrice(reader));
				}

			case XMLStreamConstants.END_ELEMENT:
				if (reader.getLocalName().
						equalsIgnoreCase(RATE))
					return rate;
			}
		}
		throw new XMLStreamException("Premature end of file");
	}

	/**
	 *
	 * <br />
	 * The method sets the price, breakfast and currency 
	 * for the price object
	 *
	 * @param reader stream used for reading the XML file
	 * @return a price object 
	 */

	private Price readPrice(XMLStreamReader reader) 
			throws XMLStreamException {

		Price price = new Price();
		price.setPrice(Double.valueOf(reader.getAttributeValue(null, TOTAL)));

		while (reader.hasNext()) {

			int eventType = reader.next();
			switch (eventType) {

			case XMLStreamReader.START_ELEMENT:
				if(reader.getLocalName().
						equalsIgnoreCase(BREAKFAST))  {
					price.setBreakfast(reader.getAttributeValue(null, "included"));
				}
				if(reader.getLocalName().
						equalsIgnoreCase(CURRENCY)) {
					price.setCurrency(reader.getElementText());
				}

			case XMLStreamConstants.END_ELEMENT:
				if (reader.getLocalName().
						equals(PRICE)){
					return price;
				}

			}
		}
		throw new XMLStreamException("Premature end of file");
	}

	/**
	 *
	 * <br />
	 * The method sets the maximum price for a hotel
	 *
	 * @param hotel for which we set the maximum price
	 */

	public void setMaximumPrice(Hotel hotel){
		Price maximum = new Price();
		double maximumPrice = Double.MIN_VALUE;
		String currency = null;
		String breakfast = null;
		for(Room room : hotel.rooms){
			for(Rate rate: room.rates){
				if(rate.prices.price > maximumPrice){
					maximumPrice = rate.prices.price;
					currency = rate.prices.currency;
					breakfast = rate.prices.breakfast;
				}
			}
		}
		maximum.price = maximumPrice;
		maximum.breakfast = breakfast;
		maximum.currency = currency;
		hotel.setMaximumPrice(maximum);

	} 

	/**
	 * 
	 * <br />
	 * The method sets the minimum price for a hotel
	 *
	 * @param hotel for which we set the minimum price
	 */

	public void setMinimumPrice(Hotel hotel){
		Price minimum = new Price();
		double minimumPrice = Double.MAX_VALUE;
		String currency = null;
		String breakfast = null;
		for(Room room : hotel.rooms){
			for(Rate rate: room.rates){
				if(rate.prices.price < minimumPrice){
					minimumPrice = rate.prices.price;
					currency = rate.prices.currency;
					breakfast = rate.prices.breakfast;
				}
			}
		}
		minimum.breakfast = breakfast;
		minimum.currency = currency;
		minimum.price = minimumPrice;
		hotel.setMinimumPrice(minimum);
	}
}