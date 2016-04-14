package at.ac.tuwien.big.we16.ue2;

import java.time.LocalDate;

/**
 * Created by Michael on 14.04.2016.
 */
public class Product {

	private int id;
	private String name;
	private double price;
	private String maxBidUser;
	private LocalDate expiredDate;
	private boolean expired;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getMaxBidUser() {
		return maxBidUser;
	}

	public void setMaxBidUser(String maxBidUser) {
		this.maxBidUser = maxBidUser;
	}

	public LocalDate getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(LocalDate expiredDate) {
		this.expiredDate = expiredDate;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}
}
