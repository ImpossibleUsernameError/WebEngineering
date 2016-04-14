package at.ac.tuwien.big.we16.ue2;

import java.util.LinkedList;

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
	private LinkedList<Product> lastSeen;

	public User(String username, String email){
		this.username = username;
		this.email = email;
		this.budget = 1500;
		this.runningAuctions = 0;
		this.wonAuctions = 0;
		this.lostAuctions = 0;
		lastSeen = new LinkedList<>();
	}

	public String getUsername() {
		return username;
	}

	public double getBudget() {
		return budget;
	}

	public int getRunningAuctions() {
		return runningAuctions;
	}

	public int getWonAuctions() {
		return wonAuctions;
	}

	public int getLostAuctions() {
		return lostAuctions;
	}

	public String getEmail() {
		return email;
	}

	public LinkedList<Product> getLastSeen() {
		return lastSeen;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void addProductToLastSeen(Product p){
		lastSeen.add(p);
	}
}
