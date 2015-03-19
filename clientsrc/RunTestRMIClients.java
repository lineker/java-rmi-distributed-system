import java.awt.HeadlessException;

public class RunTestRMIClients {
	/**
	 * Initial client id.
	 */
	private static int mClientIds = 0;

	static int nClients;
	static String address_middle;
	static int port_middle;
	public static void main(String[] args) throws HeadlessException, Exception{
		System.out.println("Started Handler tester");
		 nClients = Integer.parseInt(args[0]);
		 address_middle = args[1];
		 port_middle = Integer.parseInt(args[2]);
		if(nClients > 1)
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
		//new Thread(new RMIClient(mClientIds++,port_middle,address_middle,true)).start();
	}
	
	/**
	 * Test with i clients.
	 * @param i The number of clients to test with.
	 */
	public static void testMultipleClients(int i){
		for(int j = 0; j < i; j++){
			//new Thread(new RMIClient(mClientIds++,port_middle,address_middle,false)).start();
		}
	}
}
