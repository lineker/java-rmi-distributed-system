import java.awt.HeadlessException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import LockManager.DeadlockException;
import ResInterface.ResourceManager;
import TransactionManager.InvalidTransactionException;
import TransactionManager.TransactionAbortedException;


public class RMIClient{
	
	/**
	 * The id of the client.
	 */
	private static int mID;
	private static String server;
	private  static int port;
	static ResourceManager rm = null;
	static boolean warmup = false;
	/**
	 * A constructor to set the id of the client. The ids do not need to be unique
	 * but for testing purposes one might choose to make them unique.
	 * @param i The desired id.
	 */

	public static void main(String[] args) throws HeadlessException, Exception{
		// TODO Auto-generated method stub
		try 
		{
			mID  = Integer.parseInt(args[0]);
			server = args[1];
			port = Integer.parseInt(args[2]);
			warmup = Boolean.parseBoolean(args[3]);
		    // get a reference to the rmiregistry
		    Registry registry = LocateRegistry.getRegistry(server,port);
		    // get the proxy and the remote reference by rmiregistry lookup
		    rm = (ResourceManager) registry.lookup("MyGroup25");
		    
		    if(rm!=null)
			{
			    System.out.println("Successful");
			    System.out.println("Connected to RM");
			}
		    else
			{
			    System.out.println("Unsuccessful");
			}
		    // make call on remote method
		} 
	    catch (Exception e) 
		{	
		    System.err.println("Client exception: " + e.toString());
		    e.printStackTrace();
		}
	    
	    if (System.getSecurityManager() == null) {
		System.setSecurityManager(new RMISecurityManager());
	    }
	    
	    if(warmup)
	    	warmup();
	    runCmds();
	}
	
	private static void warmup()
	{
		try 
		{
			int tid = rm.start();
			for (int i = 1; i < 1001; i++) {
				rm.addFlight(tid,i,50,10);
				
			}
			for (int i = 1; i < 1001; i++) {
				rm.queryFlight(tid, i);
			}
			rm.commit(tid);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransactionAbortedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidTransactionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DeadlockException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void runCmds()
	{
		
		try 
		{
			
			int tid = rm.start();
			Long start = System.currentTimeMillis();
			rm.addFlight(tid,1,50,10);
			rm.addFlight(tid,1,50,10);
			rm.addFlight(tid,1,50,10);
			Long end = System.currentTimeMillis();
			rm.commit(tid);
			log(new Long(end-start).toString(), mID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransactionAbortedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidTransactionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DeadlockException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void log(String data, int clientid)
	{
		 
		File file =new File("clientid-"+new Integer(clientid).toString()+".txt");

		//if file doesnt exists, then create it
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//true = append file
		FileWriter fileWritter;
		try {
			fileWritter = new FileWriter(file.getName(),true);
		
	        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	        bufferWritter.write(data + '\n');
	        bufferWritter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void cooldown()
	{
		
	}

}
