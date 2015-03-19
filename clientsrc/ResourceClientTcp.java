import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Vector;

import LockManager.DeadlockException;
import ResInterface.ResourceManager;
import TransactionManager.InvalidTransactionException;
import TransactionManager.TransactionAbortedException;



public class ResourceClientTcp implements ResourceManager {

	Socket socket = null;
	PrintWriter out = null;
	BufferedReader in = null;
	
	String server = null;
	int port = -1;
	/**
	 * The id of the client.
	 */
	private final int mID;
	
	public ResourceClientTcp(int i, String server, int port)
	{
		this.server = server;
		this.port = port;
		mID = i;
	}
	
	private String send(String methodName, Object[] args)
	{
		try {
			System.out.println("opening socket to-> "+this.server+":"+this.port);
			//connect and send feedback
	        socket = new Socket(this.server, this.port);
	        System.out.println("Client " + mID + " connected.");
	        out = new PrintWriter(socket.getOutputStream(), true);
	        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        
	        System.out.println("Client " + mID + " sending : "+methodName);
	        //req = new RequestJson(RequestType.FEEDBACK, "TEST_CLIENT", null,
	        //		"Feedback test from client " + mID, null);
	        
	        //out.println(gson.toJson(req));
	        String req = methodName +",";
	        for (int i = 0; i < args.length; i++) {
				if( methodName == "itinerary" ) {
					if( i == 2 ){
						Vector v = (Vector) args[i];
						//System.out.println("PLEASE: " + v.get(0).toString());
						String flights = "";
						for(int r=0; r < v.size(); r++) {
							if( r == (v.size()-1) )
								flights += v.get(r);
							else
								flights += v.get(r) + ",";
						}			
						//strings = Arrays.toString(strings);
						//String flights = Arrays.toString(v);
						req += flights + ",";				
					}
					else
						req += args[i].toString() + ",";
				}
				else
					req += args[i].toString() + ",";
			}
	        System.out.println("client sending : "+req);
	        out.println(req);
	        String response = in.readLine();
	        
			socket.close();
			out.close();
		    in.close();
		    return response;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
		return null;
	}
	
	@Override
	public boolean addFlight(int id, int flightNum, int flightSeats,
			int flightPrice) throws RemoteException {
		Object[] a = {id,flightNum,flightSeats,flightPrice};
		String response = send("addFlight", a);
		return Boolean.parseBoolean(response);
	}

	@Override
	public boolean addCars(int id, String location, int numCars, int price)
			throws RemoteException {
		Object[] a = {id,location,numCars,price};
		String response = send("addCars", a);
		return Boolean.parseBoolean(response);
	}

	@Override
	public boolean addRooms(int id, String location, int numRooms, int price)
			throws RemoteException {
		Object[] a = {id,location,numRooms,price};
		String response = send("addRooms", a);
		return Boolean.parseBoolean(response);		
	}

	@Override
	public int newCustomer(int id) throws RemoteException {
		Object[] a = {id};
		String response = send("newCustomer", a);
		return Integer.parseInt(response);
	}

	@Override
	public boolean deleteFlight(int id, int flightNum) throws RemoteException {
		Object[] a = {id,flightNum};
		String response = send("deleteFlight", a);
		return Boolean.parseBoolean(response);
	}

	@Override
	public boolean deleteCars(int id, String location) throws RemoteException {
		Object[] a = {id,location};
		String response = send("deleteCars", a);
		return Boolean.parseBoolean(response);
	}

	@Override
	public boolean deleteRooms(int id, String location) throws RemoteException {
		Object[] a = {id,location};
		String response = send("deleteRooms", a);
		return Boolean.parseBoolean(response);
	}

	@Override
	public boolean deleteCustomer(int id, int customer) throws RemoteException {
		Object[] a = {id,customer};
		String response = send("deleteCustomer", a);
		return Boolean.parseBoolean(response);
	}

	@Override
	public int queryFlight(int id, int flightNumber) throws RemoteException {
		Object[] a = {id,flightNumber};
		String response = send("queryFlight", a);
		return Integer.parseInt(response);
	}

	@Override
	public int queryCars(int id, String location) throws RemoteException {
		Object[] a = {id,location};
		String response = send("queryCars", a);
		return Integer.parseInt(response);
	}

	@Override
	public int queryRooms(int id, String location) throws RemoteException {
		Object[] a = {id,location};
		String response = send("queryRooms", a);
		return Integer.parseInt(response);
	}

	@Override
	public String queryCustomerInfo(int id, int customer)
			throws RemoteException {
		Object[] a = {id,customer};
		String response = send("queryCustomerInfo", a);
		return response;
	}

	@Override
	public int queryFlightPrice(int id, int flightNumber)
			throws RemoteException {
		Object[] a = {id,flightNumber};
		String response = send("queryFlightPrice", a);
		return Integer.parseInt(response);
	}

	@Override
	public int queryCarsPrice(int id, String location) throws RemoteException {
		Object[] a = {id,location};
		String response = send("queryCarsPrice", a);
		return Integer.parseInt(response);
	}

	@Override
	public int queryRoomsPrice(int id, String location) throws RemoteException {
		Object[] a = {id,location};
		String response = send("queryRoomsPrice", a);
		return Integer.parseInt(response);
	}

	@Override
	public boolean reserveFlight(int id, int customer, int flightNumber)
			throws RemoteException {
		Object[] a = {id,customer,flightNumber};
		String response = send("reserveFlight", a);
		return Boolean.parseBoolean(response);

	}

	@Override
	public boolean reserveCar(int id, int customer, String location)
			throws RemoteException {
		Object[] a = {id,customer,location};
		String response = send("reserveCar", a);
		return Boolean.parseBoolean(response);
	}

	@Override
	public boolean reserveRoom(int id, int customer, String locationd)
			throws RemoteException {
		Object[] a = {id,customer,locationd};
		String response = send("reserveRoom", a);
		return Boolean.parseBoolean(response);
	}

	@Override
	public boolean itinerary(int id, int customer, Vector flightNumbers,
			String location, boolean Car, boolean Room) throws RemoteException {
		Object[] a = {id,customer,flightNumbers,location,Car,Room};
		String response = send("itinerary", a);
		return Boolean.parseBoolean(response);
	}

	@Override
	public boolean customerExist(int id, int customerID) throws RemoteException {
		Object[] a = {id,customerID};
		String response = send("customerExist", a);
		return Boolean.parseBoolean(response);
	}

	@Override
	public boolean customerReserveWrite(int id, String key, String location,
			int price, int customerID) throws RemoteException {
		Object[] a = {id,key,location,price,customerID};
		String response = send("customerReserveWrite", a);
		return Boolean.parseBoolean(response);
	}

	@Override
	public void dump(String RMname) throws RemoteException {
		Object[] a = {RMname};
		String response = send("dump", a);
	}
	
	public boolean removeReservation(int id, String s,int c) 
	{
		return true;
	}

	@Override
	public int start() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean commit(int transactionId) throws RemoteException,
			TransactionAbortedException, InvalidTransactionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void abort(int transactionId) throws RemoteException,
			InvalidTransactionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean shutdown() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	public void realShutdown() throws RemoteException {

	}

	@Override
	public boolean newCustomerWithId(int id, int cid) throws RemoteException,
			DeadlockException {
		Object[] a = {id,cid};
		String response = send("newCustomer", a);
		return Boolean.parseBoolean(response);
	}

	@Override
	public int startWithId(int TID) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}
}
