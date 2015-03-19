package rm.webservice;

import java.rmi.RemoteException;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import LockManager.DeadlockException;
import ResInterface.ResourceManager;
import TransactionManager.InvalidTransactionException;
import TransactionManager.TransactionAbortedException;

@WebService
public class RmWeb implements ResourceManager{

	ResourceManager rm = null;

	public RmWeb(ResourceManager myRM) {
		rm = myRM;
	}

	@WebMethod
	public boolean addFlight(int id, int flightNum, int flightSeats,
			int flightPrice) throws RemoteException, DeadlockException {
		return rm.addFlight(id, flightNum, flightSeats, flightPrice);
	}

	@WebMethod
	public boolean addCars(int id, String location, int numCars, int price)
			throws RemoteException, DeadlockException {
		// TODO Auto-generated method stub
		return rm.addCars(id, location, numCars, price);
	}

	@WebMethod
	public boolean addRooms(int id, String location, int numRooms, int price)
			throws RemoteException, DeadlockException {
		// TODO Auto-generated method stub
		return rm.addRooms(id, location, numRooms, price);
	}

	@WebMethod
	public int newCustomer(int id) throws RemoteException,
			NumberFormatException, DeadlockException {
		return rm.newCustomer(id);
	}

	@WebMethod
	public boolean newCustomerWithId(int id, int cid) throws RemoteException,
			DeadlockException {
		// TODO Auto-generated method stub
		return rm.newCustomerWithId(id, cid);
	}

	@WebMethod
	public boolean deleteFlight(int id, int flightNum) throws RemoteException,
			DeadlockException {
		// TODO Auto-generated method stub
		return rm.deleteFlight(id, flightNum);
	}

	@WebMethod
	public boolean deleteCars(int id, String location) throws RemoteException,
			DeadlockException {
		// TODO Auto-generated method stub
		return rm.deleteCars(id, location);
	}

	@WebMethod
	public boolean deleteRooms(int id, String location) throws RemoteException,
			DeadlockException {
		// TODO Auto-generated method stub
		return rm.deleteRooms(id, location);
	}

	@WebMethod
	public boolean deleteCustomer(int id, int customer) throws RemoteException,
			DeadlockException {
		// TODO Auto-generated method stub
		return rm.deleteCustomer(id, customer);
	}

	@WebMethod
	public int queryFlight(int id, int flightNumber) throws RemoteException,
			DeadlockException {
		// TODO Auto-generated method stub
		return rm.queryFlight(id, flightNumber);
	}

	@WebMethod
	public int queryCars(int id, String location) throws RemoteException,
			DeadlockException {
		// TODO Auto-generated method stub
		return rm.queryCars(id, location);
	}

	@WebMethod
	public int queryRooms(int id, String location) throws RemoteException,
			DeadlockException {
		// TODO Auto-generated method stub
		return rm.queryRooms(id, location);
	}

	@WebMethod
	public String queryCustomerInfo(int id, int customer)
			throws RemoteException, DeadlockException {
		// TODO Auto-generated method stub
		return rm.queryCustomerInfo(id, customer);
	}

	@WebMethod
	public int queryFlightPrice(int id, int flightNumber)
			throws RemoteException, DeadlockException {
		// TODO Auto-generated method stub
		return rm.queryFlightPrice(id, flightNumber);
	}

	@WebMethod
	public int queryCarsPrice(int id, String location) throws RemoteException,
			DeadlockException {
		// TODO Auto-generated method stub
		return rm.queryCarsPrice(id, location);
	}

	@WebMethod
	public int queryRoomsPrice(int id, String location) throws RemoteException,
			DeadlockException {
		// TODO Auto-generated method stub
		return rm.queryRoomsPrice(id, location);
	}

	@WebMethod
	public boolean reserveFlight(int id, int customer, int flightNumber)
			throws RemoteException, DeadlockException {
		System.out.println("inside RmWeb: reserveFlight");
		return rm.reserveFlight(id, customer, flightNumber);
	}

	@WebMethod
	public boolean reserveCar(int id, int customer, String location)
			throws RemoteException, DeadlockException {
		// TODO Auto-generated method stub
		return rm.reserveCar(id, customer, location);
	}

	@WebMethod
	public boolean reserveRoom(int id, int customer, String locationd)
			throws RemoteException, DeadlockException {
		// TODO Auto-generated method stub
		return rm.reserveRoom(id, customer, locationd);
	}

	@WebMethod
	public boolean itinerary(int id, int customer, Vector flightNumbers,
			String location, boolean Car, boolean Room) throws RemoteException {
		// TODO Auto-generated method stub
		return rm.itinerary(id, customer, flightNumbers, location, Car, Room);
	}

	@WebMethod
	public boolean customerExist(int id, int customerID) throws RemoteException {
		// TODO Auto-generated method stub
		return rm.customerExist(id, customerID);
	}

	@WebMethod
	public boolean customerReserveWrite(int id, String key, String location,
			int price, int customerID) throws RemoteException {
		// TODO Auto-generated method stub
		return rm.customerReserveWrite(id, key, location, price, customerID);
	}

	@WebMethod
	public void dump(String RMname) throws RemoteException {
		// TODO Auto-generated method stub
		rm.dump(RMname);
	}

	@WebMethod
	public boolean removeReservation(int id, String reservedkey, int count)
			throws RemoteException, DeadlockException {
		// TODO Auto-generated method stub
		return rm.removeReservation(id, reservedkey, count);
	}

	@WebMethod
	public int start() throws RemoteException {
		return rm.start();
	}
	
	@WebMethod
	public int startWithId(int TID) throws RemoteException {
		return rm.startWithId(TID);
	}

	@WebMethod
	public boolean commit(int transactionId) throws RemoteException,
			TransactionAbortedException, InvalidTransactionException {
		// TODO Auto-generated method stub
		return rm.commit(transactionId);
	}

	@WebMethod
	public void abort(int transactionId) throws RemoteException,
			InvalidTransactionException {
		// TODO Auto-generated method stub
		rm.abort(transactionId);

	}

	@WebMethod
	public boolean shutdown() throws RemoteException {
		// TODO Auto-generated method stub
		return rm.shutdown();
	}

	@WebMethod
	public void realShutdown() throws RemoteException {
		// TODO Auto-generated method stub
		rm.realShutdown();
	}

}
