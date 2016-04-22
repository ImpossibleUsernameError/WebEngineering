package at.ac.tuwien.big.we16.ue2.websocket;

import at.ac.tuwien.big.we16.ue2.Product;
import at.ac.tuwien.big.we16.ue2.Userpool;
import at.ac.tuwien.big.we16.ue2.service.NotifierService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * This endpoint listens on the /socket URL.
 */
@ServerEndpoint(value="/socket", configurator = BigBidConfigurator.class, decoders = ProductDec.class )
public class BigBidEndpoint {
    private final NotifierService notifierService;

    public BigBidEndpoint(NotifierService notifierService) {
        this.notifierService = notifierService;
    }

    /**
     * When a new WebSocket connection is established, we register both the
     * socket session and the associated HTTP session with the notifier service.
     */
    @OnOpen
    public void onOpen(Session socketSession, EndpointConfig config) {
        this.notifierService.register(socketSession, (HttpSession) config.getUserProperties().get(HttpSession.class.getName()));
    }

    @OnMessage
    public void onMessage(String p)
    {
        JsonObject json = (JsonObject) (new JsonParser()).parse(p);
        json.addProperty("type", "overbid");
        if(!json.get("oldmaxbidder").getAsString().equals("comUser") && Userpool.getInstance().getUser(json.get("oldmaxbidder").getAsString()) != null) {
            NotifierService.sendToClient(Userpool.getInstance().getUser(json.get("oldmaxbidder").getAsString()), json.toString());
        }
        json.addProperty("type", "newbid");
        NotifierService.sendToAll(json.toString());
    }

    /**
     * When a socket connection is closed, we remove its session from the
     * notifier service.
     */
    @OnClose
    public void onClose(Session socketSession) {
        this.notifierService.unregister(socketSession);
    }
}