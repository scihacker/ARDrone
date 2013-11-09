package ardroneLib;

import java.net.InetAddress;

public class Test extends Object {

	public static void main(String[] args) throws Exception {
		InetAddress inet_addr = InetAddress.getByName("192.168.1.1");

        ARDrone ardrone = new ARDrone(inet_addr);
        Thread.sleep(ARDrone.INTERVAL);
        
		ardrone.send_at_cmd("AT*REF=" + ardrone.get_seq() + ",290717696");
	        
        int cnt = 0;
        
        // TODO: put your code here
        // TODO: you can realize a gui program using awt, and put the command into it.
        
        ardrone.do_land();
        
        while (true) {
            Thread.sleep(30); 

		    cnt++;
		    if (cnt >= 5) {  
		    	// avoid ARDRONE_COM_LOST_MASK (need to re-connect for this state)
		    	cnt = 0;
		    	ardrone.send_at_cmd("AT*COMWDG=" + (ardrone.seq++));
		    }
		}
	}
}
