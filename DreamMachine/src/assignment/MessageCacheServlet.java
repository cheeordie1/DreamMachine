package assignment;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
/**
 * Servlet implementation class MessageCacheServlet
 */
@WebServlet("/MessageCacheServlet")
public class MessageCacheServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageCacheServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		HashMap<String, ArrayList<String>> cache = 
			(HashMap<String, ArrayList<String>>) request.getSession().getAttribute("cache");
		
		String message = request.getParameter("message");
		String sender = request.getParameter("sender");
		
		if(cache.get(sender) != null) {
			cache.get(sender).add(message);	
		} else {
			ArrayList<String> list = new ArrayList<String>();
			list.add(message);
			cache.put(sender, list);
		}
		request.getSession().setAttribute("cache", cache);
		
		
	}

}
