package at.ac.tuwien.big.we16.ue2.service;

import at.ac.tuwien.big.we16.ue2.Product;
import at.ac.tuwien.big.we16.ue2.ProductPool;
import at.ac.tuwien.big.we16.ue2.User;
import at.ac.tuwien.big.we16.ue2.Userpool;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.*;

public class NotifierService {
    private static Map<Session, HttpSession> clients = new ConcurrentHashMap<>();
    private ProductPool pool;
    private final ScheduledExecutorService executor;

    public NotifierService() {
        // Use the scheduled executor to regularly check for recently expired auctions
        // and send a notification to all relevant users.
        this.executor = Executors.newSingleThreadScheduledExecutor();
        pool = ProductPool.getInstance();
        //Setting up the executor for checking every second if a product is gets expired
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Product product = new Product();
                boolean expiredProductFound = false;

                //Iterate through all products, searching for the ones, that are expired, but haven't been set to expired yet
                for (Product p : pool.getProducts()) {

                    //If the time is over but the product isn't marked as expired
                    if (!p.isExpired() && !p.getExpiredTime().isAfter(LocalDateTime.now())) {

                        //Mark it as expired
                        p.setExpired(true);
                        expiredProductFound = true;
                        product = p;
                        break;
                    }
                }
                if(expiredProductFound){

                    //Iterating through all users
                    for (User u : Userpool.getInstance().getUserPool()) {

                        //Excluding the computer user
                        if (!u.getEmail().equals("comUser")) {

                            //If the user has taken part in the now expired auction
                            if (u.getAuctions().contains(product)) {

                                //Set the auction specific values
                                u.setRunningAuctions(u.getRunningAuctions() - 1);
                                u.getAuctions().remove(product);
                                if (u.getEmail().equals(product.getMaxBidUser())) {
                                    u.setWonAuctions(u.getWonAuctions() + 1);
                                } else {
                                    u.setLostAuctions(u.getLostAuctions() + 1);
                                }
                            }

                            //Creating new message, to send to the user
                            JsonObject msg = new JsonObject();
                            msg.addProperty("type", "expiredAuction");
                            msg.addProperty("productId", product.getId());
                            msg.addProperty("budget", u.getBudget());
                            msg.addProperty("runningA", u.getRunningAuctions());
                            msg.addProperty("wonA", u.getWonAuctions());
                            msg.addProperty("lostA", u.getLostAuctions());

                            //Send message with user specific data
                            sendToClient(getSocketSessionByUser(u), msg.toString());
                            System.out.println();
                            System.out.println("sent: " + msg);
                            System.out.println();
                        }
                    }
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    /**
     * This method is used by the WebSocket endpoint to save a reference to all
     * connected users. A list of connections is needed so that the users can be
     * notified about events like new bids and expired auctions (see
     * assignment). We need the socket session so that we can push data to the
     * client. We need the HTTP session to find out which user is currently
     * logged in in the browser that opened the socket connection.
     */
    public void register(Session socketSession, HttpSession httpSession) {
        clients.put(socketSession, httpSession);
    }

    public void unregister(Session userSession) {
        clients.remove(userSession);
    }

    /**
     * Call this method from your servlet's shutdown listener to cleanly
     * shutdown the thread when the application stops.
     * <p>
     * See http://www.deadcoderising.com/execute-code-on-webapp-startup-and-shutdown-using-servletcontextlistener/
     */
    public void stop() {
        this.executor.shutdown();
    }

    /**
     * Send a message through the websocket to the client
     *
     * @param s   The session of the user
     * @param msg The message to be sent
     */
    public void sendToClient(Session s, String msg) {
        s.getAsyncRemote().sendText(msg);
    }

    /**
     * Send message to all clients
     *
     * @param message The message
     */
    public void sendToAll(String message) {

        for (Session ses : clients.keySet()) {
            sendToClient(ses, message);
        }
    }

    private static Session getSocketSessionByUser(User user){
        for(Session session : clients.keySet()){
            if(((User)clients.get(session).getAttribute("user")).getEmail().equals(user.getEmail())){
                return session;
            }
        }
        return null;
    }
}
