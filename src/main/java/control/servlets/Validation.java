package control.servlets;

import model.dao.CustomerDAO;
import model.dao.EmployeeDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/validation")
public class Validation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("user");
        String password = request.getParameter("password");
        String loginClass = request.getParameter("loginClass");
//        System.out.println(email + " - " + password + " - " + loginClass);
        Object user = null;
        RequestDispatcher requestDispatcher = null;
        try {
            if (loginClass.equals("customer")) {
                user = new CustomerDAO().exists(email, password);
                requestDispatcher = request.getRequestDispatcher("jsp/client-home.jsp");
            } else {
                user = new EmployeeDAO().exists(email, password);
                requestDispatcher = request.getRequestDispatcher("jsp/employee-home.jsp");
            }
            user.getClass();
        } catch (Exception e) {
            user = -1;
            requestDispatcher = request.getRequestDispatcher("html/error.html");
        } finally {
            request.getSession().setAttribute("user", user);
            requestDispatcher.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
