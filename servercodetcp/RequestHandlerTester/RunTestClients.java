package RequestHandlerTester;

import java.awt.HeadlessException;
import java.util.LinkedList;


/**
 * A tester to handle the load on the reqeust handler. Run many different clients at once.
 *
 */
public class RunTestClients {
	
	/**
	 * Initial client id.
	 */
	private static int mClientIds = 0;


	public static void main(String[] args) throws HeadlessException, Exception{
		System.out.println("Started Handler tester");
		if(args.length > 0)
			testMultipleClients(Integer.parseInt(args[0]));
		else
		testOneClient();
		//testMultipleClients(2);
		//testMultipleClients(10);
		//testMultipleClients(50);
	}
	
	/**
	 * Test with a single client.
	 */
	public static void testOneClient(){
		new Thread(new Client(mClientIds++)).start();
	}
	
	/**
	 * Test with i clients.
	 * @param i The number of clients to test with.
	 */
	public static void testMultipleClients(int i){
		for(int j = 0; j < i; j++){
			new Thread(new Client(mClientIds++)).start();
		}
	}
}
