package RequestHandlerTester;

import java.util.LinkedList;
import java.util.List;

import ResImpl.RMHashtable;
import ResImpl.RequestHandler;

/**
 * A test class that runs the request handler. This will start the request handler on a 
 * default 2011 port.
 * 
 *
 */
public class MiddleTester {

	public static void main(String[] args) throws Exception {
		
		int port = Integer.parseInt(args[0]);
		RMHashtable rms = new RMHashtable();
		for (int i = 1; i < args.length; i++) {
			String[] rmsvalues = args[i].split(":");
			if(rmsvalues.length != 3)
			{
				System.out.println("Usage MiddleTester Flight:ip:port");
				break;
			}
			else
			{
				Object[] r = {rmsvalues[1],Integer.parseInt(rmsvalues[2])};
				rms.put(rmsvalues[0],r);
			}
		}
		//start middleware
		
	    RequestHandler handlermid = new RequestHandler(2012,rms);
		handlermid.start();
	}
}
