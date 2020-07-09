package control.servlets;

import control.entities.*;
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

@WebServlet(value = "/employee-home", name = "EmployeeHome")
public class EmployeeHome extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("submit-btn");
        Employee employee = (Employee) session.getAttribute("user");
        RequestDispatcher requestDispatcher = null;
        Visit visit = null;
        Address address = null;
        Summary summary = null;
        Customer customer = null;
        Payment payment = null;
        int activeTab = 0;
        int hiddenId = request.getParameter("idvisithidden") != null ? Integer.parseInt(request.getParameter("idvisithidden")) : -1;
        switch (action) {
            case "finish":
                requestDispatcher = request.getRequestDispatcher("jsp/employee-home-finish.jsp");
                visit = new EmployeeDAO().getVisit(hiddenId);
                summary = new EmployeeDAO().getSummary(visit.getId());
                break;
            case "details":
                requestDispatcher = request.getRequestDispatcher("jsp/employee-home-details.jsp");
                visit = new EmployeeDAO().getVisit(hiddenId);
                address = new EmployeeDAO().getAddress(visit.getId());
                summary = new EmployeeDAO().getSummary(visit.getId());
                customer = new EmployeeDAO().getVisitsCustomer(visit.getId());
                payment = new EmployeeDAO().getPayment(visit.getId());
                break;
            case "go-back":
                requestDispatcher = request.getRequestDispatcher("jsp/employee-home.jsp");
                break;
            case "save":
                requestDispatcher = request.getRequestDispatcher("jsp/employee-home.jsp");
                visit = new EmployeeDAO().getVisit(hiddenId);
                summary = new EmployeeDAO().getSummary(visit.getId());
                String description = request.getParameter("description");
                int rating = Integer.parseInt(request.getParameter("rating"));
                Summary summaryUpdate = new Summary(summary.getId(), rating, description, "");
                new EmployeeDAO().setSummary(summaryUpdate);
                break;
            case "terminate":
                requestDispatcher = request.getRequestDispatcher("jsp/employee-home");
                visit = new EmployeeDAO().getVisit(hiddenId);
                new EmployeeDAO().endVisit(visit.getId());
                break;
            case "logout":
                requestDispatcher = request.getRequestDispatcher("index.jsp");
                session.invalidate();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + action);
        }
        System.out.println(hiddenId);
        CustomerHome.setAttributes(request, response, customer, requestDispatcher, visit, address, summary, payment, activeTab);
    }
}
