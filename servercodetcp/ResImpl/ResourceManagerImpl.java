// -------------------------------
// adapated from Kevin T. Manley
// CSE 593
//
package ResImpl;

import ResInterface.*;

import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;

//public class ResourceManagerImpl extends java.rmi.server.UnicastRemoteObject
/**
 * Processing Thread class that processes requests from a client given a connection 
 * from the request handler.
 *
 */
public class ResourceManagerImpl extends Thread
	implements ResourceManager  {
	
	protected RMHashtable m_itemHT = null;
	ResourceManager rm_middle = null;
	
	//variables when middleware connect to any RM
	Socket socket = null;
	PrintWriter out = null;
	BufferedReader in = null;
	
	//middlewareinfo
	String server = "localhost";
	int port = 2012;
	
	/**
	 * Socket for the client connection.
	 */
	private final Socket mSocket;
	 
	/**
	 * Constructor for the processing thread.
	 * @param socket - The Socket for the client connection.
	 * @param manager - The music manager of the system.
	 */
	public ResourceManagerImpl(Socket socket, RMHashtable data) throws RemoteException {
		m_itemHT = data;
		mSocket = socket;
	}
	
	public void setMiddlewareInfo(String address, int port)
	{
		this.server = address;
		this.port = port;
	}
	
	@Override
	public void run(){
		//Input and output streams
		BufferedReader in;
		PrintWriter out;
		System.out.println("RM running");
		//Request object
		//RequestJson request;
		//Gson gson = new Gson();
		String request;
		try {
			//No longer connected, move on
			if(!mSocket.isConnected())
				return;
			
			//Create input and output streams
			 in = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
			 out = new PrintWriter(mSocket.getOutputStream(), true);
			 
			 //read in request
			 //request = gson.fromJson(in.readLine(), RequestJson.class);
			 request = in.readLine();
			 System.out.println("REQUEST : " + request);
			 
			 //parse request and get reply
			 //ResponseJson reply = parseRequest(request);
			 //String stringReply = gson.toJson((reply));
			 Object stringReply = parseRequest(request).toString();
			 System.out.println("REPLY : " + stringReply);
			 
			 //if there is a reply, send it back
			 //if(reply != null)
			 out.println(stringReply);
			 
			 //close streams and connection
			 in.close();
			 out.close();
			 mSocket.close();
		} catch (Exception e) {
			System.err.println("Processing Thread: Processing Error - exiting");
			e.printStackTrace();
			return;
		}
	}
	
	int parseInt(String s)
	{
		return Integer.parseInt(s);
	}
	
	private String send(String methodName, Object[] args)
	{
		try {
			
			//connect and send feedback
	        socket = new Socket(server,port);
	        System.out.println("RM Client connected to middleware");
	        out = new PrintWriter(socket.getOutputStream(), true);
	        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        
	        System.out.println("RM sending to middleware: "+methodName);

	        String req = methodName +",";
	        for (int i = 0; i < args.length; i++) {
				req += args[i].toString() + ",";
			}
	        System.out.println("RM client sending : "+req);
	        out.println(req);
	        String reply = in.readLine();
	        
			socket.close();
			out.close();
		    in.close();
		    return reply;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
		return null;
	}
	
	/**
	 * Method to parse the request and call the appropriate methods to execute.
	 * @param request The request sent by the client.
	 * @return The response to the client if there is one.
	 */
	private Object parseRequest(String request){
		
		Object response = null;
		String[] req = request.split(",");
		int choice = this.findChoice(req[0]);
		System.out.println("Inside RM, looking for method " + req[0] +"choice:"+choice);
		//decide which of the commands this was
				switch(choice){
				case 1: //customerExist
					try 
					{
						response = this.customerExist(parseInt(req[1]), parseInt(req[2]));
					} catch (RemoteException e) {
						System.out.println("Middleware issue checking if customer exist" + req[2]);
						e.printStackTrace();
					}
				    break;
				    
				case 2:  //new flight
				    try {
						response = this.addFlight(parseInt(req[1]), parseInt(req[2]), parseInt(req[3]), parseInt(req[4]));
					} catch (RemoteException e) {
						System.out.println("Connection issue with addFlight");
						e.printStackTrace();
					}
				    break;
				    
				case 3:  //new Car
				    try {
						response = this.addCars(parseInt(req[1]), req[2].toString(), parseInt(req[3]), parseInt(req[4]));
					} catch (RemoteException e) {
						System.out.println("Connection issue with addCar");
						e.printStackTrace();
					}
				    break;
				    
				case 4:  //new Room
				    try {
						response = this.addRooms(parseInt(req[1]), req[2].toString(), parseInt(req[3]), parseInt(req[4]));
					} catch (RemoteException e) {
						System.out.println("Connection issue with addRoom");
						e.printStackTrace();
					}
				    break;
				    
				case 5:  //new Customer
					try 
					{
						response = this.newCustomer(parseInt(req[1]));
					} catch (RemoteException e) {
						System.out.println("Middleware issue creating customer given id" + req[1]);
						e.printStackTrace();
					}
				    break;
				    
				case 6: //delete Flight
				    try {
						response = this.deleteFlight(parseInt(req[1]), parseInt(req[2]));
					} catch (RemoteException e) {
						System.out.println("Connection issue with deleteFlight");
						e.printStackTrace();
					}
				    break;
				    
				case 7: //delete Car
				    try {
						response = this.deleteCars(parseInt(req[1]),req[2].toString());
					} catch (RemoteException e) {
						System.out.println("Connection issue with deleteCars");
						e.printStackTrace();
					}
				    break;
				    
				case 8: //delete Room
				    try {
						response = this.deleteRooms(parseInt(req[1]),req[2].toString());
					} catch (RemoteException e) {
						System.out.println("Connection issue with deleteRooms");
						e.printStackTrace();
					}
				    break;
				    
				case 9: //delete Customer
				     try {
						response = this.deleteCustomer(parseInt(req[1]), parseInt(req[2]));
					} catch (RemoteException e) {
						System.out.println("Connection issue with deleteCustomer");
						e.printStackTrace();
					}
				    break;
				    
				case 10: //querying a flight
				    try {
						response = this.queryFlight(parseInt(req[1]), parseInt(req[2]));
					} catch (RemoteException e) {
						System.out.println("Connection issue with queryFlight");
						e.printStackTrace();
					}
				    break;
				    
				case 11: //querying a Car Location
				    try {
						response = this.queryCars(parseInt(req[1]),req[2].toString());
					} catch (RemoteException e) {
						System.out.println("Connection issue with queryCars");
						e.printStackTrace();
					}
				    break;
				    
				case 12: //querying a Room location
				    try {
						response = this.queryRooms(parseInt(req[1]),req[2].toString());
					} catch (RemoteException e) {
						System.out.println("Connection issue with queryRooms");
						e.printStackTrace();
					}
				    break;
				    
				case 13: //querying Customer Information
				    try {
						response = this.queryCustomerInfo(parseInt(req[1]), parseInt(req[2]));
					} catch (RemoteException e) {
						System.out.println("Connection issue with queryCustomerInfo");
						e.printStackTrace();
					}
				    break;		       
				    
				case 14: //querying a flight Price
				    try {
						response = this.queryFlightPrice(parseInt(req[1]), parseInt(req[2]));
					} catch (RemoteException e) {
						System.out.println("Connection issue with queryFlightPrice");
						e.printStackTrace();
					}
				    break;
				    
				case 15: //querying a Car Price
				    try {
						response = this.queryCarsPrice(parseInt(req[1]),req[2].toString());
					} catch (RemoteException e) {
						System.out.println("Connection issue with queryCarsPrice");
						e.printStackTrace();
					}	    
				    break;

				case 16: //querying a Room price
				    try {
						response = this.queryRoomsPrice(parseInt(req[1]),req[2].toString());
					} catch (RemoteException e) {
						System.out.println("Connection issue with queryRoomsPrice");
						e.printStackTrace();
					}
				    break;
				    
				case 17:  //reserve a flight
					try {
						response = this.reserveFlight(parseInt(req[1]), parseInt(req[2]), parseInt(req[3]));
					} catch (RemoteException e) {
						System.out.println("Connection issue with reserveFlight");
						e.printStackTrace();
					}
				    break;
				    
				case 18:  //reserve a car
				    try {
						response = this.reserveCar(parseInt(req[1]), parseInt(req[2]), req[3].toString());
					} catch (RemoteException e) {
						System.out.println("Connection issue with reserveCar");
						e.printStackTrace();
					}
				    break;
				    
				case 19:  //reserve a room
				    try {
						response = this.reserveRoom(parseInt(req[1]), parseInt(req[2]), req[3].toString());
					} catch (RemoteException e) {
						System.out.println("Connection issue with reserveRoom");
						e.printStackTrace();
					}
				    break;
				    
				case 20:  //reserve an Itinerary
				    try {
						String[] flights = Arrays.copyOfRange(req, 3, (req.length-3));
						String vols = "";
						for(int i=0; i < flights.length; i++){
							if( i == (flights.length-1) )
								vols += flights[i];
							else
								vols += flights[i] + ",";
						}
						response = this.itinerary(parseInt(req[1]), parseInt(req[2]), parse(vols), req[req.length-3].toString(), Boolean.parseBoolean(req[req.length-2]), Boolean.parseBoolean(req[req.length-1]));
						/*String[] flights = Arrays.copyOfRange(req, 3, (req.length-3));
						List l = Arrays.asList(flights);
						Vector v = new Vector(l);
						response = this.itinerary(parseInt(req[1]), parseInt(req[2]), v, req[4].toString(), Boolean.parseBoolean(req[5]), Boolean.parseBoolean(req[6]));*/
					} catch (RemoteException e) {
						System.out.println("Connection issue with itinerary");
						e.printStackTrace();
					}
				    break;
				    		    
				case 21:  //customerReserveWrite
					try 
					{
						response = this.customerReserveWrite(parseInt(req[1]), req[2].toString(),req[3].toString(),parseInt(req[4]),parseInt(req[5]));
					} catch (RemoteException e) {
						System.out.println("Middleware issue checking if customer exist" + req[2]);
						e.printStackTrace();
					}
					break;				    
				    
				case 22:  //new Customer given id
					try 
					{
						response = this.newCustomer(parseInt(req[1]),parseInt(req[2]));
					} catch (RemoteException e) {
						System.out.println("Middleware issue creating customer given id" + req[2]);
						e.printStackTrace();
					}
				    break;
				case 23: //dump
					try 
					{
						this.dump(req[1].toString());
					} catch (RemoteException e) {

						e.printStackTrace();
					}
				    break;
				default:
				    System.out.println("The interface does not support this command.");
				    break;
				}//end of switch
		
		return response;
	}
	 
	public ResourceManager getMiddleRM()
	{
		
		return this.rm_middle;
	}
	 
	// Reads a data item
	private RMItem readData( int id, String key )
	{	
		synchronized(m_itemHT){
			return (RMItem) m_itemHT.get(key);
		}
	}

	// Writes a data item
	private void writeData( int id, String key, RMItem value )
	{
		synchronized(m_itemHT){
			m_itemHT.put(key, value);
		}
	}
	
	// Remove the item out of storage
	protected RMItem removeData(int id, String key){
		synchronized(m_itemHT){
			return (RMItem)m_itemHT.remove(key);
		}
	}
	
	
	// deletes the entire item
	protected boolean deleteItem(int id, String key)
	{
		Trace.info("RM::deleteItem(" + id + ", " + key + ") called" );
		ReservableItem curObj = (ReservableItem) readData( id, key );
		// Check if there is such an item in the storage
		if( curObj == null ) {
			Trace.warn("RM::deleteItem(" + id + ", " + key + ") failed--item doesn't exist" );
			return false;
		} else {
			if(curObj.getReserved()==0){
				removeData(id, curObj.getKey());
				Trace.info("RM::deleteItem(" + id + ", " + key + ") item deleted" );
				return true;
			}
			else{
				Trace.info("RM::deleteItem(" + id + ", " + key + ") item can't be deleted because some customers reserved it" );
				return false;
			}
		} // if
	}
	

	// query the number of available seats/rooms/cars
	protected int queryNum(int id, String key) {
		Trace.info("RM::queryNum(" + id + ", " + key + ") called" );
		ReservableItem curObj = (ReservableItem) readData( id, key);
		int value = 0;  
		if( curObj != null ) {
			value = curObj.getCount();
		} // else
		Trace.info("RM::queryNum(" + id + ", " + key + ") returns count=" + value);
		return value;
	}	
	
	// query the price of an item
	protected int queryPrice(int id, String key){
		Trace.info("RM::queryCarsPrice(" + id + ", " + key + ") called" );
		ReservableItem curObj = (ReservableItem) readData( id, key);
		int value = 0; 
		if( curObj != null ) {
			value = curObj.getPrice();
		} // else
		Trace.info("RM::queryCarsPrice(" + id + ", " + key + ") returns cost=$" + value );
		return value;		
	}
	
	// reserve an item
	protected boolean reserveItem(int id, int customerID, String key, String location){
		Trace.info("RM::reserveItem( " + id + ", customer=" + customerID + ", " +key+ ", "+location+" ) called" );		
		// Read customer object if it exists (and read lock it)
		String cust_id = Customer.getKey(customerID);
		//Customer cust = (Customer) readData( id, Customer.getKey(customerID) );		
		boolean exist;
		try
		{
			Object[] p = {id, customerID};
			String response = send("customerExist",p);
			exist = Boolean.parseBoolean(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Trace.warn("RM::reserveItem->customerExist( " + id + ", " + customerID + ")  failed" );
			return false;
		}
		if( !exist ) {
			Trace.warn("RM::reserveCar( " + id + ", " + customerID + ", " + key + ", "+location+")  failed--customer doesn't exist" );
			return false;
		} 
		
		// check if the item is available
		ReservableItem item = (ReservableItem)readData(id, key);
		if(item==null){
			Trace.warn("RM::reserveItem( " + id + ", " + customerID + ", " + key+", " +location+") failed--item doesn't exist" );
			return false;
		}else if(item.getCount()==0){
			Trace.warn("RM::reserveItem( " + id + ", " + customerID + ", " + key+", " + location+") failed--No more items" );
			return false;
		}else{
			
			try {
				Object[] q = {id, key, location, item.getPrice(), customerID};
				send("customerReserveWrite",q);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Trace.warn("RM::reserveItem->customerReserveAndWrite( " + id + ", " + customerID + ", " + key + ", "+location+")  failed" );
				return false;
			}
			//Customer cust = (Customer) readData( id, Customer.getKey(customerID) );	
			//cust.reserve( key, location, item.getPrice());		
			//writeData( id, cust.getKey(), cust );
			
			// decrease the number of available items in the storage
			item.setCount(item.getCount() - 1);
			item.setReserved(item.getReserved()+1);
			
			Trace.info("RM::reserveItem( " + id + ", " + customerID + ", " + key + ", " +location+") succeeded" );
			return true;
		}		
	}
	
	// Create a new flight, or add seats to existing flight
	//  NOTE: if flightPrice <= 0 and the flight already exists, it maintains its current price
	public boolean addFlight(int id, int flightNum, int flightSeats, int flightPrice)
		throws RemoteException
	{
		Trace.info("RM::addFlight(" + id + ", " + flightNum + ", $" + flightPrice + ", " + flightSeats + ") called" );
		Flight curObj = (Flight) readData( id, Flight.getKey(flightNum) );
		if( curObj == null ) {
			// doesn't exist...add it
			Flight newObj = new Flight( flightNum, flightSeats, flightPrice );
			writeData( id, newObj.getKey(), newObj );
			Trace.info("RM::addFlight(" + id + ") created new flight " + flightNum + ", seats=" +
					flightSeats + ", price=$" + flightPrice );
		} else {
			// add seats to existing flight and update the price...
			curObj.setCount( curObj.getCount() + flightSeats );
			if( flightPrice > 0 ) {
				curObj.setPrice( flightPrice );
			} // if
			writeData( id, curObj.getKey(), curObj );
			Trace.info("RM::addFlight(" + id + ") modified existing flight " + flightNum + ", seats=" + curObj.getCount() + ", price=$" + flightPrice );
		} // else
		return(true);
	}


	
	public boolean deleteFlight(int id, int flightNum)
		throws RemoteException
	{
		return deleteItem(id, Flight.getKey(flightNum));
	}



	// Create a new room location or add rooms to an existing location
	//  NOTE: if price <= 0 and the room location already exists, it maintains its current price
	public boolean addRooms(int id, String location, int count, int price)
		throws RemoteException
	{
		System.out.println("addRooms on RM");
		Trace.info("RM::addRooms(" + id + ", " + location + ", " + count + ", $" + price + ") called" );
		Hotel curObj = (Hotel) readData( id, Hotel.getKey(location) );
		if( curObj == null ) {
			// doesn't exist...add it
			Hotel newObj = new Hotel( location, count, price );
			writeData( id, newObj.getKey(), newObj );
			Trace.info("RM::addRooms(" + id + ") created new room location " + location + ", count=" + count + ", price=$" + price );
		} else {
			// add count to existing object and update price...
			curObj.setCount( curObj.getCount() + count );
			if( price > 0 ) {
				curObj.setPrice( price );
			} // if
			writeData( id, curObj.getKey(), curObj );
			Trace.info("RM::addRooms(" + id + ") modified existing location " + location + ", count=" + curObj.getCount() + ", price=$" + price );
		} // else
		return(true);
	}

	// Delete rooms from a location
	public boolean deleteRooms(int id, String location)
		throws RemoteException
	{
		return deleteItem(id, Hotel.getKey(location));
		
	}

	// Create a new car location or add cars to an existing location
	//  NOTE: if price <= 0 and the location already exists, it maintains its current price
	public boolean addCars(int id, String location, int count, int price)
		throws RemoteException
	{
		Trace.info("RM::addCars(" + id + ", " + location + ", " + count + ", $" + price + ") called" );
		Car curObj = (Car) readData( id, Car.getKey(location) );
		if( curObj == null ) {
			// car location doesn't exist...add it
			Car newObj = new Car( location, count, price );
			writeData( id, newObj.getKey(), newObj );
			Trace.info("RM::addCars(" + id + ") created new location " + location + ", count=" + count + ", price=$" + price );
		} else {
			// add count to existing car location and update price...
			curObj.setCount( curObj.getCount() + count );
			if( price > 0 ) {
				curObj.setPrice( price );
			} // if
			writeData( id, curObj.getKey(), curObj );
			Trace.info("RM::addCars(" + id + ") modified existing location " + location + ", count=" + curObj.getCount() + ", price=$" + price );
		} // else
		return(true);
	}


	// Delete cars from a location
	public boolean deleteCars(int id, String location)
		throws RemoteException
	{
		return deleteItem(id, Car.getKey(location));
	}



	// Returns the number of empty seats on this flight
	public int queryFlight(int id, int flightNum)
		throws RemoteException
	{
		return queryNum(id, Flight.getKey(flightNum));
	}

	// Returns the number of reservations for this flight. 
//	public int queryFlightReservations(int id, int flightNum)
//		throws RemoteException
//	{
//		Trace.info("RM::queryFlightReservations(" + id + ", #" + flightNum + ") called" );
//		RMInteger numReservations = (RMInteger) readData( id, Flight.getNumReservationsKey(flightNum) );
//		if( numReservations == null ) {
//			numReservations = new RMInteger(0);
//		} // if
//		Trace.info("RM::queryFlightReservations(" + id + ", #" + flightNum + ") returns " + numReservations );
//		return numReservations.getValue();
//	}


	// Returns price of this flight
	public int queryFlightPrice(int id, int flightNum )
		throws RemoteException
	{
		return queryPrice(id, Flight.getKey(flightNum));
	}


	// Returns the number of rooms available at a location
	public int queryRooms(int id, String location)
		throws RemoteException
	{
		return queryNum(id, Hotel.getKey(location));
	}


	
	
	// Returns room price at this location
	public int queryRoomsPrice(int id, String location)
		throws RemoteException
	{
		return queryPrice(id, Hotel.getKey(location));
	}


	// Returns the number of cars available at a location
	public int queryCars(int id, String location)
		throws RemoteException
	{
		return queryNum(id, Car.getKey(location));
	}


	// Returns price of cars at this location
	public int queryCarsPrice(int id, String location)
		throws RemoteException
	{
		return queryPrice(id, Car.getKey(location));
	}

	// Returns data structure containing customer reservation info. Returns null if the
	//  customer doesn't exist. Returns empty RMHashtable if customer exists but has no
	//  reservations.
	public RMHashtable getCustomerReservations(int id, int customerID)
		throws RemoteException
	{
		Trace.info("RM::getCustomerReservations(" + id + ", " + customerID + ") called" );
		Customer cust = (Customer) readData( id, Customer.getKey(customerID) );
		if( cust == null ) {
			Trace.warn("RM::getCustomerReservations failed(" + id + ", " + customerID + ") failed--customer doesn't exist" );
			return null;
		} else {
			return cust.getReservations();
		} // if
	}

	// return a bill
	public String queryCustomerInfo(int id, int customerID)
		throws RemoteException
	{
		Trace.info("RM::queryCustomerInfo(" + id + ", " + customerID + ") called" );
		Customer cust = (Customer) readData( id, Customer.getKey(customerID) );
		if( cust == null ) {
			Trace.warn("RM::queryCustomerInfo(" + id + ", " + customerID + ") failed--customer doesn't exist" );
			return "";   // NOTE: don't change this--WC counts on this value indicating a customer does not exist...
		} else {
				String s = cust.printBill();
				Trace.info("RM::queryCustomerInfo(" + id + ", " + customerID + "), bill follows..." );
				System.out.println( s );
				return s;
		} // if
	}

  // customer functions
  // new customer just returns a unique customer identifier
	
  public int newCustomer(int id)
		throws RemoteException
	{
		Trace.info("INFO: RM::newCustomer(" + id + ") called" );
		// Generate a globally unique ID for the new customer
		int cid = Integer.parseInt( String.valueOf(id) +
								String.valueOf(Calendar.getInstance().get(Calendar.MILLISECOND)) +
								String.valueOf( Math.round( Math.random() * 100 + 1 )));
		Customer cust = new Customer( cid );
		writeData( id, cust.getKey(), cust );
		Trace.info("RM::newCustomer(" + cid + ") returns ID=" + cid );
		return cid;
	}

	// I opted to pass in customerID instead. This makes testing easier
  public boolean newCustomer(int id, int customerID )
		throws RemoteException
	{
		Trace.info("INFO: RM::newCustomer(" + id + ", " + customerID + ") called" );
		Customer cust = (Customer) readData( id, Customer.getKey(customerID) );
		if( cust == null ) {
			cust = new Customer(customerID);
			writeData( id, cust.getKey(), cust );
			Trace.info("INFO: RM::newCustomer(" + id + ", " + customerID + ") created a new customer" );
			return true;
		} else {
			Trace.info("INFO: RM::newCustomer(" + id + ", " + customerID + ") failed--customer already exists");
			return false;
		} // else
	}


	// Deletes customer from the database. 
	public boolean deleteCustomer(int id, int customerID)
			throws RemoteException
	{
		Trace.info("RM::deleteCustomer(" + id + ", " + customerID + ") called" );
		Customer cust = (Customer) readData( id, Customer.getKey(customerID) );
		if( cust == null ) {
			Trace.warn("RM::deleteCustomer(" + id + ", " + customerID + ") failed--customer doesn't exist" );
			return false;
		} else {			
			// Increase the reserved numbers of all reservable items which the customer reserved. 
			RMHashtable reservationHT = cust.getReservations();
			for(Enumeration e = reservationHT.keys(); e.hasMoreElements();){		
				String reservedkey = (String) (e.nextElement());
				ReservedItem reserveditem = cust.getReservedItem(reservedkey);
				Trace.info("RM::deleteCustomer(" + id + ", " + customerID + ") has reserved " + reserveditem.getKey() + " " +  reserveditem.getCount() +  " times"  );
				ReservableItem item  = (ReservableItem) readData(id, reserveditem.getKey());
				Trace.info("RM::deleteCustomer(" + id + ", " + customerID + ") has reserved " + reserveditem.getKey() + "which is reserved" +  item.getReserved() +  " times and is still available " + item.getCount() + " times"  );
				item.setReserved(item.getReserved()-reserveditem.getCount());
				item.setCount(item.getCount()+reserveditem.getCount());
			}
			
			// remove the customer from the storage
			removeData(id, cust.getKey());
			
			Trace.info("RM::deleteCustomer(" + id + ", " + customerID + ") succeeded" );
			return true;
		} // if
	}




	// Frees flight reservation record. Flight reservation records help us make sure we
	//  don't delete a flight if one or more customers are holding reservations
//	public boolean freeFlightReservation(int id, int flightNum)
//		throws RemoteException
//	{
//		Trace.info("RM::freeFlightReservations(" + id + ", " + flightNum + ") called" );
//		RMInteger numReservations = (RMInteger) readData( id, Flight.getNumReservationsKey(flightNum) );
//		if( numReservations != null ) {
//			numReservations = new RMInteger( Math.max( 0, numReservations.getValue()-1) );
//		} // if
//		writeData(id, Flight.getNumReservationsKey(flightNum), numReservations );
//		Trace.info("RM::freeFlightReservations(" + id + ", " + flightNum + ") succeeded, this flight now has "
//				+ numReservations + " reservations" );
//		return true;
//	}
//	

	
	// Adds car reservation to this customer. 
	public boolean reserveCar(int id, int customerID, String location)
		throws RemoteException
	{
		return reserveItem(id, customerID, Car.getKey(location), location);
	}


	// Adds room reservation to this customer. 
	public boolean reserveRoom(int id, int customerID, String location)
		throws RemoteException
	{
		return reserveItem(id, customerID, Hotel.getKey(location), location);
	}
	// Adds flight reservation to this customer.  
	public boolean reserveFlight(int id, int customerID, int flightNum)
		throws RemoteException
	{
		return reserveItem(id, customerID, Flight.getKey(flightNum), String.valueOf(flightNum));
	}
	
	/* reserve an itinerary */
    public boolean itinerary(int id,int customer,Vector flightNumbers,String location,boolean Car,boolean Room)
	throws RemoteException {
    	return false;
    }
    
    public boolean customerExist(int id, int customerID) 
    throws RemoteException {
    	return false;
    }
    
    public boolean customerReserveWrite(int id, String key, String location, int price , int customerID) 
    throws RemoteException{
    	return false;
    }
    
    public void dump(String RMname) throws RemoteException
    {
    	this.m_itemHT.dump();
    }
    public Vector parse(String command)
	{
	Vector arguments = new Vector();
	StringTokenizer tokenizer = new StringTokenizer(command,",");
	String argument ="";
	while (tokenizer.hasMoreTokens())
	    {
		argument = tokenizer.nextToken();
		argument = argument.trim();
		arguments.add(argument);
	    }
	return arguments;
	}
    public int findChoice(String argument)
    {
	if (argument.compareToIgnoreCase("customerExist")==0)
	    return 1;
	else if(argument.compareToIgnoreCase("addflight")==0)
	    return 2;
	else if(argument.compareToIgnoreCase("addcars")==0)
	    return 3;
	else if(argument.compareToIgnoreCase("addrooms")==0)
	    return 4;
	else if(argument.compareToIgnoreCase("newcustomer")==0)
	    return 5;
	else if(argument.compareToIgnoreCase("deleteflight")==0)
	    return 6;
	else if(argument.compareToIgnoreCase("deletecars")==0)
	    return 7;
	else if(argument.compareToIgnoreCase("deleterooms")==0)
	    return 8;
	else if(argument.compareToIgnoreCase("deletecustomer")==0)
	    return 9;
	else if(argument.compareToIgnoreCase("queryflight")==0)
	    return 10;
	else if(argument.compareToIgnoreCase("querycars")==0)
	    return 11;
	else if(argument.compareToIgnoreCase("queryrooms")==0)
	    return 12;
	else if(argument.compareToIgnoreCase("querycustomerinfo")==0)
	    return 13;
	else if(argument.compareToIgnoreCase("queryflightprice")==0)
	    return 14;
	else if(argument.compareToIgnoreCase("querycarsprice")==0)
	    return 15;
	else if(argument.compareToIgnoreCase("queryroomsprice")==0)
	    return 16;
	else if(argument.compareToIgnoreCase("reserveflight")==0)
	    return 17;
	else if(argument.compareToIgnoreCase("reservecar")==0)
	    return 18;
	else if(argument.compareToIgnoreCase("reserveroom")==0)
	    return 19;
	else if(argument.compareToIgnoreCase("itinerary")==0)
	    return 20;
	else if (argument.compareToIgnoreCase("customerReserveWrite")==0)
	    return 21;
	else if (argument.compareToIgnoreCase("newcustomerid")==0)
	    return 22;
	else if (argument.compareToIgnoreCase("dump")==0)
	    return 23;
	else
	    return 666;

    }
}
