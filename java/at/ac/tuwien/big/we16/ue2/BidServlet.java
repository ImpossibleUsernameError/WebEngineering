package at.ac.tuwien.big.we16.ue2;

import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet for Bidding on a Product
 */
public class BidServlet extends HttpServlet {

	@Override
	public void init() throws ServletException {
		super.init();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		System.out.println("user: " + user + ", newP: " + request.getParameter("newP") + ", prodId: " + request.getParameter("prodId"));
		double newPrice = Double.parseDouble(request.getParameter("newP"));
		Product p = ProductPool.getInstance().getProductById(request.getParameter("prodId"));
		double oldPrice = p.getPrice();
		System.out.println("new: " + newPrice + ", old: " + p.getPrice());
		if(newPrice > p.getPrice() && user != null && (user.getBudget() - newPrice) >= 0){
			if(p.getMaxBidUser().equals(user.getEmail())) {
				user.setBudget(user.getBudget() - newPrice + oldPrice);
			}
			else {
				user.setBudget(user.getBudget() - newPrice);
			}
			p.setPrice(newPrice);
			p.setMaxBidUser(user.getEmail());
			System.out.println(ProductPool.getInstance().getProductById(p.getId()));
			if(user.getAuctions().isEmpty() || !user.getAuctions().contains(p)){
				user.getAuctions().add(p);
				user.setRunningAuctions(user.getRunningAuctions() + 1);
			}
			session.setAttribute("user", user);
			System.out.println(user);
			JsonObject json = new JsonObject();
			json.addProperty("newBudget", user.getBudget());
			json.addProperty("newProductPrice", p.getPrice());
			json.addProperty("runningAuctions", user.getRunningAuctions());
			response.getWriter().write(json.toString());
		}
		else {
			throw new ServletException();
		}

	}

}
