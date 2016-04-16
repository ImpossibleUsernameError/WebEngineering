package at.ac.tuwien.big.we16.ue2;

import at.ac.tuwien.big.we16.ue2.productdata.JSONDataLoader;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * All Products are created in this class from the Json
 */
public class ProductPool {

	private List<Product> products;

	public ProductPool() {
		products = new LinkedList<>();
		init();
	}

	/**
	 * Adds all products to the member variable products, expired ones are added at the end
	 */
	private void init() {
		JSONDataLoader loader = new JSONDataLoader();
		List<Product> expiredOnes = new LinkedList<>();
		//Go through all music products, set the values and add it to the list
		for (int i = 0; i < loader.getMusic().length; i++) {
			JSONDataLoader.Music m = loader.getMusic()[i];
			Product p = new Product();
			p.setId(m.getId());
			p.setName(m.getAlbum_name());
			p.setImg(m.getImg());
			p.setExpiredTime(LocalDateTime.parse(m.getEnd_time()));
			if (p.getExpiredTime().isAfter(LocalDateTime.now())) {
				p.setExpired(false);
			}
			p.setCategory("Musik");
			if (p.isExpired()) {
				expiredOnes.add(p);
			} else {
				products.add(p);
			}
		}
		for (JSONDataLoader.Book b : loader.getBooks()) {
			Product p = new Product();
			p.setId(b.getId());
			p.setName(b.getTitle());
			p.setImg(b.getImg());
			p.setExpiredTime(LocalDateTime.parse(b.getEnd_time()));
			if (p.getExpiredTime().isAfter(LocalDateTime.now())) {
				p.setExpired(false);
			}
			p.setCategory("Buch");
			if (p.isExpired()) {
				expiredOnes.add(p);
			} else {
				products.add(p);
			}
		}
		for (JSONDataLoader.Movie m : loader.getFilms()) {
			Product p = new Product();
			p.setId(m.getId());
			p.setName(m.getTitle());
			p.setImg(m.getImg());
			p.setExpiredTime(LocalDateTime.parse(m.getEnd_time()));
			if (p.getExpiredTime().isAfter(LocalDateTime.now())) {
				p.setExpired(false);
			}
			p.setCategory("Film");
			if (p.isExpired()) {
				expiredOnes.add(p);
			} else {
				products.add(p);
			}
		}
		for (Product p : expiredOnes) {
			products.add(p);
		}
	}

	public List<Product> getProducts() {
		return products;
	}
}
