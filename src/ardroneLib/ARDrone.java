package ardroneLib;

import java.net.*;

public class ARDrone extends Object {
	
    static final int NAVDATA_PORT = 5554;
    static final int VIDEO_PORT   = 5555;
    static final int AT_PORT 	  = 5556;
    static final int INTERVAL	  = 100;
    
    private Pcmd pc;
    int seq = 1;
    //private int seq_last = 1;
	//private String at_cmd_last = "";
	private InetAddress inet_addr;
	private DatagramSocket socket_at;
    
	// TODO: Unknown parameters
	public ARDrone(InetAddress inet_addr) throws Exception {

		pc = new Pcmd();
		socket_at = new DatagramSocket(ARDrone.AT_PORT);
    	socket_at.setSoTimeout(3000);
        
		this.inet_addr = inet_addr;

		send_at_cmd("AT*PMODE=" + get_seq() + ",2");	
		Thread.sleep(INTERVAL);
		send_at_cmd("AT*MISC=" + get_seq() + ",2,20,2000,3000");
		Thread.sleep(INTERVAL);
		send_at_cmd("AT*REF=" + get_seq() + ",290717696");
		Thread.sleep(INTERVAL);
		send_at_cmd("AT*COMWDG=" + get_seq());
		Thread.sleep(INTERVAL);
		send_at_cmd("AT*CONFIG=" + get_seq() + ",\"control:altitude_max\",\"2000\""); //altitude max 2m
		Thread.sleep(INTERVAL);
		send_at_cmd("AT*CONFIG=" + get_seq() + ",\"control:control_level\",\"0\""); //0:BEGINNER, 1:ACE, 2:MAX
		Thread.sleep(INTERVAL);
		send_at_cmd("AT*CONFIG=" + get_seq() + ",\"general:navdata_demo\",\"TRUE\"");
		Thread.sleep(INTERVAL);
		send_at_cmd("AT*CONFIG=" + get_seq() + ",\"general:video_enable\",\"TRUE\"");
		Thread.sleep(INTERVAL);
		send_at_cmd("AT*CONFIG=" + get_seq() + ",\"pic:ultrasound_freq\",\"8\"");
		Thread.sleep(INTERVAL);
		send_at_cmd("AT*FTRIM=" + get_seq()); //flat trim
		Thread.sleep(INTERVAL);

		send_at_cmd("AT*REF=" + get_seq() + ",290717696");
		Thread.sleep(INTERVAL);
	    send_pcmd(pc);
		Thread.sleep(INTERVAL);
		send_at_cmd("AT*REF=" + get_seq() + ",290717696");
	}

	public synchronized void send_at_cmd(String at_cmd) throws Exception {
		// TODO: make any sense?
    	// at_cmd_last = at_cmd;
    	byte[] buf_snd = (at_cmd + "\r").getBytes();
    	DatagramPacket packet_snd = new DatagramPacket(buf_snd, buf_snd.length, inet_addr, ARDrone.AT_PORT);
    	socket_at.send(packet_snd);
    }
	
    public void send_pcmd(Pcmd pc) throws Exception {
    	this.pc = pc;
    	send_at_cmd("AT*PCMD=" + get_seq() + "," + pc.enable + "," + Pcmd.intOfFloat(pc.roll) + "," + Pcmd.intOfFloat(pc.pitch)
    			+ "," + Pcmd.intOfFloat(pc.gaz) + "," + Pcmd.intOfFloat(pc.yaw));
    }
    
    public void do_take_off() throws Exception {
    	send_at_cmd("AT*REF=" + get_seq() + ",290718208");
    }
    
    public void do_land() throws Exception {
    	send_at_cmd("AT*REF=" + get_seq() + ",290717696");
    }
    
    public void do_toggle_emergency() throws Exception {
    	send_at_cmd("AT*REF=" + get_seq() + ",290717952");
    }
    public Pcmd get_pcmd() {
    	return pc;
    }
    
	synchronized int get_seq() {
    	return seq++;
    }
}
