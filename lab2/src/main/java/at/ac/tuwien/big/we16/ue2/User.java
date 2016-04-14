package at.ac.tuwien.big.we16.ue2;

/**
 * Created by Michael on 14.04.2016.
 */
public class User {

	private String username;
	private double budget;
	private int runningAuctions;
	private int wonAuctions;
	private int lostAuctions;
	private String email;

	public User(String username, String email){
		this.username = username;
		this.email = email;
		this.budget = 1500;
		this.runningAuctions = 0;
		this.wonAuctions = 0;
		this.lostAuctions = 0;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
