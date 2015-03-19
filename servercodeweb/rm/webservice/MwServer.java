package rm.webservice;

import javax.xml.ws.Endpoint;

import ResImpl.MiddlewareImpl;

public class MwServer {
	public static void main(String[] args) {
		
		MiddlewareImpl MwWeb = new MiddlewareImpl();
		
        Endpoint.publish("http://localhost:9898/MwWeb", new RmWeb(MwWeb));
 
        System.out.println("MW service is ready");
    }
}
