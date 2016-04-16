package at.ac.tuwien.big.we16.ue2;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Chrisi on 2016-04-14.
 */
public class DetailServlet extends HttpServlet {

    //private static final long serialVersionUID = 1L;
    private ProductPool productpool;

    @Override
    public void init() throws ServletException {
        super.init();
        productpool=new ProductPool();
        //userpool = new Userpool();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        System.out.println("product - "+request.getParameter("product"));
        Product uber=new Product();
        for( Product p : productpool.getProducts()){
            if(p.getId().equals(request.getParameter("product"))){
                uber=p;
            }
        }

        //User user =(User)session.getAttribute("user");
       // System.out.println(request.getParameter("email"));
        //System.out.println(request.getAttribute("data-product-id"));
        //User user = userpool.getUser(request.getParameter("email"));
        //boolean newuser = false;
        //if(user != null) { //user == null
            /*user = new User();
            newuser = true;
            user.setEmail(request.getParameter("email"));
            user.setBudget(1500);
            user.setLostAuctions(0);
            user.setRunningAuctions(0);
            user.setWonAuctions(0);
            user.setUsername("so ein scheissdreck");
        } else {*/
         //   user = userpool.getUser(user.getEmail());
        //}
        //user.setUsername("So ein Scheissdreck");

        /*String[] inter = request.getParameterValues("interests");
        if(!newuser) {
            user.clearInterests();
        }
        if(inter != null ) {
            List<String> interests = Arrays.asList(inter);
            if(interests.contains("webEngineering")) {
                user.addInterest(Interest.WEBENINEERING);
            }
            if(interests.contains("modelEngineering")) {
                user.addInterest(Interest.MODELENGINEERING);
            }
            if(interests.contains("semanticWeb")) {
                user.addInterest(Interest.SEMANTICWEB);
            }
            if(interests.contains("objectOrientedModeling")) {
                user.addInterest(Interest.OBJECTORIENTEDMODELING);
            }
            if(interests.contains("businessInformatics")) {
                user.addInterest(Interest.BUSINESSINFORMATICS);
            }
        }*/

        session.setAttribute("product", uber);

        /*if(newuser) {
            userpool.registerUser(user);
        }*/
        System.out.println("kjaskjksxa");
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/details.jsp");
        dispatcher.forward(request, response);
    }

}
