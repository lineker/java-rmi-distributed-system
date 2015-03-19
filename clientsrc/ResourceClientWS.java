import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Vector;

import javax.xml.namespace.QName;

import LockManager.DeadlockException;
import ResInterface.ResourceManager;
import TransactionManager.InvalidTransactionException;
import TransactionManager.TransactionAbortedException;
import rm.rmwebservice.client.DeadlockException_Exception;
import rm.rmwebservice.client.InvalidTransactionException_Exception;
import rm.rmwebservice.client.NumberFormatException_Exception;
import rm.rmwebservice.client.RmWebService;
import rm.rmwebservice.client.TransactionAbortedException_Exception;


public class ResourceClientWS implements ResourceManager{
	
	rm.rmwebservice.client.RmWeb rm;
	public ResourceClientWS(String server, int port)
	{       
        URL mwURL = getWebserviceURL("localhost",9898);
		rm.rmwebservice.client.RmWebService service = new rm.rmwebservice.client.RmWebService(mwURL,new QName("http://webservice.rm/", "RmWebService"));
		rm = service.getRmWebPort();
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
	
	@Override
	public boolean addFlight(int id, int flightNum, int flightSeats,
			int flightPrice) throws RemoteException, DeadlockException {
		
		try {
			return rm.addFlight(id, flightNum, flightSeats, flightPrice);
		} catch (DeadlockException_Exception e) {
			//e.printStackTrace();
			throw new DeadlockException(id,e.getMessage());
		}
	}
	@Override
	public boolean addCars(int id, String location, int numCars, int price)
			throws RemoteException, DeadlockException {
		try {
			return rm.addCars(id, location, numCars, price);
		} catch (DeadlockException_Exception e) {
			//e.printStackTrace();
			throw new DeadlockException(id,e.getMessage());
		}		
	}
	@Override
	public boolean addRooms(int id, String location, int numRooms, int price)
			throws RemoteException, DeadlockException {
		try {
			return rm.addRooms(id, location, numRooms, price);
		} catch (DeadlockException_Exception e) {
			//e.printStackTrace();
			throw new DeadlockException(id,e.getMessage());
		}		
	}
	@Override
	public int newCustomer(int id) throws RemoteException,
			NumberFormatException, DeadlockException {
		try {
			return rm.newCustomer(id);
		} catch (DeadlockException_Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new DeadlockException(id,e.getMessage());
		} catch (NumberFormatException_Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new NumberFormatException(e.getMessage());
		}
	}
	
	@Override
	public boolean newCustomerWithId(int id, int cid) throws RemoteException,
			DeadlockException {
		try {
			return rm.newCustomerWithId(id, cid);
		} catch (DeadlockException_Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new DeadlockException(id,e.getMessage());
		} 		
	}
	@Override
	public boolean deleteFlight(int id, int flightNum) throws RemoteException,
			DeadlockException {
		try {
			return rm.deleteFlight(id, flightNum);
		} catch (DeadlockException_Exception e) {
			//e.printStackTrace();
			throw new DeadlockException(id,e.getMessage());
		}	
	}
	@Override
	public boolean deleteCars(int id, String location) throws RemoteException,
			DeadlockException {
		try {
			return rm.deleteCars(id, location);
		} catch (DeadlockException_Exception e) {
			//e.printStackTrace();
			throw new DeadlockException(id,e.getMessage());
		}	
	}
	@Override
	public boolean deleteRooms(int id, String location) throws RemoteException,
			DeadlockException {
		try {
			return rm.deleteRooms(id, location);
		} catch (DeadlockException_Exception e) {
			//e.printStackTrace();
			throw new DeadlockException(id,e.getMessage());
		}	
	}
	@Override
	public boolean deleteCustomer(int id, int customer) throws RemoteException,
			DeadlockException {
		try {
			return rm.deleteCustomer(id, customer);
		} catch (DeadlockException_Exception e) {
			//e.printStackTrace();
			throw new DeadlockException(id,e.getMessage());
		}	
	}
	@Override
	public int queryFlight(int id, int flightNumber) throws RemoteException,
			DeadlockException {
		try {
			return rm.queryFlight(id, flightNumber);
		} catch (DeadlockException_Exception e) {
			//e.printStackTrace();
			throw new DeadlockException(id,e.getMessage());
		}	
	}
	@Override
	public int queryCars(int id, String location) throws RemoteException,
			DeadlockException {
		try {
			return rm.queryCars(id, location);
		} catch (DeadlockException_Exception e) {
			//e.printStackTrace();
			throw new DeadlockException(id,e.getMessage());
		}	
	}
	@Override
	public int queryRooms(int id, String location) throws RemoteException,
			DeadlockException {
		try {
			return rm.queryRooms(id, location);
		} catch (DeadlockException_Exception e) {
			//e.printStackTrace();
			throw new DeadlockException(id,e.getMessage());
		}	
	}
	@Override
	public String queryCustomerInfo(int id, int customer)
			throws RemoteException, DeadlockException {
		try {
			return rm.queryCustomerInfo(id, customer);
		} catch (DeadlockException_Exception e) {
			//e.printStackTrace();
			throw new DeadlockException(id,e.getMessage());
		}
	}
	@Override
	public int queryFlightPrice(int id, int flightNumber)
			throws RemoteException, DeadlockException {
		try {
			return rm.queryFlightPrice(id, flightNumber);
		} catch (DeadlockException_Exception e) {
			//e.printStackTrace();
			throw new DeadlockException(id,e.getMessage());
		}
	}
	@Override
	public int queryCarsPrice(int id, String location) throws RemoteException,
			DeadlockException {
		try {
			return rm.queryCarsPrice(id, location);
		} catch (DeadlockException_Exception e) {
			//e.printStackTrace();
			throw new DeadlockException(id,e.getMessage());
		}
	}
	@Override
	public int queryRoomsPrice(int id, String location) throws RemoteException,
			DeadlockException {
		try {
			return rm.queryRoomsPrice(id, location);
		} catch (DeadlockException_Exception e) {
			//e.printStackTrace();
			throw new DeadlockException(id,e.getMessage());
		}
	}
	@Override
	public boolean reserveFlight(int id, int customer, int flightNumber)
			throws RemoteException, DeadlockException {
		// TODO Auto-generated method stub
		try {
			return rm.reserveFlight(id, customer, flightNumber);
		} catch (DeadlockException_Exception e) {
			// TODO Auto-generated catch block
			throw new DeadlockException(id, e.getMessage());
		}
	}
	@Override
	public boolean reserveCar(int id, int customer, String location)
			throws RemoteException, DeadlockException {
		try {
			return rm.reserveCar(id, customer, location);
		} catch (DeadlockException_Exception e) {
			// TODO Auto-generated catch block
			throw new DeadlockException(id, e.getMessage());
		}
	}
	@Override
	public boolean reserveRoom(int id, int customer, String locationd)
			throws RemoteException, DeadlockException {
		try {
			return rm.reserveRoom(id, customer, locationd);
		} catch (DeadlockException_Exception e) {
			// TODO Auto-generated catch block
			throw new DeadlockException(id, e.getMessage());
		}	
	}
	@Override
	public boolean itinerary(int id, int customer, Vector flightNumbers,
			String location, boolean Car, boolean Room) throws RemoteException {
		return rm.itinerary(id, customer, flightNumbers, location, Car, Room);
	}
	@Override
	public boolean customerExist(int id, int customerID) throws RemoteException {
		return rm.customerExist(id, customerID);
	}
	@Override
	public boolean customerReserveWrite(int id, String key, String location,
			int price, int customerID) throws RemoteException {
		return rm.customerReserveWrite(id, key, location, price, customerID);
	}
	@Override
	public void dump(String RMname) throws RemoteException {
		dump(RMname);
	}
	@Override
	public boolean removeReservation(int id, String reservedkey, int count)
			throws RemoteException, DeadlockException {
		return removeReservation(id, reservedkey, count);
	}
	@Override
	public int start() throws RemoteException {
		
		return rm.start();
	}

	public int startWithId(int TID) throws RemoteException {
		// TODO Auto-generated method stub
		return rm.startWithId(TID);
	}
	@Override
	public boolean commit(int transactionId) throws RemoteException,
			TransactionAbortedException, InvalidTransactionException {
		// TODO Auto-generated method stub
		try {
			return rm.commit(transactionId);
		} catch (InvalidTransactionException_Exception e) {
			// TODO Auto-generated catch block
			throw new InvalidTransactionException(transactionId, e.getMessage());
		} catch (TransactionAbortedException_Exception e) {
			// TODO Auto-generated catch block
			throw new TransactionAbortedException(transactionId, e.getMessage());
		}
	}
	@Override
	public void abort(int transactionId) throws RemoteException,
			InvalidTransactionException {
		try {
			rm.abort(transactionId);
		} catch (InvalidTransactionException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int start(int id){
		return 0;
	}
	@Override
	public boolean shutdown() throws RemoteException {
		// TODO Auto-generated method stub
		return rm.shutdown();
	}
	@Override
	public void realShutdown() throws RemoteException {
		rm.realShutdown();
	}
}
