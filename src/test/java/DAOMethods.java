import control.actor.Customer;
import model.dao.CustomerDAO;
import model.dao.DAO;

import java.sql.SQLException;
import java.util.List;

public class DAOMethods {
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
         */

        try {

//            leerTodaLaBD();
//            leerTodaLaBDAlternativa();
//            leerUnRegistro(3);
//            leerUnRegistroAlternativa(3);
//            borrarRegistro(3);

            // Requiere un objeto Customer previo con datos
//            crearUnRegistro();
//            crearUnRegistroAlternativa();
//            actualizarRegistro(3);
//            actualizarRegistroAlternativa(3);

            // Requiere mail y password (login)
//            existeRegistro();

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void leerTodaLaBD() throws SQLException {
        DAO customerDAO = new CustomerDAO();
        List<Customer> customerList = (List<Customer>) customerDAO.read();
        for (Customer customer : customerList) {
            System.out.println("ID: " + customer.getId() + "\tNombre: " + customer.getName());
        }
    }

    private static void leerTodaLaBDAlternativa() throws SQLException {
        for (Customer customer : (List<Customer>) new CustomerDAO().read()) {
            System.out.println("ID: " + customer.getId() + "\tNombre: " + customer.getName());
        }
    }

    private static void leerUnRegistro(int id) {
        DAO customerDAO = new CustomerDAO();
        Customer customer = (Customer) customerDAO.read(id); // 3 simboliza a algún ID existente
        System.out.println("Correo: " + customer.getEmail() + "\tContraseña: " + customer.getPassword());
    }

    private static void leerUnRegistroAlternativa(int id) {
        System.out.println("Correo: " + ((Customer) new CustomerDAO().read(id)).getEmail());
    }

    private static void crearUnRegistro() throws SQLException {
        // El campo de ID no es relevante para la creación de instancias de la clase Customer

        Customer customer = new Customer(-1, "Eduardo", "Ortega",
                "eduardo.ortega@gmail.com", "NEWPASS", "+56981726384");

        // El customer cargado en DAO no es el customer de la BD

        DAO customerDAO = new CustomerDAO(customer);
        customerDAO.create();
        System.out.println("Registro creado");
    }

    private static void crearUnRegistroAlternativa() {
        new CustomerDAO(new Customer(-1, "Eduardo", "Ortega",
                "eduardo.ortega@gmail.com", "NEWPASS", "+56981726384")).create();
        System.out.println("Registro creado");
    }

    private static void actualizarRegistro(int id) {
        // Notar que se hace uso de 'new CustomerDAO()' tres veces, eso es por estar implementado el patrón
        // Singleton y ese ser un ejemplo que requiere de muchas instancias de la BD, en la práctica no
        // es siempre así.

        DAO customerDAO = new CustomerDAO();
        Customer customer = (Customer) customerDAO.read(id); // Obtenemos un Customer de la BD

        System.out.println("Apellido: " + customer.getLastName());
        customer.setLastName("Garrido"); // Cambiamos algún dato

        customerDAO = new CustomerDAO(customer);
        Customer customerModificado = (Customer) customerDAO.update();

        customer = (Customer) new CustomerDAO().read(id); // Leemos nuevamente el registro desde la BD
        System.out.println("Apellido: " + customer.getLastName());
    }

    private static void actualizarRegistroAlternativa(int id) {
        Customer customer = (Customer) new CustomerDAO().read(id);
        System.out.println("Apellido: " + customer.getLastName());

        customer.setLastName("Zapata");
        new CustomerDAO(customer).update();
        System.out.println("Apellido: " + ((Customer) new CustomerDAO().read(id)).getLastName());
    }

    private static void borrarRegistro(int id) {
        new CustomerDAO().delete(id);
    }

    private static void existeRegistro() {
        // El método entrega como retorno el registro completo del usuario que se solicita o bien
        // un -1 en caso de no existir, por ello un try catch es la mejor alternativa para verificarlo.

        String email = "cristobalgajardo.v@gmail.com";
        String pass = "12345";
        boolean existeElUsuario = false; // Ejemplo de cómo puede ser utilizado

        try {
            Customer customer = (Customer) new CustomerDAO().exists(email, pass);
            System.out.println("El usuario existe");
            existeElUsuario = true;
        } catch (ClassCastException e) {
            System.out.println("El usuario no existe");
        }

        if (existeElUsuario) System.out.println("\nESTÁS LOGUEADO");
    }
}
