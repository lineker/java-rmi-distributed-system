package TransactionManager;

import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Hashtable;

import rm.rmwebservice.client.InvalidTransactionException_Exception;
import rm.rmwebservice.client.TransactionAbortedException_Exception;

import ResImpl.RMHashtable;
import ResInterface.ResourceManager;

public class TransactionManager {

	protected Hashtable<Integer,Hashtable<String,Integer>> m_transactionManager = new Hashtable<Integer,Hashtable<String,Integer>>();
	
	public TransactionManager()
	{
		
	}
	
	public int countTransactions()
	{
		return m_transactionManager.size();
	}
	
	public boolean containsTransaction(int TID)
	{
		return m_transactionManager.containsKey(TID);
	}
	
	public int start()
	{
		int TID =  m_transactionManager.size()+1;
		//make sure is unique
		while(m_transactionManager.containsKey(TID))
		{
			TID++;
		}
		m_transactionManager.put(TID, new Hashtable<String,Integer>());
		return TID;
	}
	
	public boolean commit(int TID,RMHashtable rms) throws RemoteException, TransactionAbortedException, InvalidTransactionException
	{
		boolean result = false;
		Hashtable<String,Integer> ht = m_transactionManager.get(TID);
		if(ht != null)
		{
			//loop and send commit to each RM that 
			Enumeration e = ht.keys();
			
			
		    //iterate through Hashtable keys Enumeration
		    while(e.hasMoreElements())
		    {
		    	String rm_name = (String)e.nextElement();
		    	rm.rmwebservice.client.RmWeb r = (rm.rmwebservice.client.RmWeb) rms.get(rm_name);
				if(r != null)
				{
					try {
						result  = r.commit(TID);
					} catch (InvalidTransactionException_Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (TransactionAbortedException_Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if(!result)
				{
					System.out.println("RM : "+rm_name+" failed to commit");
					return false; //some RM failed to commit
				}
		  	}
		}
	    
	  //clean transaction reference
		Object n = m_transactionManager.remove(TID);
		if(n == null)
			return false;
		return true;
	}
	
	public void abort(int TID, RMHashtable rms) throws RemoteException, InvalidTransactionException
	{
		System.out.println("TM: Aborting Transaction : "+ TID);
		Hashtable<String, Integer> ht = m_transactionManager.get(TID);
		if(ht != null)
		{
			//loop and send abort to each RM that 
			Enumeration e = ht.keys();
			boolean result = false;
		    //iterate through Hashtable keys Enumeration
		    while(e.hasMoreElements())
		    {
		    	String rm_name = (String)e.nextElement();
		    	rm.rmwebservice.client.RmWeb r = (rm.rmwebservice.client.RmWeb) rms.get(rm_name);
		    	System.out.println(rm_name+" : Aborting Transaction : "+ TID);
				if(r != null)
				{
					try {
						r.abort(TID);
					} catch (InvalidTransactionException_Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						throw new  InvalidTransactionException(TID, e1.getMessage());
					}
				}
		  	}
		}
		else
		{
			System.out.println("ht is null TID:"+TID);
		}
	    System.out.println("DONE - TM: Aborting Transaction : "+ TID);
	  //remove transaction from transaction Manager
		m_transactionManager.remove(TID);
	}
	
	public void shutdown(RMHashtable rms) throws RemoteException
	{

		//loop and send abort to each RM that 
		Enumeration e = rms.keys();
		boolean result = false;
	    //iterate through Hashtable keys Enumeration
	    while(e.hasMoreElements())
	    {
	    	String rmname = (String)e.nextElement();
	    	rm.rmwebservice.client.RmWeb r = (rm.rmwebservice.client.RmWeb) rms.get(rmname);
			if(r != null)
			{
				r.shutdown();
			}
	    
	    }
	}
	
	public int getTransaction(int TID,String RMName, RMHashtable rms) throws RemoteException
	{
		int rTID = -1;
		synchronized (m_transactionManager) {
			Hashtable<String,Integer> ht = m_transactionManager.get(TID);
			if(ht == null)
			{
				ht = new Hashtable<String,Integer>();
				m_transactionManager.put(TID,ht);
			}
				Enumeration e = ht.keys();
				boolean result = false;
			    //iterate through Hashtable keys Enumeration
			    while(e.hasMoreElements())
			    {
			    	String rm_name = (String)e.nextElement();
			    	
			    	if(rm_name == RMName)
			    	{
			    		return TID;
	
			    	}
			    	
			  	}
			    
			    
			    rm.rmwebservice.client.RmWeb r = (rm.rmwebservice.client.RmWeb) rms.get(RMName);
				if(r != null)
				{
					rTID = r.startWithId(TID); //enforce TID to be the same
					ht.put(RMName,rTID);
					System.out.println("new transaction on "+RMName+" TID: "+rTID);
				}
			
			return rTID;
			
		}
		
	}
	
}
