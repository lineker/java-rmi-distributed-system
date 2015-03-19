package TransactionManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Hashtable;

import ResImpl.MiddlewareImpl;

public class TransactionWatcher  extends Thread {

	Hashtable<Integer,Long> timestamps = null;
	MiddlewareImpl MW = null;
	long timeToLive = 20000;//time in miliseconds
	
	public TransactionWatcher(MiddlewareImpl MW)
	{
		log("Watcher initialized");
		this.timestamps = new Hashtable<Integer,Long>();
		this.MW = MW;
	}
	@Override
	public void run(){
		log("Watcher running");
		while(true)
		{
			Enumeration e = timestamps.keys();

		    //iterate through Hashtable keys Enumeration
		    while(e.hasMoreElements())
		    {
		    	Integer key = (Integer)e.nextElement();
		    	long current = System.currentTimeMillis();
		    	Long previous = timestamps.get(key);
		    	if(previous != null)
		    	{
			    	if((current - previous.longValue()) > timeToLive)
			    	{
			    		log("Watcher: need to abort TID: "+key);
			    		//need to abort
			    		try {
							this.MW.abort(key.intValue());
							timestamps.remove(key.intValue());
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InvalidTransactionException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			    	}
		    	}
		    	
		    }
		}
	}
	
	public void reset(int TID)
	{
		long timeInMillis = System.currentTimeMillis();
		timestamps.put(TID, timeInMillis);
		System.out.println("reset timer TID: "+TID);
		log("reset timer TID: "+TID);
	}
	public void remove(int TID)
	{
		timestamps.remove(TID);
	}
	
	private void log(String data)
	{
		 
		File file =new File("watcher-log.txt");

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
}
