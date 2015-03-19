package rm.webservice;

import javax.xml.ws.Endpoint;

import ResImpl.ResourceManagerImpl;

public class RmServer {
	public static void main(String[] args) {
		
		ResourceManagerImpl rmWebFlight = new ResourceManagerImpl(true);
        Endpoint.publish("http://localhost:9899/RmWeb", new RmWeb(rmWebFlight));
        
        ResourceManagerImpl rmWebCar = new ResourceManagerImpl(true);
        Endpoint.publish("http://localhost:8899/RmWeb", new RmWeb(rmWebCar));
        
        ResourceManagerImpl rmWebHotel = new ResourceManagerImpl(true);
        Endpoint.publish("http://localhost:7899/RmWeb", new RmWeb(rmWebHotel));
 
        System.out.println("RM service is ready");
    }
}
