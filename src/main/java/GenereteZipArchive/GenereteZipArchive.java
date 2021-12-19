package GenereteZipArchive;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GenereteZipArchive
 */
public class GenereteZipArchive extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GenereteZipArchive() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Намиране на пълния път до папката files
		ServletContext context = request.getServletContext();
		String path = context.getRealPath("/files");

		// Задаване на типа на файла, който ще се изпраща
		response.setContentType("application/zip");

		// Задаване на името на генерирания файл, чрез
		// задаване на стойност на заглавната променливата
		// Content-Disposition
		response.setHeader("Content-Disposition", "attachment;filename=MyArchive.zip");

		// Получаване на изходящ поток, за изпащане на
		// информация към браузъра
		OutputStream os = response.getOutputStream();

		// Генериране на архива и изпращането му към браузъра
		SendArchiveToStream(path, os);
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

	private void SendArchiveToStream(String path, OutputStream os) throws IOException, FileNotFoundException {
		// Създване на нов архив, който ще бъде изпратен
		// към изходящия поток
		ZipOutputStream zipArchive = new ZipOutputStream(os);

		// отваряне на папката, която ще се архивира
		File dir = new File(path);

		// прочитане на всички папки и директории и записа им
		// в масива files
		File[] filesOrDirs = dir.listFiles();

		// Обхождане на всички файлове
		for (File fileOrDir : filesOrDirs) {
			if (fileOrDir.isDirectory())
				continue;

			// Добавяне на ново име на файл в архива
			ZipEntry e = new ZipEntry(fileOrDir.getName());
			zipArchive.putNextEntry(e);

			// Добавяне на последователност от байтове към файла,
			// добавен с putNextEntry(). Байтовете се добавят
			// чрез отваряне на файла и изпращането на байтовете
			// му към архива zipArchive. Четат се по 1024 байта.
			FileInputStream file = new FileInputStream(fileOrDir.getPath());

			while (true) {
				byte[] data = new byte[1024];
				int length = file.read(data);

				// при край на файла се преустановява
				// изпращането на байтове
				if (length == -1)
					break;

				zipArchive.write(data, 0, length);
			}

			// приключване на добавянето на файл в архива
			zipArchive.closeEntry();
		}

		// Затваряне на обекта от тип архив. С това приключва и
		// изпращането на данни към потока.
		zipArchive.close();
	}

}
