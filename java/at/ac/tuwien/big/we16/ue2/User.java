package at.ac.tuwien.big.we16.ue2;

import java.util.LinkedList;
import java.util.List;

/**
 * This class represents an User of the Application
 */
public class User {

	private double budget;
	private int runningAuctions;
	private int wonAuctions;
	private int lostAuctions;
	private String email;
	private List<Product> lastSeen = new LinkedList<>();
	private List<Product> auctions = new LinkedList<>();


	public User(){

	}


	public User(String email){
		this.setEmail(email);
		this.setBudget(1500);
		this.setRunningAuctions(0);
		this.setWonAuctions(0);
		this.setLostAuctions(0);
	}

	public List<Product> getAuctions() {
		return auctions;
	}

	public void setAuctions(List<Product> auctions) {
		this.auctions = auctions;
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

	public void setLastSeen(List<Product> lastSeen) {
		this.lastSeen = lastSeen;
	}

	public boolean equals(Object other){
		return other.getClass() == this.getClass() && ((User)other).getEmail().equals(getEmail());
	}

	public int hashCode(){
		int hc = 99;
		int multiplier = 1037;
		return multiplier * (email.hashCode() + hc);
	}

	public String toString(){
		return email + ", " + budget + "," + runningAuctions + ", " + wonAuctions + ", " + lostAuctions;
	}

}

