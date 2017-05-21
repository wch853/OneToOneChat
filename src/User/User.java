package User;

import java.io.Serializable;

/**
 * ∂®“ÂUser¿‡
 *
 */
@SuppressWarnings("serial")
public class User implements Serializable {
	String id, IP;
	public User(String id, String IP) {
		this.id = id;
		this.IP = IP;
	}
	public String getId() {
		return id;
	}
	public String getIP() {
		return IP;
	}
}

