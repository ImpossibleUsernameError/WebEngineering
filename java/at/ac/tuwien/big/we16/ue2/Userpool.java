package at.ac.tuwien.big.we16.ue2;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Michael on 14.04.2016.
 */
public class Userpool {

	private List<User> users;
	private static Userpool userPool = null;

	private Userpool() {
		users = new LinkedList<>();
	}

	public List<User> getUserPool(){
		return users;
	}

	public static Userpool getInstance(){
		if(userPool == null){
			userPool= new Userpool();
		}
		return userPool;
	}


	public User getUser(String email){
		Iterator<User> it = users.iterator();

		while(it.hasNext()){
			User user = it.next();
			if(user.getEmail().equals(email)){
				return user;
			}
		}
		return null;
	}

	public void addUser(User user){
		users.add(user);
	}

	@Override
	public String toString() {
		Iterator<User> it = users.iterator();
		String string = "";
		while(it.hasNext()){
			string += it.next() + ", ";
		}
		return string;
	}
}
