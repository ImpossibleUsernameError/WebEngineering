package at.ac.tuwien.big.we16.ue2;

import java.util.LinkedList;

/**
 * Created by Michael on 14.04.2016.
 */
public class Userpool {

	private LinkedList<User> userpool;

	public Userpool() {
		userpool.add(new User("John Doe", "johnnyboy@gmx.at"));
	}

	public LinkedList<User> getUserPool(){
		return userpool;
	}
}
