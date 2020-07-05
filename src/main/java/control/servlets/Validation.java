package control.servlets;

import control.entities.Customer;
import control.entities.Employee;
import model.dao.CustomerDAO;
import model.dao.EmployeeDAO;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Validation")
public class Validation extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String loginClass = request.getParameter("loginClass");
        Object validation = null;
        try {
            if (loginClass.equals("Cliente")) {
                validation = (Customer) new CustomerDAO().exists(email, password);
            } else {
                validation = (Employee) new EmployeeDAO().exists(email, password);
            }
        } catch (SQLException e) {
            validation = (int) -1;
        } finally {
            request.setAttribute("customer", validation);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
