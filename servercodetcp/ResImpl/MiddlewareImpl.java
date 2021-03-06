package ResImpl;

import ResInterface.*;

import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.*;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MiddlewareImpl extends Thread implements  ResourceManager{

	protected RMHashtable rms = null;
	protected RMHashtable m_itemHT = null;
	
	//variables when middleware connect to any RM
	Socket socket = null;
	PrintWriter out = null;
	BufferedReader in = null;
	
	/**
	 * Socket for the client connection.
	 */
	private final Socket mSocket;
	
	/**
	 * @param args
	 */
	/*public static void main(String[] args) {
		String server = "localhost";
		
        if (args.length == 1) {
            server = server + ":" + args[0];
        } else if (args.length != 0 &&  args.length != 1) {
            System.err.println ("Wrong usage");
            System.out.println("Usage: java middleware.middlewareImpl [port]");
            System.exit(1);
        }
	 
		 try 
	     {
			 // create a new Handler Server on port 2012
			RequestHandler handler = new RequestHandler(2012);
			handler.start();
			 
			 System.err.println("Server ready");
	     } 
		 catch (Exception e) 
	     {
			 System.err.println("Server exception: " + e.toString());
			 e.printStackTrace();
	     }
		 
	     
	     
	    //create connection to customer RM
	    String[] nameOfRM = {"Flight"}; //"Hotel","Flight","Car"
	    for (String name : nameOfRM) {
	    	try 
			{   
	    		Object[] p = {"localhost",2013};
			    rms.put(name, p);
			} 
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	    
	}*/
	
	

	public MiddlewareImpl(Socket socket, RMHashtable data, RMHashtable rms) throws RemoteException {
		m_itemHT = data;
		mSocket = socket;
		this.rms = rms;
	}
	
	@Override
	public void run(){
		//Input and output streams
		BufferedReader in;
		PrintWriter out;
		System.out.println("Middleware running");

		String request;
		try {
			//No longer connected, move on
			if(!mSocket.isConnected())
				return;
			
			//Create input and output streams
			 in = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
			 out = new PrintWriter(mSocket.getOutputStream(), true);
			 
			 //read in request
			 request = in.readLine();
			 System.out.println("REQUEST : " + request);
			 
			//parse request and get reply
			 Object ob = parseRequest(request);
			 String stringReply = "";
			 if(ob != null)
			 	stringReply = ob.toString();
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
	
	private String send(String methodName, Object[] args, String RMName)
	{
		try {
			Object[] p = (Object[])rms.get(RMName);
			//connect and send feedback
	        socket = new Socket((String)p[0],(Integer)p[1]);
	        System.out.println("Middleware Client connected.");
	        out = new PrintWriter(socket.getOutputStream(), true);
	        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        
	        System.out.println("Middleware Client sending : "+methodName);

	        String req = methodName +",";
	        for (int i = 0; i < args.length; i++) {
				req += args[i].toString() + ",";
			}
	        System.out.println("client sending : "+req);
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
	
	@Override
	public boolean addFlight(int id, int flightNum, int flightSeats,
			int flightPrice) throws RemoteException {
		System.out.println("addFlight on middleware");
		Object[] r = (Object[]) rms.get("Flight");
		if(r != null)
		{
			Object[] p = {id, flightNum, flightSeats, flightPrice};
			String retval = send("addFlight",p,"Flight");
			return Boolean.parseBoolean(retval);
		}
		return false;
	}

	@Override
	public boolean addCars(int id, String location, int numCars, int price)
			throws RemoteException {
		System.out.println("addCars on middleware");
		Object[] r = (Object[]) rms.get("Car");
		if(r != null)
		{
			Object[] p = {id, location, numCars, price};
			String retval = send("addCars",p,"Car");
			return Boolean.parseBoolean(retval);
		}
		return false;
	}

	@Override
	public boolean addRooms(int id, String location, int numRooms, int price)
			throws RemoteException {
		System.out.println("addRooms on middleware");
		Object[] r = (Object[]) rms.get("Hotel");
		if(r != null)
		{
			Object[] p = {id, location, numRooms, price};
			String retval = send("addRooms",p,"Hotel");
			return Boolean.parseBoolean(retval);
		}
		return false;
	}

	@Override
	public int newCustomer(int id) throws RemoteException {
		System.out.println("newCustomer on middleware");
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

	@Override
	public boolean newCustomer(int id, int customerID) throws RemoteException {
		System.out.println("newCustomer on middleware");
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

	@Override
	public boolean deleteFlight(int id, int flightNum) throws RemoteException {
		System.out.println("deleteFlight on middleware");
		Object[] r = (Object[]) rms.get("Flight");
		if(r != null)
		{
			Object[] p = {id, flightNum};
			String retval = send("deleteFlight",p,"Flight");
			return Boolean.parseBoolean(retval);
		}
		return false;
	}

	@Override
	public boolean deleteCars(int id, String location) throws RemoteException {
		System.out.println("deleteCars on middleware");
		Object[] r = (Object[]) rms.get("Car");
		if(r != null)
		{
			Object[] p = {id, location};
			String retval = send("deleteCars",p,"Car");
			return Boolean.parseBoolean(retval);
		}
		return false;
	}

	@Override
	public boolean deleteRooms(int id, String location) throws RemoteException {
		System.out.println("deleteRooms on middleware");
		Object[] r = (Object[]) rms.get("Hotel");
		if(r != null)
		{
			Object[] p = {id, location};
			String retval = send("deleteRooms",p,"Hotel");
			return Boolean.parseBoolean(retval);
		}
		return false;
	}

	@Override
	public boolean deleteCustomer(int id, int customerID) throws RemoteException {
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

	@Override
	public int queryFlight(int id, int flightNumber) throws RemoteException {
		System.out.println("queryFlight on middleware");
		Object[] r = (Object[]) rms.get("Flight");
		if(r != null)
		{
			Object[] p = {id, flightNumber};
			String retval = send("queryFlight",p,"Flight");
			return Integer.parseInt(retval);
		}
		return 0;
	}

	@Override
	public int queryCars(int id, String location) throws RemoteException {
		System.out.println("queryCars on middleware");
		Object[] r = (Object[]) rms.get("Car");
		if(r != null)
		{
			Object[] p = {id, location};
			String retval = send("queryCars",p,"Car");
			return Integer.parseInt(retval);
		}
		return 0;
	}

	@Override
	public int queryRooms(int id, String location) throws RemoteException {
		System.out.println("queryRooms on middleware");
		Object[] r = (Object[]) rms.get("Hotel");
		if(r != null)
		{
			Object[] p = {id, location};
			String retval = send("queryRooms",p,"Hotel");
			return Integer.parseInt(retval);
		}
		return 0;
	}

	@Override
	public String queryCustomerInfo(int id, int customer)
			throws RemoteException {
		Trace.info("RM::queryCustomerInfo(" + id + ", " + customer + ") called" );
		Customer cust = (Customer) readData( id, Customer.getKey(customer) );
		if( cust == null ) {
			Trace.warn("RM::queryCustomerInfo(" + id + ", " + customer + ") failed--customer doesn't exist" );
			return "";   // NOTE: don't change this--WC counts on this value indicating a customer does not exist...
		} else {
				String s = cust.printBill();
				Trace.info("RM::queryCustomerInfo(" + id + ", " + customer + "), bill follows..." );
				System.out.println( s );
				return s;
		} 
	}

	@Override
	public int queryFlightPrice(int id, int flightNumber)
			throws RemoteException {
		System.out.println("queryFlightPrice on middleware");
		Object[] r = (Object[]) rms.get("Flight");
		if(r != null)
		{
			Object[] p = {id, flightNumber};
			String retval = send("queryFlightPrice",p,"Flight");
			return Integer.parseInt(retval);
		}
		return 0;
	}

	@Override
	public int queryCarsPrice(int id, String location) throws RemoteException {
		System.out.println("queryCarsPrice on middleware");
		Object[] r = (Object[]) rms.get("Car");
		if(r != null)
		{
			Object[] p = {id, location};
			String retval = send("queryCarsPrice",p,"Car");
			return Integer.parseInt(retval);
		}
		return 0;
	}

	@Override
	public int queryRoomsPrice(int id, String location) throws RemoteException {
		System.out.println("queryRoomsPrice on middleware");
		Object[] r = (Object[]) rms.get("Hotel");
		if(r != null)
		{
			Object[] p = {id, location};
			String retval = send("queryRoomsPrice",p,"Hotel");
			return Integer.parseInt(retval);
		}
		return 0;
	}

	@Override
	public boolean reserveFlight(int id, int customer, int flightNumber)
			throws RemoteException {
		System.out.println("reserveFlight on middleware");
		Object[] r = (Object[]) rms.get("Flight");
		if(r != null)
		{
			Object[] p = {id, customer, flightNumber};
			String retval = send("reserveFlight",p,"Flight");
			return Boolean.parseBoolean(retval);
		}
		
		return false;
	}

	@Override
	public boolean reserveCar(int id, int customer, String location)
			throws RemoteException {
		System.out.println("reserveCar on middleware");
		Object[] r = (Object[]) rms.get("Car");
		if(r != null)
		{
			Object[] p = {id, customer, location};
			String retval = send("reserveCar",p,"Car");
			return Boolean.parseBoolean(retval);
		}
		return false;
	}

	@Override
	public boolean reserveRoom(int id, int customer, String locationd)
			throws RemoteException {
		Object[] r = (Object[]) rms.get("Hotel");
		if(r != null)
		{
			Object[] p = {id, customer, locationd};
			String retval = send("reserveRoom",p,"Hotel");
			return Boolean.parseBoolean(retval);
		}
		return false;
	}

	@Override
	public boolean itinerary(int id, int customer, Vector flightNumbers,
			String location, boolean Car, boolean Room) throws RemoteException {
			boolean roomReserved = true;
			boolean carReserved = true;
			boolean reserved = false;
			if( Room == true ) {
				roomReserved = reserveRoom(id,customer,location);
				if( roomReserved == true ) {
					System.out.println("your room has been reserved.");
					reserved = true;
				}
			}
			if( Car == true ) {
				carReserved = reserveCar(id,customer,location);
				if( carReserved == true ) {
					System.out.println("your car has been reserved.");
					reserved = true;
				}
			}
			for(int i=0; i < flightNumbers.size(); i++) {
				int flightNumber = Integer.parseInt(flightNumbers.get(i).toString());
				boolean flight = reserveFlight(id,customer,flightNumber);
				if( flight == false ) {
					System.out.println("the flight with flightNumber=" + flightNumber + " could not be reserved");
				}
				if( flight == true ) //if any flight is reserved then set reserved to true 
					reserved = true;
			}
			if( reserved == false ) // if this holds then there were no flights reserved, no cars nor rooms reserved
				return false;
		return true;
	}

	@Override
	public boolean customerExist(int id, int customerID) throws RemoteException {
		Customer cust = (Customer) readData( id, Customer.getKey(customerID));
		if(cust != null)
			return true;
		return false;
	}

	@Override
	public boolean customerReserveWrite(int id, String key, String location, int price,
			int customerID) throws RemoteException {
		Customer cust = (Customer) readData( id, Customer.getKey(customerID) );
		cust.reserve( key, location, price);		
		writeData( id, cust.getKey(), cust );
		return false;
	}
	
	public void dump(String RMname) throws RemoteException
	{
		if(RMname.equalsIgnoreCase("customer"))
		{
			this.m_itemHT.dump();
		}else{
			ResourceManager r = (ResourceManager) this.rms.get(RMname);
			r.dump(RMname);
		}
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
