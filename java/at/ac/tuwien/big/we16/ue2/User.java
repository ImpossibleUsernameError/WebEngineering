package at.ac.tuwien.big.we16.ue2;

import java.util.LinkedList;
import java.util.List;

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
	private List<Product> lastSeen = new LinkedList<>();

	/*public User(String username, String email){
		this.username = username;
		this.email = email;
		this.budget = 1500;
		this.runningAuctions = 0;
		this.wonAuctions = 0;
		this.lostAuctions = 0;
	}*/

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public double getBudget() {
		return budget;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	public int getRunningAuctions() {
		return runningAuctions;
	}

	public void setRunningAuctions(int runningAuctions) {
		this.runningAuctions = runningAuctions;
	}

	public int getWonAuctions() {
		return wonAuctions;
	}

	public void setWonAuctions(int wonAuctions) {
		this.wonAuctions = wonAuctions;
	}

	public int getLostAuctions() {
		return lostAuctions;
	}

	public void setLostAuctions(int lostAuctions) {
		this.lostAuctions = lostAuctions;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Product> getLastSeen() {
		return lastSeen;
	}

}