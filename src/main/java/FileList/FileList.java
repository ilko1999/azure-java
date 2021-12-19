package FileList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FileList
 */
public class FileList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FileList() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletContext context = request.getServletContext();
		String path = context.getRealPath("/files");
		File dir = new File(path);
		File[] filesOrDirs = dir.listFiles();
		String delimeter = request.getParameterValues("delimeter")[0];
		if (delimeter.length() == 0)
			delimeter = ",";
		ArrayList<String> fileNames = new ArrayList<String>();

		for (File file : filesOrDirs) {
			fileNames.add(file.getName());
		}

		String files = String.join(delimeter, fileNames);
		System.out.println(files);
		response.setContentType("text/plain");
		response.getWriter().print(files);
		response.getWriter().close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
