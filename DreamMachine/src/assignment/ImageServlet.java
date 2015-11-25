package assignment;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ImageServlet
 */
@WebServlet(description = "Servlet to retrieve images from Database", urlPatterns = { "/image/*" })
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Photo photo = parseImageFromURL(request.getRequestURL().toString());
		if (photo == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		byte[] imgData = photo.toBytes();
		if (imgData == null) {
			response.sendError(HttpServletResponse.SC_CONFLICT);
			return;
		}
		response.setContentType("image/" + photo.ftype);
        response.setContentLength(imgData.length);
        response.getOutputStream().write(imgData);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * parses the photo trying to be requested from the URL
	 */
	private Photo parseImageFromURL(String url) {
		String match = "/image/";
		int idx = url.indexOf(match);
		String idString = url.substring(idx + match.length());
		int id = Integer.parseInt(idString);
		List<Photo> pagePhoto = Photo.searchById(id);
		if (pagePhoto.isEmpty())
			return null;
		else
			return pagePhoto.get(0);
	}
	
}
