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

    private ProductPool productpool;

    @Override
    public void init() throws ServletException {
        super.init();
        productpool = new ProductPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);

        Product uber = new Product();
        for( Product p : productpool.getProducts()){
            if(p.getId().equals(request.getParameter("product"))){
                uber = p;
            }
        }



        request.setAttribute("product", uber);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/details.jsp");
        dispatcher.forward(request, response);
    }

}
