package ardroneLib;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Pcmd extends Object {
	public int enable;
	public float roll, pitch, gaz, yaw;
	
	private static ByteBuffer bb = ByteBuffer.allocate(4);  
	private static FloatBuffer fb = bb.asFloatBuffer();
    private static IntBuffer ib = bb.asIntBuffer();
    
	public Pcmd() {
		roll = pitch = gaz = yaw = 0f;
		enable = 0;
	}
	
	public Pcmd(int enable, float roll, float pitch, float gaz, float yaw) {
		this.enable = enable;
		this.roll = roll;
		this.pitch = pitch;
		this.gaz = gaz;
		this.yaw = yaw;
	}
	
	public Pcmd(Pcmd another) {
		this.enable = another.enable;
		this.roll = another.roll;
		this.pitch = another.pitch;
		this.gaz = another.gaz;
		this.yaw = another.yaw;
	}
	
	public static int intOfFloat(float f) {
    	fb.put(0, f);
        return ib.get(0);
    }
	
	public void set_hover() {
    	roll = pitch = gaz = yaw = 0f;
		enable = 1;
	}
	
    public void set_up(float speed) {
		gaz = speed;
    }
    
    public void set_forward(float speed) {
		pitch = speed;
    }
    
    public void set_go_right(float speed) {
    	roll = speed;
    }
    
    public void set_rotate_right(float speed) {
    	yaw = speed;
    }
    
}
