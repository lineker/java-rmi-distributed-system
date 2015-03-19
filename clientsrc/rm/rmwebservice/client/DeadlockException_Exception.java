
package rm.rmwebservice.client;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebFault(name = "DeadlockException", targetNamespace = "http://webservice.rm/")
public class DeadlockException_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private DeadlockException faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public DeadlockException_Exception(String message, DeadlockException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public DeadlockException_Exception(String message, DeadlockException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: rm.rmwebservice.client.DeadlockException
     */
    public DeadlockException getFaultInfo() {
        return faultInfo;
    }

}
