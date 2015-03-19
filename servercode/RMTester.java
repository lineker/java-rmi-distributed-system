import java.rmi.RemoteException;

import LockManager.DeadlockException;
import ResImpl.ResourceManagerImpl;
import TransactionManager.InvalidTransactionException;


public class RMTester {

	/**
	 * @param args
	 * @throws RemoteException 
	 * @throws InvalidTransactionException 
	 * @throws DeadlockException 
	 * @throws NumberFormatException 
	 */
	public static void main(String[] args) throws RemoteException, InvalidTransactionException, NumberFormatException, DeadlockException {
		// TODO Auto-generated method stub
		 ResourceManagerImpl rm = new ResourceManagerImpl();
		 int TID = rm.start();
		 System.out.println("TID : " + TID);
		 int cid = rm.newCustomer(TID);
		 rm.addCars(TID, "montreal", 1, 10);
		 rm.addCars(TID, "montreal", 2, 10);
		 rm.dump("Car");
		 System.out.println("----------------------------------------------");
		 rm.abort(TID);
		 rm.dump("Car");
	}

}
