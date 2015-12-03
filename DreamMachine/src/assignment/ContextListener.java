package assignment;

import java.util.Random;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import jdk.nashorn.internal.ir.RuntimeNode.Request;

import java.util.HashSet;
/**
 * Application Lifecycle Listener implementation class ContextListener
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public ContextListener() {
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
    	DBConnection.closeConnection();
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
    	HashSet<Integer> online = new HashSet<Integer>();
    	arg0.getServletContext().setAttribute("onlineUsers", online);
        arg0.getServletContext().setAttribute("rint", new Random(28462));
        DBConnection.connect();
    }
	
}
