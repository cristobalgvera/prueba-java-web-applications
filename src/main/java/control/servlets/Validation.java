package control.servlets;

import control.entities.Customer;
import control.entities.Employee;
import control.entities.Payment;
import control.entities.Visit;
import model.dao.CustomerDAO;
import model.dao.EmployeeDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@WebServlet(value = "/validation", name = "Validation")
public class Validation extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("user");
        String password = request.getParameter("password");
        String loginClass = request.getParameter("loginClass");
        HttpSession session = request.getSession();
        Object user = null;
        RequestDispatcher requestDispatcher = null;
        try {
            if (loginClass.equals("customer")) {
                user = new CustomerDAO().exists(email, password);
                List<Payment> payments = new CustomerDAO((Customer) user).getPayments();
                List<Visit> visits = new CustomerDAO((Customer) user).getVisits();
                session.setAttribute("visits", visits);
                session.setAttribute("payments", payments);
                payments = new CustomerDAO((Customer) user).getDebts();
                session.setAttribute("debts", payments);
                requestDispatcher = request.getRequestDispatcher("jsp/client-home.jsp");
            } else {
                user = new EmployeeDAO().exists(email, password);
                session.setAttribute("payments", new EmployeeDAO((Employee) user).getPayments());
                session.setAttribute("allVisits", new EmployeeDAO((Employee) user).allVisits());
                session.setAttribute("pendingVisits", new EmployeeDAO((Employee) user).pendingVisits());
                requestDispatcher = request.getRequestDispatcher("jsp/employee-home.jsp");
            }
            user.getClass();
        } catch (Exception e) {
            user = -1;
            requestDispatcher = request.getRequestDispatcher("html/error.html");
        } finally {
            session.setAttribute("user", user);
            requestDispatcher.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
