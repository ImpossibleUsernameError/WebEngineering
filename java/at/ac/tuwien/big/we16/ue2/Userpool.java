package at.ac.tuwien.big.we16.ue2;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Michael on 14.04.2016.
 */
public class Userpool {

	private List<User> userpool = new LinkedList<>();

	public Userpool() {
		User user = new User();
		user.setEmail("johnnyboy@gmail.com");
		user.setUsername("John Doe");
		user.setBudget(1500);
		user.setRunningAuctions(0);
		user.setWonAuctions(0);
		user.setLostAuctions(0);
		userpool.add(user);
	}

	public List<User> getUserPool(){
		return userpool;
	}

	public User getUser(String email){
		Iterator<User> it = userpool.iterator();

		while(it.hasNext()){
			User user = it.next();
			if(user.getEmail().equals(email)){
				return user;
			}
		}
		return null;
	}
}
