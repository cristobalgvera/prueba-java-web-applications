package control.brain;

import model.dao.DAO;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; 

@WebServlet("/Validate.do")
public class Validate extends HttpServlet {
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		
		String mail;
		String pass;
//		DAO dao = new DAO();
//
//		mail = request.getParameter(namemail);
//		pass = request.getParameter(namepass);
//
//		user u = new user(mail, pass);
//		user newUser = dao.exists(u);
//
//		HttpSession session = request.getSession();
//		if (newUser.errorExist()) {
//			request.getRequestDispatcher("Error").forward(request, response);
//		} else {
//			session.setAttribute("user", newUser);
//			request.getRequestDispatcher("Menu").forward(request, response);
//		}
		
	}
	private static final long serialVersionUID = 1L;

	public Validate() {
		super();

	}
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
