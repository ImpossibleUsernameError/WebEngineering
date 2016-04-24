package at.ac.tuwien.big.we16.ue2;

import at.ac.tuwien.big.we16.ue2.service.NotifierService;
import at.ac.tuwien.big.we16.ue2.service.ServiceFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;



@WebListener
public class ContextListener implements ServletContextListener {

    private static NotifierService notifierService;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent){
        notifierService = ServiceFactory.getNotifierService();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent){
        notifierService.stop();
    }
}
