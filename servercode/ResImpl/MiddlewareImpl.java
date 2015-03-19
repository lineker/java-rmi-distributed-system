package ResImpl;

import LockManager.DeadlockException;
import LockManager.LockManager;
import LockManager.TrxnObj;
import ResInterface.*;
import TransactionManager.InvalidTransactionException;
import TransactionManager.TransactionAbortedException;
import TransactionManager.TransactionManager;
import TransactionManager.TransactionWatcher;

import java.util.*;
import java.rmi.*;
import java.lang.*;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class MiddlewareImpl implements ResourceManager{

	static RMHashtable rms = new RMHashtable();
	protected RMHashtable m_itemHT = new RMHashtable();
	static int registryport = 3000;
	static int registryRMport = 3001;
	
	protected Hashtable<Integer,HashMap<String,RMItem>> m_transaction = new Hashtable<Integer,HashMap<String,RMItem>>();
	protected LockManager lockmanager = new LockManager();
	
	protected TransactionManager m_tmanager = new TransactionManager();
	
	protected TransactionWatcher transaction_watcher = null;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if( args.length != 4 ) {
			System.out.println("You must enter 1 argument for the port and 3 arguments of your active RMs");
			System.exit(1);
		}
		String server = "localhost";
		
/*        if (args.length == 4) {
            server = server + ":" + args[0];
        } else// if (args.length != 0 &&  args.length != 1) 
	{
            System.err.println ("Wrong usage");
            System.out.println("Usage: java middleware.middlewareImpl [port]");
            System.exit(1);
        }
*/	 
		 try 
	     {
			 // create a new Server object
			 MiddlewareImpl obj = new MiddlewareImpl();
			 int port = Integer.parseInt(args[0]);
			 // dynamically generate the stub (client proxy)
			 ResourceManager rm = (ResourceManager) UnicastRemoteObject.exportObject(obj, port);
			 // Bind the remote object's stub in the registry
			 Registry registry = LocateRegistry.getRegistry(registryport);
			 registry.rebind("MyGroup25", rm);
			 
			 System.err.println("Server ready");
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
	     
	    //create connection to customer RM
	    Registry registry;
	    String nom;
	    int port;
	    String[] str = new String[3];
	    String[] nameOfRM = {args[1],args[2],args[3]};
	   // String[] nameOfRM = {"Hotel","Flight","Car"};
	    for (String name : nameOfRM) {
	    	try 
			{
				//split the attribute into 3 parts: Hotel, IP and Port
				str = name.split(":");
				nom = str[0];
				String serv = str[1];
				port = Integer.parseInt(str[2]); //port registry of RMI
				// get a reference to the rmiregistry
				//use the following when server on different computer
				//registry = LocateRegistry.getRegistry(server,port);
				System.out.print("connection to RM at "+serv+":"+port);
				registry = LocateRegistry.getRegistry(serv,registryRMport);
				// get the proxy and the remote reference by rmiregistry lookup
			    ResourceManager rm = (ResourceManager) registry.lookup("MyGroup25-"+nom);
			    
			    rms.put(nom, rm);
			} 
			catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    
	}

	public MiddlewareImpl()
	{
		transaction_watcher = new TransactionWatcher(this);
		transaction_watcher.start();
	}
	
	public void track(int id, String key, RMItem value)
	{
		HashMap<String, RMItem> l;
		RMItem item;
		synchronized(m_transaction){
			l = m_transaction.get(id);
			if(l == null) 
			{
				System.out.println("track hashtable is null and trying to track");
				return;
			}
		}
		
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
		protected RMItem removeData(int id, String key){
			RMItem item = null;
			Boolean lockgrant = false;
			synchronized(lockmanager){
				try {
					lockgrant = lockmanager.Lock(id, key, TrxnObj.WRITE);
				} catch (DeadlockException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
				TID = m_tmanager.start();
				
				//transaction for Customer objects
				m_transaction.put(TID, new HashMap<String,RMItem>());
			}
			transaction_watcher.reset(TID);
			return TID;
		}
		
		
		public boolean commit(int TID) throws RemoteException, TransactionAbortedException,
	    InvalidTransactionException
		{   
			transaction_watcher.remove(TID);
			boolean result = m_tmanager.commit(TID,rms);
			
			//commit local
		    //remove transaction for customer
		    m_transaction.remove(TID);
			lockmanager.UnlockAll(TID);
			//end commit local
			
			return result;
		}
		
		public void abort(int TID) throws RemoteException, InvalidTransactionException
		{
			transaction_watcher.remove(TID);
			System.out.println("Aborting Transaction : "+ TID);
			
			//abort on all RMs
			m_tmanager.abort(TID,rms);
			
			//Abort for customer (local RM)
			//if find null for key remove from items hashtable
			HashMap<String, RMItem> l = m_transaction.get(TID);
			
			if(l != null)
			{
				Iterator iter = l.keySet().iterator();

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
		            	System.out.println(key + " put back");
		            }
	
				}
			}
			
			//abort local
		    //remove transaction for customer
		    m_transaction.remove(TID);
			lockmanager.UnlockAll(TID);
			//end abort local
			
			
		}
		
		public boolean shutdown() throws RemoteException
		{
			//abort on all RMs
			if( m_tmanager.countTransactions() != 0 ) //if there are transactions active then do nothing
				return false;
			else
			{
				m_tmanager.shutdown(rms);
				killRm t1 = new killRm (this);
				t1.start();
				return true;
			}
		}

	public void realShutdown() throws RemoteException {
		System.exit(0);
	}

	
	@Override
	public boolean addFlight(int id, int flightNum, int flightSeats,
			int flightPrice) throws RemoteException {
		transaction_watcher.reset(id);
		System.out.println("addFlight on middleware");
		ResourceManager r = (ResourceManager) rms.get("Flight");
		if(r != null)
		{
			int tid = m_tmanager.getTransaction(id,"Flight", rms);
			try {
				return r.addFlight(tid, flightNum, flightSeats, flightPrice);
			} catch (DeadlockException e) {
				// TODO Auto-generated catch block
				System.out.println("TID : "+id+" deadlock!");
				try {
					m_tmanager.abort(id, rms);
				} catch (InvalidTransactionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return false;
	}

	@Override
	public boolean addCars(int id, String location, int numCars, int price)
			throws RemoteException {
		transaction_watcher.reset(id);
		System.out.println("addCars on middleware");
		ResourceManager r = (ResourceManager) rms.get("Car");
		if(r != null)
		{
			int tid = m_tmanager.getTransaction(id,"Car", rms);
			try {
				return r.addCars(tid, location, numCars, price);
			} catch (DeadlockException e) {
				// TODO Auto-generated catch block
				System.out.println("TID : "+id+" deadlock!");
				try {
					m_tmanager.abort(id, rms);
				} catch (InvalidTransactionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return false;
	}

	@Override
	public boolean addRooms(int id, String location, int numRooms, int price)
			throws RemoteException {
		transaction_watcher.reset(id);
		System.out.println("addRooms on middleware");
		ResourceManager r = (ResourceManager) rms.get("Hotel");
		if(r != null)
		{
			int tid = m_tmanager.getTransaction(id,"Hotel", rms);
			try {
				return r.addRooms(tid, location, numRooms, price);
			} catch (DeadlockException e) {
				// TODO Auto-generated catch block
				System.out.println("TID : "+id+" deadlock!");
				try {
					m_tmanager.abort(id, rms);
				} catch (InvalidTransactionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return false;
	}

	@Override
	public int newCustomer(int id) throws RemoteException {
		transaction_watcher.reset(id);
		System.out.println("newCustomer on middleware");
		Trace.info("INFO: RM::newCustomer(" + id + ") called" );
		// Generate a globally unique ID for the new customer
		int cid = Integer.parseInt( String.valueOf(id) +
								String.valueOf(Calendar.getInstance().get(Calendar.MILLISECOND)) +
								String.valueOf( Math.round( Math.random() * 100 + 1 )));
		Customer cust = new Customer( cid );
		try {
			writeData( id, cust.getKey(), cust );
		} catch (DeadlockException e) {
			// TODO Auto-generated catch block
			System.out.println("TID : "+id+" deadlock!");
			try {
				m_tmanager.abort(id, rms);
			} catch (InvalidTransactionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		Trace.info("RM::newCustomer(" + cid + ") returns ID=" + cid );
		return cid;
	}

	@Override
	public boolean newCustomer(int id, int customerID) throws RemoteException {
		transaction_watcher.reset(id);
		System.out.println("newCustomer on middleware");
		Trace.info("INFO: RM::newCustomer(" + id + ", " + customerID + ") called" );
		Customer cust;
		try {
			cust = (Customer) readData( id, Customer.getKey(customerID) );
		
			if( cust == null ) {
				cust = new Customer(customerID);
				writeData( id, cust.getKey(), cust );
				Trace.info("INFO: RM::newCustomer(" + id + ", " + customerID + ") created a new customer" );
				return true;
			} else {
				Trace.info("INFO: RM::newCustomer(" + id + ", " + customerID + ") failed--customer already exists");
				return false;
			} // else
		} catch (DeadlockException e) {
			// TODO Auto-generated catch block
			System.out.println("TID : "+id+" deadlock!");
			try {
				m_tmanager.abort(id, rms);
			} catch (InvalidTransactionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean deleteFlight(int id, int flightNum) throws RemoteException {
		transaction_watcher.reset(id);
		System.out.println("deleteFlight on middleware");
		ResourceManager r = (ResourceManager) rms.get("Flight");
		if(r != null)
		{
			int tid = m_tmanager.getTransaction(id,"Flight", rms);
			try {
				return r.deleteFlight(tid, flightNum);
			} catch (DeadlockException e) {
				// TODO Auto-generated catch block
				System.out.println("TID : "+id+" deadlock!");
				try {
					m_tmanager.abort(id, rms);
				} catch (InvalidTransactionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return false;
	}

	@Override
	public boolean deleteCars(int id, String location) throws RemoteException {
		transaction_watcher.reset(id);
		System.out.println("deleteCars on middleware");
		ResourceManager r = (ResourceManager) rms.get("Car");
		if(r != null)
		{
			int tid = m_tmanager.getTransaction(id,"Car", rms);
			try {
				return r.deleteCars(tid, location);
			} catch (DeadlockException e) {
				// TODO Auto-generated catch block
				System.out.println("TID : "+id+" deadlock!");
				try {
					m_tmanager.abort(id, rms);
				} catch (InvalidTransactionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return false;
	}

	@Override
	public boolean deleteRooms(int id, String location) throws RemoteException {
		transaction_watcher.reset(id);
		System.out.println("deleteRooms on middleware");
		ResourceManager r = (ResourceManager) rms.get("Hotel");
		if(r != null)
		{
			int tid = m_tmanager.getTransaction(id,"Hotel", rms);
			try {
				return r.deleteRooms(tid, location);
			} catch (DeadlockException e) {
				// TODO Auto-generated catch block
				System.out.println("TID : "+id+" deadlock!");
				try {
					m_tmanager.abort(id, rms);
				} catch (InvalidTransactionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return false;
	}

	@Override
	public boolean deleteCustomer(int id, int customerID) throws RemoteException {
		transaction_watcher.reset(id);
		Trace.info("RM::deleteCustomer(" + id + ", " + customerID + ") called" );
		try {
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
					Trace.info("RM: :deleteCustomer(" + id + ", " + customerID + ") has reserved " + reserveditem.getKey() + " " +  reserveditem.getCount() +  " times"  );
					
						if(reserveditem.getKey().startsWith("flight"))
						{
							ResourceManager r = (ResourceManager) rms.get("Flight");
							if(r != null)
							{
								int tid = m_tmanager.getTransaction(id,"Flight", rms);
								
									r.removeReservation(tid, reserveditem.getKey(), reserveditem.getCount());
								
							}
						}
						else if(reserveditem.getKey().startsWith("room"))
						{
							ResourceManager r = (ResourceManager) rms.get("Hotel");
							if(r != null)
							{
								int tid = m_tmanager.getTransaction(id,"Hotel", rms);
								r.removeReservation(tid, reserveditem.getKey(), reserveditem.getCount());
							}
						}
						else if(reserveditem.getKey().startsWith("car"))
						{
							ResourceManager r = (ResourceManager) rms.get("Car");
							if(r != null)
							{
								int tid = m_tmanager.getTransaction(id,"Car", rms);
								r.removeReservation(tid, reserveditem.getKey(), reserveditem.getCount());
							}
						}
					
					
					//call here
					//Trace.info("RM::deleteCustomer(" + id + ", " + customerID + ") has reserved " + reserveditem.getKey() + "which is reserved" +  item.getReserved() +  " times and is still available " + item.getCount() + " times"  );
				}
				
				// remove the customer from the storage
				removeData(id, cust.getKey());
				
				
				
				Trace.info("RM::deleteCustomer(" + id + ", " + customerID + ") succeeded" );
				return true;
			} // if
		} catch (DeadlockException e1) {
			// TODO Auto-generated catch block
			System.out.println("TID : "+id+" deadlock!");
			try {
				m_tmanager.abort(id, rms);
			} catch (InvalidTransactionException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public int queryFlight(int id, int flightNumber) throws RemoteException {
		transaction_watcher.reset(id);
		System.out.println("queryFlight on middleware");
		ResourceManager r = (ResourceManager) rms.get("Flight");
		if(r != null)
		{
			int tid = m_tmanager.getTransaction(id,"Flight", rms);
			try {
				return r.queryFlight(tid, flightNumber);
			} catch (DeadlockException e) {
				// TODO Auto-generated catch block
				System.out.println("TID : "+id+" deadlock!");
				try {
					m_tmanager.abort(id, rms);
				} catch (InvalidTransactionException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		}
		return 0;
	}

	@Override
	public int queryCars(int id, String location) throws RemoteException {
		transaction_watcher.reset(id);
		System.out.println("queryCars on middleware");
		ResourceManager r = (ResourceManager) rms.get("Car");
		if(r != null)
		{
			int tid = m_tmanager.getTransaction(id,"Car", rms);
			try {
				return r.queryCars(tid, location);
			} catch (DeadlockException e) {
				// TODO Auto-generated catch block
				System.out.println("TID : "+id+" deadlock!");
				try {
					m_tmanager.abort(id, rms);
				} catch (InvalidTransactionException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		}
		return 0;
	}

	@Override
	public int queryRooms(int id, String location) throws RemoteException {
		transaction_watcher.reset(id);
		System.out.println("queryRooms on middleware");
		ResourceManager r = (ResourceManager) rms.get("Hotel");
		if(r != null)
		{
			int tid = m_tmanager.getTransaction(id,"Hotel", rms);
			try {
				return r.queryRooms(tid, location);
			} catch (DeadlockException e) {
				// TODO Auto-generated catch block
				System.out.println("TID : "+id+" deadlock!");
				try {
					m_tmanager.abort(id, rms);
				} catch (InvalidTransactionException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		}
		return 0;
	}

	@Override
	public String queryCustomerInfo(int id, int customer)
			throws RemoteException {
		transaction_watcher.reset(id);
		Trace.info("RM::queryCustomerInfo(" + id + ", " + customer + ") called" );

		Customer cust;
		try {
			cust = (Customer) readData( id, Customer.getKey(customer) );
		

		if( cust == null ) {

			Trace.warn("RM::queryCustomerInfo(" + id + ", " + customer + ") failed--customer doesn't exist" );

			return "";   // NOTE: don't change this--WC counts on this value indicating a customer does not exist...

		} else {

				String s = cust.printBill();

				Trace.info("RM::queryCustomerInfo(" + id + ", " + customer + "), bill follows..." );

				System.out.println( s );

				return s;

		} 
		
		} catch (DeadlockException e) {
			// TODO Auto-generated catch block
			System.out.println("TID : "+id+" deadlock!");
			try {
				m_tmanager.abort(id, rms);
			} catch (InvalidTransactionException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
		return "";
	}

	@Override
	public int queryFlightPrice(int id, int flightNumber)
			throws RemoteException {
		transaction_watcher.reset(id);
		System.out.println("queryFlightPrice on middleware");
		ResourceManager r = (ResourceManager) rms.get("Flight");
		if(r != null)
		{
			int tid = m_tmanager.getTransaction(id,"Flight", rms);
			try {
				return r.queryFlightPrice(tid, flightNumber);
			} catch (DeadlockException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					m_tmanager.abort(id, rms);
				} catch (InvalidTransactionException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		}
		return 0;
	}

	@Override
	public int queryCarsPrice(int id, String location) throws RemoteException {
		transaction_watcher.reset(id);
		System.out.println("queryCarsPrice on middleware");
		ResourceManager r = (ResourceManager) rms.get("Car");
		if(r != null)
		{
			int tid = m_tmanager.getTransaction(id,"Car", rms);
			try {
				return r.queryCarsPrice(tid, location);
			} catch (DeadlockException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					m_tmanager.abort(id, rms);
				} catch (InvalidTransactionException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		}
		return 0;
	}

	@Override
	public int queryRoomsPrice(int id, String location) throws RemoteException {
		transaction_watcher.reset(id);
		System.out.println("queryRoomsPrice on middleware");
		ResourceManager r = (ResourceManager) rms.get("Hotel");
		if(r != null)
		{
			int tid = m_tmanager.getTransaction(id,"Hotel", rms);
			try {
				return r.queryRoomsPrice(tid, location);
			} catch (DeadlockException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					m_tmanager.abort(id, rms);
				} catch (InvalidTransactionException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		}
		return 0;
	}

	@Override
	public boolean reserveFlight(int id, int customer, int flightNumber)
			throws RemoteException {
		transaction_watcher.reset(id);
		System.out.println("reserveFlight on middleware");
		ResourceManager r = (ResourceManager) rms.get("Flight");
		if(r != null)
		{
			int tid = m_tmanager.getTransaction(id,"Flight", rms);
			try {
				return r.reserveFlight(tid, customer, flightNumber);
			} catch (DeadlockException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					m_tmanager.abort(id, rms);
				} catch (InvalidTransactionException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		}
		return false;
	}

	@Override
	public boolean reserveCar(int id, int customer, String location)
			throws RemoteException {
		transaction_watcher.reset(id);
		System.out.println("reserveCar on middleware");
		ResourceManager r = (ResourceManager) rms.get("Car");
		if(r != null)
		{
			int tid = m_tmanager.getTransaction(id,"Car", rms);
			try {
				return r.reserveCar(tid, customer, location);
			} catch (DeadlockException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					m_tmanager.abort(id, rms);
				} catch (InvalidTransactionException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		}
		return false;
	}

	@Override
	public boolean reserveRoom(int id, int customer, String locationd)
			throws RemoteException {
		transaction_watcher.reset(id);
		ResourceManager r = (ResourceManager) rms.get("Hotel");
		if(r != null)
		{
			int tid = m_tmanager.getTransaction(id,"Hotel", rms);
			try {
				return r.reserveRoom(tid, customer, locationd);
			} catch (DeadlockException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					m_tmanager.abort(id, rms);
				} catch (InvalidTransactionException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		}
		return false;
	}

	@Override
	public boolean itinerary(int id, int customer, Vector flightNumbers,
			String location, boolean Car, boolean Room) throws RemoteException {
		transaction_watcher.reset(id);
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
		Customer cust = null;;
		try {
			cust = (Customer) readData( id, Customer.getKey(customerID));
		} catch (DeadlockException e) {
			// TODO Auto-generated catch block
			System.out.println("TID : "+id+" deadlock!");
			try {
				m_tmanager.abort(id, rms);
			} catch (InvalidTransactionException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
		if(cust != null)
			return true;
		return false;
	}

	@Override
	public boolean customerReserveWrite(int id, String key, String location, int price,
			int customerID) throws RemoteException {
		Customer cust;
		try {
			cust = (Customer) readData( id, Customer.getKey(customerID) );
		
		
		cust.reserve( key, location, price);		
		writeData( id, cust.getKey(), cust );
		
		} catch (DeadlockException e) {
			// TODO Auto-generated catch block
			System.out.println("TID : "+id+" deadlock!");
			try {
				m_tmanager.abort(id, rms);
			} catch (InvalidTransactionException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
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
	
	public boolean removeReservation(int id, String s,int c) 
	{
		return true;
	}

	@Override
	public int start(int TID) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
