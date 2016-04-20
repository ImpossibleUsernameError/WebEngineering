package at.ac.tuwien.big.we16.ue2.service;

import at.ac.tuwien.big.we16.ue2.Product;
import at.ac.tuwien.big.we16.ue2.ProductPool;
import at.ac.tuwien.big.we16.ue2.User;
import at.ac.tuwien.big.we16.ue2.Userpool;
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
	private NotifierService notifierService;

	public ComputerUser(){
		executor = Executors.newSingleThreadScheduledExecutor();
		notifierService = ServiceFactory.getNotifierService();
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
						p.setMaxBidUser(user.getEmail());
						/*If a real user bids, and the computer user overbids the real user, there are sometimes
						inaccuracies */
						p.setPrice(Math.floor((p.getPrice() + 0.01)*100)/100d);
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
		notifierService.stop();
		executor.shutdown();
	}
}
