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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(value = "/customer-home", name = "CustomerHome")
public class CustomerHome extends HttpServlet {
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
        Customer customer = (Customer) session.getAttribute("user");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/client-home.jsp");
        Visit lastVisit = null;
        Address address = null;
        Summary summary = null;
        Employee employee = null;
        Payment payment = null;
        int activeTab = 0;
        int hiddenId = request.getParameter("hidden-id") != null ? Integer.parseInt(request.getParameter("hidden-id")) : -1;
        switch (action) {
            case "pay":
                new CustomerDAO().pay(hiddenId);
                List<Payment> payments = new CustomerDAO(customer).getPayments();
                session.setAttribute("payments", payments);
                payments = new CustomerDAO(customer).getDebts();
                session.setAttribute("debts", payments);
                activeTab = 3;
                break;
            case "request-visit": // Tab 1
                List<String> activities = new ArrayList<>();
                activities.add("Diagnóstico participativo");
                activities.add("Plantemiento de ideas");
                activities.add("Ejecución");
                activities.add("Seguimiento");
                lastVisit = new CustomerDAO(customer).requestVisit(activities, -100000); // Precio aleatorio entre |-X| y |-X| * 4
                List<Visit> visits = new CustomerDAO((Customer) customer).getVisits();
                session.setAttribute("visits", visits);
                activeTab = 1;
                break;
            case "go-back":
                requestDispatcher = request.getRequestDispatcher("jsp/client-home.jsp");
                break;
            case "logout":
                requestDispatcher = request.getRequestDispatcher("index.jsp");
                session.invalidate();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + action);
        }
        setAttributes(request, response, customer, requestDispatcher, lastVisit, address, summary, payment, activeTab);
    }

    protected static void setAttributes(HttpServletRequest request, HttpServletResponse response, Customer customer, RequestDispatcher requestDispatcher, Visit visit, Address address, Summary summary, Payment payment, int activeTab) throws ServletException, IOException {
        if (visit != null) request.setAttribute("visit", visit);
        if (customer != null) request.setAttribute("customer", customer);
        if (summary != null) request.setAttribute("summary", summary);
        if (address != null) request.setAttribute("address", address);
        if (payment != null) request.setAttribute("payment", payment);
        request.setAttribute("activeTab", activeTab);
        requestDispatcher.forward(request, response);
    }
}


/* Petición MOSTRAR VISITAS
Número de asesoría:
 Nº de colaborador:
 Fecha de realización:
 Nº confirmación de pago:
 Actividades realizadas:
 */
