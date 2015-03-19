package RequestHandlerTester;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This is a testing class to simulate a client sending requests to the request handler.
 * The client will submit one request of each type.
 *
 */
public class Client implements Runnable {
	/**
	 * The id of the client.
	 */
	private final int mID;
	
	/**
	 * A constructor to set the id of the client. The ids do not need to be unique
	 * but for testing purposes one might choose to make them unique.
	 * @param i The desired id.
	 */
	public Client(int i){
		mID = i;
	}
	
	private String send(String req)
	{
		Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String resp = null;
        try {
        	//connect and send feedback
            socket = new Socket("mimi", 2012);
            System.out.println("Client " + mID + " connected.");
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            out.println(req);
            resp = in.readLine();
            
            socket.close();
            out.close();
            in.close();
            
        } catch (Exception e) {
            System.err.println("Client " + mID + " error: " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        } finally {
        	if(socket != null && !socket.isClosed()){
				try {
					socket.close();
				} catch (IOException e) {
					System.err.println("Cannot close socket for client " + mID);
				}
        	}
        	
        	if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					System.err.println("Cannot close input stream for client " + mID);
				}
        	}
        	if(out != null)
        		out.close();
        }
        print(resp);
        return resp;
	}
	
	public void print(String resp)
	{
		if(resp != null)
        	System.out.println("Client " + mID + " response : " + resp);
        else
        	System.err.println("Client " + mID + " error: ");
	}
	
	@Override
	public void run() {
		//create sockets, streams, and required json objects.
		
        String req = null;
        String resp = null;
        System.out.println("Start Client " + mID);
        
            
            req = "newcustomer,1";
          
            resp = send(req);
            String custId = resp;
            
            req = "addFlight,1,"+(new Integer(mID)).toString()+",50,50";
            
            resp = send(req);
            
            req = "addRooms,1,montreal,50,50";
            
            resp = send(req);
            
            req = "addCars,1,montreal,50,50";
            
            resp = send(req);

            
            //itinerary(Id,customer,flightNumbers,location,Car,Room)
            req = "itinerary,1,"+custId+","+(new Integer(mID)).toString()+",montreal,true,true";
            
            resp = send(req);
            
            req = "queryCustomerInfo,1,"+custId;
            
            resp = send(req);

        
	}
}
