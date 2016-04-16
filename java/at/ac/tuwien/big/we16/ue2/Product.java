package at.ac.tuwien.big.we16.ue2;
import java.time.LocalDateTime;

/**
 * Created by Michael on 14.04.2016.
 */
public class Product {

	private String id = "";
	private String name = "";
	private String img = "";
	private double price = 0;
	private String maxBidUser = "";
	private LocalDateTime expiredTime = null;
	private boolean expired = true;
	private String category = "";

	public String getCategory() {
		return category;
	}

	public LocalDateTime getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(LocalDateTime expiredTime) {
		this.expiredTime = expiredTime;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
	
	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	@Override
	public String toString() {
		return id + ", " + name + ", " + category + ", " + price + ", " + maxBidUser + ", " + expiredTime + ", " + expiredTime;
	}

	public String getFormattedEndtime(){
		return expiredTime.getYear() + "," + expiredTime.getMonthValue() + "," + expiredTime.getDayOfMonth() + "," + expiredTime.getHour() + "," +
				expiredTime.getMinute() + "," + expiredTime.getSecond() + "," + expiredTime.getNano()/1000000;
	}
	
}
