package control.servlets;

import control.entities.Customer;
import control.entities.Employee;
import model.dao.CustomerDAO;
import model.dao.EmployeeDAO;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/validation")
public class Validation extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("user");
        String password = request.getParameter("password");
        String loginClass = request.getParameter("loginClass");
        System.out.println(email + " - " + password + " - " + loginClass);
        Object user = null;
        RequestDispatcher requestDispatcher = null;
        try {
            if (loginClass.equals("customer")) {
                user = (Customer) new CustomerDAO().exists(email, password);
                requestDispatcher = request.getRequestDispatcher("jsp/client-home.jsp");
            } else {
//                user = (Employee) new EmployeeDAO().exists(email, password);
//                requestDispatcher = request.getRequestDispatcher("");

                user = (Customer) new CustomerDAO().exists(email, password);
                requestDispatcher = request.getRequestDispatcher("jsp/client-home.jsp");
            }
            System.out.println((user.getClass()));
        } catch (Exception e) {
            user = -1;
            requestDispatcher = request.getRequestDispatcher("error.html");
        } finally {
            request.setAttribute("user", user);
            requestDispatcher.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
