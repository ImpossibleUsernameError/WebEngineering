package at.ac.tuwien.big.we16.ue2;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Michael on 14.04.2016.
 */
public class Userpool {

	private LinkedList<User> userpool = new LinkedList<>();

	//public Userpool() {
	//	userpool.add(new User("John Doe", "johnnyboy@gmx.at"));
	//}

	public LinkedList<User> getUserPool(){
		return userpool;
	}

	public User getUser(String email){
		Iterator<User> it = userpool.iterator();
		User user = null;

		while(it.hasNext()){
			user = it.next();
			if(user.getEmail().equals(email)){
				break;
			}
		}
		return user;
	}
}
