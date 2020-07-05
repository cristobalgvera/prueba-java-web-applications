package model.dao;

import control.entities.Customer;
import control.entities.Employee;
import control.entities.Summary;
import control.entities.Visit;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOTesting {
    public static void main(String[] args) {
        /*
        Para utilizar cada método de las distintas funciones sólo debes quitar el comentario de la línea.
        En cada método se observa la forma de trabajo del DAO.
        Aún faltan métodos que deben ser agregados en función de cómo se avance en la vista, sin embargo,
        los próximos métodos tendrán un funcionamiento similar.

        Se utiliza la clase control.actor.Customer como ejemplo para el uso.

        Estos métodos se ejecutan en la consola de comandos del IDE, por tanto no es necesario que sea
        ejecutado Tomcat para su funcionamiento.

        Siempre debe haber un try/catch debido a la naturaleza de la clase principal de las bases de datos
        en JAVA.

        *------------------------
        |
        |   1: CUSTOMER DAO
        |   2: EMPLOYEE DAO
        |
        *------------------------
         */

        prueba("CUSTOMERS");
        try {
            probarBD(1);
        } catch (SQLException throwables) {
            System.out.println("ERROR: CUSTOMERS");
            throwables.printStackTrace();
        }

        prueba("EMPLOYEES");
        try {
            probarBD(2);
        } catch (SQLException throwables) {
            System.out.println("ERROR: EMPLOYEES");
            throwables.printStackTrace();
        }
    }

    private static void probarBD(int dbTest) throws SQLException {
        prueba("LEER TODA LA BASE DE DATOS");
        leerTodaLaBD(dbTest);
//        leerTodaLaBDAlternativa();
        prueba("LEER UN REGISTRO ESPECÍFICO");
        leerUnRegistro(dbTest, 5);
//        leerUnRegistroAlternativa(5);
//        borrarRegistro(1, 5);
//
//        Requiere un objeto previo con datos
        prueba("CREAR UN REGISTRO");
        crearUnRegistro(dbTest);
//        crearUnRegistroAlternativa();
        prueba("ACTUALIZAR UN REGISTRO");
        actualizarRegistro(dbTest, 5);
//        actualizarRegistroAlternativa(5);
//
//        Requiere mail y password (login)
        prueba("COMPROBAR EXISTENCIA DE UN REGISTRO");
        existeRegistro(dbTest);
        switch (dbTest) {
            case 1:
                solicitarVisitaALaEmpresa(4);
                pagarDeuda();
                break;
            case 2:
                verVisitasListadas(5);
                solicitarResumen(2); // y actualizar a la vez
                finalizarVisita();
                actividadesVisita(36);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dbTest);
        }
    }

    private static void prueba(String nombrePrueba) {
        StringBuilder stringBuilder = new StringBuilder();
        String separador = "\n***********************\n";

        stringBuilder.append(separador);
        stringBuilder.append(nombrePrueba);
        stringBuilder.append(separador);

        System.out.println(stringBuilder.toString());
    }

    private static void actividadesVisita(int visitId) throws SQLException {
        prueba("OBTENER Y ACTUALIZAR DE ACTIVIDADES");
        List<String> activities = new EmployeeDAO().getActivities(visitId);
        activities.set(3, "¡CAMBIO DE ACTIVIDAD FUNCIONA!");
        new EmployeeDAO().setActivities(visitId, activities);
    }

    private static void solicitarResumen(int visitId) throws SQLException {
        prueba("SOLICITAR RESUMEN");
        Summary summary = new EmployeeDAO().getSummary(2);
        System.out.println("ID: " + summary.getId() + "\tDescripción: " + summary.getDescription() + "\tRating: " +
                summary.getRating() + "\tFecha: " + summary.getDate());
        actualizarResumen(summary);
    }

    private static void actualizarResumen(Summary summary) {
        prueba("ACTUALIZAR RESUMEN");
        summary.setDescription("ESTA VISITA SALIÓ EXCELENTE");
        summary.setRating(9);
        new EmployeeDAO().setSummary(summary);
    }

    private static void solicitarVisitaALaEmpresa(int id) {
        prueba("SOLICITAR VISITA A LA EMPRESA");
        Customer customer = (Customer) new CustomerDAO().read(id);
        CustomerDAO customerDAO = new CustomerDAO(customer);
        List<String> activities = new ArrayList<>();
        activities.add("ACTIVIDAD 1");
        activities.add("ACTIVIDAD 2");
        activities.add("ACTIVIDAD 3");
        activities.add("ACTIVIDAD 4");
        Visit visit = customerDAO.requestVisit(activities, 150000);
        System.out.println("Customer ID: " + visit.getCustomerId() + "\tEmployee ID: " +
                visit.getEmployeeId() + "\tFecha: " + (visit.getDate()) + "\tSegunda actividad: " +
                visit.getActivity(1) + "\tPayment ID: " + visit.getPaymentId());
    }

    private static void finalizarVisita() {
        prueba("FINALIZAR VISITA");
        new EmployeeDAO().endVisit(30);
    }

    private static void pagarDeuda() {
        prueba("PAGAR DEUDA");
        new CustomerDAO().pay(80);
    }

    private static void verVisitasListadas(int id) {
        prueba("SOLICITAR VISITAS DEL EMPLOYEE");
//        Employee employee = (Employee) new EmployeeDAO().read(id);
//        EmployeeDAO employeeDAO = new EmployeeDAO(employee);
        System.out.println("\nTODAS LAS VISITAS\n");
//        List<Visit> visitList = employeeDAO.allVisits();
        for (Visit visit : new EmployeeDAO((Employee) new EmployeeDAO().read(id)).allVisits()) {
            System.out.println("Customer ID: " + visit.getCustomerId() + "\tFecha: " + visit.getDate());
        }
        System.out.println("\nVISITAS LISTAS\n");
        for (Visit visit : new EmployeeDAO((Employee) new EmployeeDAO().read(id)).readyVisits()) {
            System.out.println("Customer ID: " + visit.getCustomerId() + "\tFecha: " + visit.getDate());
        }
        System.out.println("\nVISITAS PENDIENTES\n");
        for (Visit visit : new EmployeeDAO((Employee) new EmployeeDAO().read(id)).pendingVisits()) {
            System.out.println("Customer ID: " + visit.getCustomerId() + "\tFecha: " + visit.getDate());
        }
    }

    private static void leerTodaLaBD(int dbTest) throws SQLException {
        switch (dbTest) {
            case 1:
                DAO customerDAO = new CustomerDAO();
                List<Customer> customerList = (List<Customer>) customerDAO.readAll();
                for (Customer customer : customerList) {
                    System.out.println("ID: " + customer.getId() + "\tNombre: " + customer.getName());
                }
                break;
            case 2:
                DAO employeeDAO = new EmployeeDAO();
                List<Employee> employeesList = (List<Employee>) employeeDAO.readAll();
                for (Employee employee : employeesList) {
                    System.out.println("ID: " + employee.getId() + "\tNombre: " + employee.getName());
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dbTest);
        }
    }

    private static void leerTodaLaBDAlternativa() throws SQLException {
        for (Customer customer : (List<Customer>) new CustomerDAO().readAll()) {
            System.out.println("ID: " + customer.getId() + "\tNombre: " + customer.getName());
        }
    }

    private static void leerUnRegistro(int dbTest, int id) {
        switch (dbTest) {
            case 1:
                DAO customerDAO = new CustomerDAO();
                Customer customer = (Customer) customerDAO.read(id); // Simboliza a algún ID existente
                System.out.println("Correo: " + customer.getEmail() + "\tContraseña: " + customer.getPassword());
                break;
            case 2:
                DAO employeeDAO = new EmployeeDAO();
                Employee employee = (Employee) employeeDAO.read(id);
                System.out.println("Correo: " + employee.getEmail() + "\tContraseña: " + employee.getPassword());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dbTest);
        }
    }

    private static void leerUnRegistroAlternativa(int id) {
        System.out.println("Correo: " + ((Customer) new CustomerDAO().read(id)).getEmail());
    }

    private static void crearUnRegistro(int dbTest) throws SQLException {
        // El campo de ID no es relevante para la creación de instancias de la clase Customer
        switch (dbTest) {
            case 1:
                Customer customer = new Customer(-1, "Eduardo", "Ortega",
                        "eduardo.ortega@gmail.com", "NEWPASS", "+56981726384");

                // El customer cargado en DAO no es el customer de la BD, no usarlo en etapas posteriores.
                // Si se requiere de su uso buscar en otra instancia en la BD a menos que no se requiera
                // el ID de dicho customer

                DAO customerDAO = new CustomerDAO(customer);
                customer = (Customer) customerDAO.create();
                System.out.println("ID: " + customer.getId());
                break;
            case 2:
                Employee employee = new Employee(-1, "Eduardo", "Ortega",
                        "eduardo.ortega@gmail.com", "NEWPASS", "+56981726384");
                DAO employeeDAO = new EmployeeDAO(employee);
                employee = (Employee) employeeDAO.create();
                System.out.println("ID: " + employee.getId());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dbTest);
        }
        System.out.println("Registro creado");
    }

    private static void crearUnRegistroAlternativa() throws SQLException {
        new CustomerDAO(new Customer(-1, "Eduardo", "Ortega",
                "eduardo.ortega@gmail.com", "NEWPASS", "+56981726384")).create();
        System.out.println("Registro creado");
    }

    private static void actualizarRegistro(int dbTest, int id) {
        switch (dbTest) {
            case 1:
                // Notar que se hace uso de 'new CustomerDAO()' tres veces, eso es por estar implementado el patrón
                // Singleton y ese ser un ejemplo que requiere de muchas instancias de la BD, en la práctica no
                // es siempre así.

                DAO customerDAO = new CustomerDAO();
                Customer customer = (Customer) customerDAO.read(id); // Obtenemos un Customer de la BD

                System.out.println("ID: " + customer.getId() + "\tApellido: " + customer.getLastName());
                customer.setLastName("Esparza"); // Cambiamos algún dato

                customerDAO = new CustomerDAO(customer);
                Customer customerModificado = (Customer) customerDAO.update(); // Podemos almacenar la actualización del registro desde la BD

                customer = (Customer) new CustomerDAO().read(id); // Leemos nuevamente el registro desde la BD
                System.out.println("ID: " + customer.getId() + "\tApellido: " + customer.getLastName());
                break;
            case 2:
                DAO employeeDAO = new EmployeeDAO();
                Employee employee = (Employee) employeeDAO.read(id);

                System.out.println("ID: " + employee.getId() + "\tApellido: " + employee.getLastName());
                employee.setLastName("Contreras");

                employeeDAO = new EmployeeDAO(employee);
                Employee employeeModificado = (Employee) employeeDAO.update();

                employee = (Employee) new EmployeeDAO().read(id);
                System.out.println("ID: " + employee.getId() + "\tApellido: " + employee.getLastName());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dbTest);
        }
    }

    private static void actualizarRegistroAlternativa(int id) {
        Customer customer = (Customer) new CustomerDAO().read(id);
        System.out.println("Apellido: " + customer.getLastName());

        customer.setLastName("Zapata");
        new CustomerDAO(customer).update();
        System.out.println("Apellido: " + ((Customer) new CustomerDAO().read(id)).getLastName());
    }

    private static void borrarRegistro(int dbTest, int id) {
        switch (dbTest) {
            case 1:
                new CustomerDAO().delete(id);
                break;
            case 2:
                new EmployeeDAO().delete(id);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dbTest);
        }
    }

    private static void existeRegistro(int dbTest) {
        String email, pass;
        boolean existeElUsuario = false;
        switch (dbTest) {
            case 1:
                // El método entrega como retorno el registro completo del usuario que se solicita o bien
                // un -1 en caso de no existir, por ello un try catch es la mejor alternativa para verificarlo.

                email = "cristobalgajardo.v@gmail.com";
                pass = "12345";

                // Ejemplo de cómo puede ser utilizado

                try {
                    Customer customer = (Customer) new CustomerDAO().exists(email, pass);
                    System.out.println("Correo: " + email + "\tContraseña: " + pass);
                    System.out.println("El usuario existe");
                    existeElUsuario = true;
                } catch (ClassCastException | SQLException e) {
                    System.out.println("El usuario no existe");
                }

                if (existeElUsuario) System.out.println("\nESTÁS LOGUEADO");
                break;
            case 2:
                email = "skasoler@gmail.com";
                pass = "54321";

                try {
                    Employee employee = (Employee) new EmployeeDAO().exists(email, pass);
                    System.out.println("Correo: " + email + "\tContraseña: " + pass);
                    System.out.println("El usuario existe");
                    existeElUsuario = true;
                } catch (ClassCastException | SQLException e) {
                    System.out.println("El usuario no existe");
                }

                if (existeElUsuario) System.out.println("\nESTÁS LOGUEADO");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dbTest);
        }
    }
}
