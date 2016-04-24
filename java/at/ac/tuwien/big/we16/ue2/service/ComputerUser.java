package at.ac.tuwien.big.we16.ue2.service;

import at.ac.tuwien.big.we16.ue2.Product;
import at.ac.tuwien.big.we16.ue2.ProductPool;
import at.ac.tuwien.big.we16.ue2.User;
import at.ac.tuwien.big.we16.ue2.Userpool;
import com.google.gson.JsonObject;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class represents a computer user which will bid the highest bid for each product every 10 seconds with a
 * probability of 30%
 *
 * The annotation @WebListener achieves the automatic calls of the specified functions
 */
@WebListener
public class ComputerUser implements ServletContextListener {

	private final ScheduledExecutorService executor;
	private User user;

	public ComputerUser(){
		executor = Executors.newSingleThreadScheduledExecutor();
		user = new User();
		user.setEmail("comUser");
		user.setBudget(Double.MAX_VALUE);
		Userpool.getInstance().addUser(user);
	}

	/**
	 * Is called automatically when the web app is deployed
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce){
		//Executor function for executing an action regularly
		executor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				for(Product p : ProductPool.getInstance().getProducts()){
					double random = Math.random();
					//The random number is with the probability of 30% smaller than 0.3
					if(random <= 0.3 && !p.isExpired()){
						System.out.println("bid on " + p.getName() + ": " + p.getPrice());

						//Register the current max bidder, because we have to notify him, that he was overbidden
						User userToNotify = Userpool.getInstance().getUser(p.getMaxBidUser());

						//Create new json message to send
						JsonObject json = new JsonObject();
						json.addProperty("type", "overbid");
						json.addProperty("oldmaxbidder", p.getMaxBidUser());

						//if we have an actual user to notify and not the comUser or nobody
						if(userToNotify != null && !userToNotify.getEmail().equals(user.getEmail())) {
							json.addProperty("budForOld", userToNotify.getBudget() + p.getPrice());

							//The user was overbidden, so his bid must be added to his budget again
							userToNotify.setBudget(Math.floor((userToNotify.getBudget() + p.getPrice())*100)/100d);
						}
						json.addProperty("pid", p.getId());

						p.setMaxBidUser(user.getEmail());
						/*If a real user bids, and the computer user overbids the real user, there are sometimes
						inaccuracies */
						p.setPrice(Math.floor((p.getPrice() + 0.01)*100)/100d);

						//We send the message only, if there is an actual user to inform
						//Otherwise the function would act really strange...maybe
						if(userToNotify != null && !userToNotify.getEmail().equals(user.getEmail())) {
							NotifierService.sendToClient(userToNotify, json.toString());
						}

						//Prepare message for sending it to all users
						json.addProperty("type", "newbid");
						json.addProperty("newProductPrice", p.getPrice());
						json.addProperty("currentUser", user.getEmail());

						//Sending message with new bid to all users
						NotifierService.sendToAll(json.toString());
					}
				}
			}
			//The first number is the starting delay, the second number the time interval
		}, 10, 10, TimeUnit.SECONDS);
	}

	/**
	 * Is automatically called when the application shuts down
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce){
		executor.shutdown();
	}
}
