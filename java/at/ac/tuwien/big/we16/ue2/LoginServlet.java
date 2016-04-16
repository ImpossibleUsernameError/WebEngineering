package at.ac.tuwien.big.we16.ue2;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Chrisi on 2016-04-14.
 */
public class LoginServlet extends HttpServlet {

    private Userpool userpool;

    @Override
    public void init() throws ServletException {
        super.init();
        userpool = Userpool.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        System.out.println(request.getParameter("email"));
        User user = userpool.getUser(request.getParameter("email"));

        if(user != null) {
            user = userpool.getUser(user.getEmail());
        }

        session.setAttribute("user", user);


        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/overview.jsp");
        dispatcher.forward(request, response);
    }
}
