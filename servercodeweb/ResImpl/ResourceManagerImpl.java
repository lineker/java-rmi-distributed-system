// -------------------------------
// adapated from Kevin T. Manley
// CSE 593
//
package ResImpl;

import LockManager.DeadlockException;
import LockManager.LockManager;
import LockManager.TrxnObj;
import ResInterface.*;
import TransactionManager.InvalidTransactionException;
import TransactionManager.TransactionAbortedException;

import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.*;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.xml.namespace.QName;

//public class ResourceManagerImpl extends java.rmi.server.UnicastRemoteObject
public class ResourceManagerImpl
	implements ResourceManager {
	
	protected RMHashtable m_itemHT = new RMHashtable();
	rm.rmwebservice.client.RmWeb rm_middle = null;
	static String middleServer = "localhost";
	static int registryport = 3001;
	static int middleregistryport = 3000;
	
	protected Hashtable<Integer,HashMap<String,RMItem>> m_transaction = new Hashtable<Integer,HashMap<String,RMItem>>();
	protected LockManager lockmanager = new LockManager();
	
	public static void main(String args[]) {
        // Figure out where server is running
        String server = "localhost";
        int port = 0;
         if (args.length >= 1) {
             server = server + ":" + args[0];
             port = Integer.parseInt(args[0]);
             middleServer = args[2];
         } else if (args.length != 0 &&  args.length != 1) {
             System.err.println ("Wrong usage");
             System.out.println("Usage: java ResImpl.ResourceManagerImpl [port] [RM Label] [Middleware host]");
             System.exit(1);
         }
	 
		 try 
	     {
			 // create a new Server object Label:ip:port
			 ResourceManagerImpl obj = new ResourceManagerImpl();
			 // dynamically generate the stub (client proxy)
			 ResourceManager rm = (ResourceManager) UnicastRemoteObject.exportObject(obj, port);
			 
			 // Bind the remote object's stub in the registry
			 Registry registry = LocateRegistry.getRegistry(3001);
			 registry.rebind("MyGroup25-"+args[1], rm);
			 
			 System.err.println("Server["+args[1]+"] ready");
	     } 
		 catch (Exception e) 
	     {
			 System.err.println("Server exception: " + e.toString());
			 e.printStackTrace();
	     }
		 
	     // Create and install a security manager
	     if (System.getSecurityManager() == null) {
	     	System.setSecurityManager(new RMISecurityManager());
	     }
	 }
	 
	 
	 public ResourceManagerImpl() throws RemoteException {
	 }
	 
	 public ResourceManagerImpl(boolean isWS)
	 {
		 //set up web proxy to MW 
	 }
	 
	 private URL getWebserviceURL(String server, int port)
		{
			 URL url = null;
		        try {
		            URL baseUrl;
		            baseUrl = rm.rmwebservice.client.RmWebService.class.getResource(".");
		            url = new URL(baseUrl, "http://"+server+":"+new Integer(port).toString()+"/MwWeb?wsdl");
		        } catch (MalformedURLException e) {
		            System.out.println("Failed to create URL for the wsdl Location: 'http://localhost:9898/MwWeb?wsdl', retrying as a local file");
		            System.out.println(e.getMessage());
		        }
		        return url;
		}
	 
	 public rm.rmwebservice.client.RmWeb getMiddleRM()
	{
		if(this.rm_middle == null)
		{
			//Add others RM just changing port and address
			URL mwURL = getWebserviceURL("localhost",9898);
			rm.rmwebservice.client.RmWebService service = new rm.rmwebservice.client.RmWebService(mwURL,new QName("http://webservice.rm/", "RmWebService"));
			this.rm_middle = service.getRmWebPort();
		    
		    if(rm_middle!=null)
			{
			    System.out.println("Successful - Connected to Middle RM");
			}
		    else
			{
			    System.out.println("Unsuccessful - Connected to Middle RM");
			}
		}
		return this.rm_middle;
	}
	
	public void track(int id, String key, RMItem value)
	{
		HashMap<String, RMItem> l;
		RMItem item;
		synchronized(m_transaction){
			l = m_transaction.get(id);
		}
		if(l == null) System.out.println("l null");
		
		
			if(!l.containsKey(key))
			{
				synchronized(m_itemHT){
					RMItem original = (RMItem)m_itemHT.get(key);
					if(original == null && value == null)
						return; //if we don't find the item and we are trying to read or delete there is no point on adding to track
					item = (RMItem)DeepCopy.copy(original);
					if(item != null){
						System.out.println("original added to track :"+item.toString() + " ---- " + item.hashCode());
						if(value != null) System.out.println("new :"+value.toString() + " ---- " + value.hashCode());
					}
					else System.out.println("original added to track : null");
					l.put(key, item);
				}
			}
		
	}
	 
	// Reads a data item
	private RMItem readData( int id, String key ) throws DeadlockException
	{	
		Boolean lockgrant = false;
		synchronized(lockmanager){
				lockgrant = lockmanager.Lock(id, key, TrxnObj.READ);
			
		}
		if(lockgrant)
		{
			System.out.println(id+" - lock granted");
			this.track(id, key, null);
			synchronized(m_itemHT){
				return (RMItem) m_itemHT.get(key);
			}
		}
		/*TODO: Might have to change this to wait for a lock? */
		return null; 
	}

	// Writes a data item
	private void writeData( int id, String key, RMItem value ) throws DeadlockException
	{
		Boolean lockgrant = false;
		synchronized(lockmanager){
				lockgrant = lockmanager.Lock(id, key, TrxnObj.WRITE);
			
		}
		if(lockgrant)
		{
			System.out.println(id+" - lock granted");

			this.track(id, key, value);
			synchronized(m_itemHT){
				m_itemHT.put(key, value);
			}

		}
	}
	
	// Remove the item out of storage
	protected RMItem removeData(int id, String key) throws DeadlockException{
		RMItem item = null;
		Boolean lockgrant = false;
		synchronized(lockmanager){

				lockgrant = lockmanager.Lock(id, key, TrxnObj.WRITE);
			
		}
		if(lockgrant)
		{
			System.out.println(id+" - lock granted");
			
			this.track(id, key, null);
			synchronized(m_itemHT){
				item = (RMItem)m_itemHT.remove(key);
			}

		}
		return item;
	}
	
	public int start() throws RemoteException
	{
		int TID;
		synchronized(m_transaction){
			TID = m_transaction.size()+1;
			m_transaction.put(TID, new HashMap<String,RMItem>());
		}
		System.out.println(m_transaction.size());
		return TID;
	}
	
	public int startWithId(int TID) throws RemoteException
	{
		synchronized(m_transaction){
			
			m_transaction.put(TID, new HashMap<String,RMItem>());
		}
		return TID;
	}
	
	public boolean commit(int TID) throws RemoteException, TransactionAbortedException,
    InvalidTransactionException
	{
		Object n = m_transaction.remove(TID);
		lockmanager.UnlockAll(TID);
		if(n == null)
			return false;
		return true;
	}
	
	public void abort(int TID) throws RemoteException, InvalidTransactionException
	{
		System.out.println("Aborting Transaction : "+ TID);
		//if find null for key remove from items hashtable
		HashMap<String, RMItem> l = m_transaction.get(TID);
		
		if(l != null)
		{
			 Iterator iter = l.keySet().iterator();
			 System.out.println("number items to rollback : "+l.size());
			 while(iter.hasNext()) {
	
				String key = (String) iter.next();
		
				RMItem item = l.get(key);
		
				System.out.println("Aborting item : "+ key);
	            if(item == null)
	            {
	            	m_itemHT.remove(key);
	            	System.out.println(key + " removed");
	            }
	            else
	            {
	            	m_itemHT.put(key, item);
	            	System.out.println(key + " put back : "+item.toString());
	            	
	            }
	
			}
		}
		
		m_transaction.remove(TID);
        lockmanager.UnlockAll(TID);
	}
	
	public boolean shutdown() throws RemoteException
	{
		killRm t1 = new killRm (this);
		t1.start();
		return true;
	}

	public void realShutdown() throws RemoteException {
		System.exit(0);
	}
	
	// deletes the entire item
	protected boolean deleteItem(int id, String key) throws DeadlockException
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
	protected int queryNum(int id, String key) throws DeadlockException {
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
	protected int queryPrice(int id, String key) throws DeadlockException{
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
	protected boolean reserveItem(int id, int customerID, String key, String location) throws DeadlockException{
		Trace.info("RM::reserveItem( " + id + ", customer=" + customerID + ", " +key+ ", "+location+" ) called" );		
		// Read customer object if it exists (and read lock it)
		String cust_id = Customer.getKey(customerID);
		//Customer cust = (Customer) readData( id, Customer.getKey(customerID) );		
		boolean exist;
		System.out.println("reserve item will try to check customer");
		exist = getMiddleRM().customerExist(id, customerID);
		System.out.println("customerExist returned");
		if( !exist ) {
			Trace.warn("RM::reserveItem( " + id + ", " + customerID + ", " + key + ", "+location+")  failed--customer doesn't exist" );
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
				getMiddleRM().customerReserveWrite(id, key, location, item.getPrice(), customerID);
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
	
	public boolean removeReservation(int id, String reservedkey, int count) throws DeadlockException
	{
		System.out.println("removing reservation");
		ReservableItem item  = (ReservableItem) readData(id, reservedkey);
		item.setReserved(item.getReserved()-count);
		item.setCount(item.getCount()+count);
		
		return true;
	}
	
	// Create a new flight, or add seats to existing flight
	//  NOTE: if flightPrice <= 0 and the flight already exists, it maintains its current price
	public boolean addFlight(int id, int flightNum, int flightSeats, int flightPrice)
		throws RemoteException, DeadlockException
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
		throws RemoteException, DeadlockException
	{
		return deleteItem(id, Flight.getKey(flightNum));
	}



	// Create a new room location or add rooms to an existing location
	//  NOTE: if price <= 0 and the room location already exists, it maintains its current price
	public boolean addRooms(int id, String location, int count, int price)
		throws RemoteException, DeadlockException
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
		throws RemoteException, DeadlockException
	{
		return deleteItem(id, Hotel.getKey(location));
		
	}

	// Create a new car location or add cars to an existing location
	//  NOTE: if price <= 0 and the location already exists, it maintains its current price
	public boolean addCars(int id, String location, int count, int price)
		throws RemoteException, DeadlockException
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
		throws RemoteException, DeadlockException
	{
		return deleteItem(id, Car.getKey(location));
	}



	// Returns the number of empty seats on this flight
	public int queryFlight(int id, int flightNum)
		throws RemoteException, DeadlockException
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
		throws RemoteException, DeadlockException
	{
		return queryPrice(id, Flight.getKey(flightNum));
	}


	// Returns the number of rooms available at a location
	public int queryRooms(int id, String location)
		throws RemoteException, DeadlockException
	{
		return queryNum(id, Hotel.getKey(location));
	}


	
	
	// Returns room price at this location
	public int queryRoomsPrice(int id, String location)
		throws RemoteException, DeadlockException
	{
		return queryPrice(id, Hotel.getKey(location));
	}


	// Returns the number of cars available at a location
	public int queryCars(int id, String location)
		throws RemoteException, DeadlockException
	{
		return queryNum(id, Car.getKey(location));
	}


	// Returns price of cars at this location
	public int queryCarsPrice(int id, String location)
		throws RemoteException, DeadlockException
	{
		return queryPrice(id, Car.getKey(location));
	}

	// Returns data structure containing customer reservation info. Returns null if the
	//  customer doesn't exist. Returns empty RMHashtable if customer exists but has no
	//  reservations.
	public RMHashtable getCustomerReservations(int id, int customerID)
		throws RemoteException, DeadlockException
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
		throws RemoteException, DeadlockException
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
		} 
	}

  // customer functions
  // new customer just returns a unique customer identifier
	
  public int newCustomer(int id)
		throws RemoteException, NumberFormatException, DeadlockException
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
  public boolean newCustomerWithId(int id, int customerID )
		throws RemoteException, DeadlockException
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
			throws RemoteException, DeadlockException
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
		throws RemoteException, DeadlockException
	{
		return reserveItem(id, customerID, Car.getKey(location), location);
	}


	// Adds room reservation to this customer. 
	public boolean reserveRoom(int id, int customerID, String location)
		throws RemoteException, DeadlockException
	{
		return reserveItem(id, customerID, Hotel.getKey(location), location);
	}
	// Adds flight reservation to this customer.  
	public boolean reserveFlight(int id, int customerID, int flightNum)
		throws RemoteException, DeadlockException
	{
		System.out.println("inside RM, reserveFlight");
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
}
