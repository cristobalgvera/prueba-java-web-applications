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
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/employee-home.jsp");
        Visit visit = null;
        Address address = null;
        Summary summary = null;
        Employee employee = null;
        Payment payment = null;
        int activeTab = 0;
        int hiddenId = Integer.parseInt(null);
        switch (action) {
            // TODO Set activeTabs when is needed
            case "MOSTRAR VISITAS":
                // TODO Test right path and parameter name
                hiddenId = Integer.parseInt(request.getParameter("hidden-btn"));

                visit = new EmployeeDAO().getVisit(hiddenId);
                employee = (Employee) new EmployeeDAO().read(visit.getEmployeeId());
                payment = new CustomerDAO(customer).getPayments().get(visit.getPaymentId());
                break;
            case "PAGAR":
                hiddenId = Integer.parseInt(request.getParameter("hidden-btn"));
                new CustomerDAO().pay(hiddenId);
                List<Payment> payments = new CustomerDAO(customer).getPayments();
                session.setAttribute("payments", payments);
                break;
            case "SOLICITAR VISITA":
                String activities = request.getParameter("hidden-btn");
                List<String> listedActivities = Arrays.asList(activities.split(";;;;"));
                visit = new CustomerDAO(customer).requestVisit(listedActivities, -90000); // Precio aleatorio entre |-X| y |-X| * 4
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + action);
        }
        setAttributes(request, response, customer, requestDispatcher, visit, address, summary, payment, activeTab);
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
