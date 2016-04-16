package at.ac.tuwien.big.we16.ue2;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Chrisi on 2016-04-16.
 */
public class BackToOverviewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/overview.jsp");
        dispatcher.forward(request, response);
    }
}
