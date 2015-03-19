package ResImpl;

import ResInterface.*;

class killRm extends Thread {
    ResourceManager rm;


    public killRm (ResourceManager rm) {
        this.rm = rm;

    }

    public void run () {
	try {
	    	this.sleep (1000);
		rm.realShutdown();
		System.exit(0);
	}
	catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }    
}
