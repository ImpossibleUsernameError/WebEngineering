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

	/**
	 * This method receives the new price and the product id for which a bid was made from the request, checks
	 * if the new price is actually higher than the old one and if the bank account of the user wouldn't get below 0
	 * when making this bid and if the bid fulfills the conditions the product's properties are set and a response
	 * with the new budget of the user, the changed running auctions as well as the new Price are sent back to the user
	 * @param request the http request
	 * @param response the http response
	 * @throws ServletException if the bid is invalid
	 * @throws IOException if some error with the response occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");

		//Getting the new price from the request
		double newPrice = Math.floor(Double.parseDouble(request.getParameter("newP"))*100)/100d;
		//Getting the product with the requested ID
		Product p = ProductPool.getInstance().getProductById(request.getParameter("prodId"));
		double oldPrice = p.getPrice();
		String oldUser = p.getMaxBidUser();
		double oldBud = -1;
		if(!oldUser.equals("")) {
			oldBud = Userpool.getInstance().getUser(oldUser).getBudget();
		}



		//If the new price is greater than the old one and the user has more budget than he would bid
		if(newPrice > p.getPrice() && user != null && (user.getBudget() - newPrice) >= 0){

			//The user is currently the highest bidder and wants to increase his bid
			if(p.getMaxBidUser().equals(user.getEmail())) {
				oldBud = user.getBudget() - newPrice;
				user.setBudget(Math.floor((user.getBudget() - newPrice + oldPrice)*100)/100d);
			}

			//Another user or no one is currently the highest bidder
			else {
				user.setBudget((Math.floor((user.getBudget() - newPrice)*100))/100d);
			}

			//Setting the new product price
			p.setPrice(newPrice);

			//Setting the new highest bidder for the product
			p.setMaxBidUser(user.getEmail());

			/*If the user hasn't been involved in the auction of this product, then add the product to his auctions and
			increase the runningAuctions count */
			if(user.getAuctions().isEmpty() || !user.getAuctions().contains(p)){
				user.getAuctions().add(p);
				user.setRunningAuctions(user.getRunningAuctions() + 1);
			}

			//Creating json object for sending data back to the jsp page


			JsonObject json = new JsonObject();
			json.addProperty("type", "newbid");
			json.addProperty("newBudget", user.getBudget());
			json.addProperty("newProductPrice", p.getPrice());
			json.addProperty("runningAuctions", user.getRunningAuctions());
			json.addProperty("oldmaxbidder", oldUser);
			json.addProperty("budForOld", oldBud+oldPrice);
			json.addProperty("currentUser", user.getEmail());
			json.addProperty("pid", p.getId());
			//Sending data back to jsp
			response.getWriter().write(json.toString());
		}
		else {
			throw new ServletException();
		}

	}

}

