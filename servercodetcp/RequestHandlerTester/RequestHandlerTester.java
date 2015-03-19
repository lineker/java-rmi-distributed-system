package RequestHandlerTester;

import java.util.LinkedList;
import java.util.List;

import ResImpl.RequestHandler;

/**
 * A test class that runs the request handler. This will start the request handler on a 
 * default 2011 port.
 * 
 *
 */
public class RequestHandlerTester {

	public static void main(String[] args) throws Exception {
		
		//start RM
		RequestHandler handler = new RequestHandler(Integer.parseInt(args[0]), args[1], Integer.parseInt(args[2]));
		handler.start();
	}
}
