package Main;

import Controller.AuthController; 
import Controller.CocheController;
import Controller.GastoController;
import Model.AuthModel; 
import Model.Coche;
import Model.Gasto;
import Model.ModelDatabase;
import Model.Usuario;
import Model.dtos.UserLoginDto;
import Model.dtos.UserRegisterDto;
import View.CocheView;
import View.GastoView;
import View.UsuarioView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    private CocheController cocheController;
    private GastoController gastoController;
    private AuthController authController; 
    private AuthModel authModel; 
    private ModelDatabase modelDatabase; 

    private UsuarioView usuarioView;
    private CocheView cocheView;
    private GastoView gastoView;

    private Usuario usuarioLogueado;

    private Scanner scanner;

    public Main() throws ClassNotFoundException, SQLException, IOException {
    	modelDatabase = new ModelDatabase(); 
        cocheController = new CocheController(modelDatabase); // Pasa modelDatabase al constructor     
        gastoController = new GastoController();
        authModel = new AuthModel();
        authController = new AuthController(authModel); // Pasa authModel al AuthController
        usuarioView = new UsuarioView(authController); // Pasa authController al UsuarioView
        cocheView = new CocheView();
        gastoView = new GastoView();
        usuarioLogueado = null;
        scanner = new Scanner(System.in);
    }


    public void iniciar() throws ClassNotFoundException, IOException {
        System.out.println("Bienvenido a la aplicación de Control de Gastos de Coches");
        while (true) {
            System.out.println("\nMenú Principal:");
            System.out.println("1. Registrarse");
            System.out.println("2. Iniciar sesión");
            System.out.println("3. Salir");
            System.out.print("Seleccione opción: ");

            String opcion = scanner.nextLine().trim();
            switch (opcion) {
                case "1":
                    registrar();
                    break;
                case "2":
                    login();
                    if (usuarioLogueado != null) {
                        menuUsuario();
                    }
                    break;
                case "3":
                    System.out.println("Gracias por usar la aplicación. ¡Hasta pronto!");
                    return;
                default:
                    System.out.println("Opción inválida, intente de nuevo.");
            }
        }
    }

    private void registrar() {
        try {
            String nombre = usuarioView.promptNombreRegistro();
            String contrasenia = usuarioView.promptContraseñaRegistro();
            
            String uuid = UUID.randomUUID().toString();
            
            UserRegisterDto nuevoUsuario = new UserRegisterDto(nombre, contrasenia, uuid); 

            if (authController.register(nuevoUsuario)) {
                usuarioView.showRegistroExitoso(nuevoUsuario.getUuid()); 
            } else {
                usuarioView.showError("Error al registrar el usuario.");
            }
        } catch (Exception e) {
            usuarioView.showError(e.getMessage());
        }
    }


    private void login() throws ClassNotFoundException, IOException {
        String nombre = usuarioView.promptNombreLogin();
        String contrasenia = usuarioView.promptContraseñaLogin(); 
        UserLoginDto userLoginDto = new UserLoginDto(nombre, contrasenia, 0, ""); // valores temporales

        
        if (authController.login(userLoginDto)) { 
            UserLoginDto usuarioDesdeDb = authModel.byName(nombre); 
            if (usuarioDesdeDb != null) {
                usuarioLogueado = new Usuario(usuarioDesdeDb.getName(), usuarioDesdeDb.getPassword(), (int) usuarioDesdeDb.getId(), usuarioDesdeDb.getUuid()); // Asignar el usuario logueado
                usuarioView.showLoginExitoso(usuarioLogueado.getNombre());
                menuUsuario(); 
            } else {
                usuarioView.showLoginFallido();
            }
        } else {
            usuarioView.showLoginFallido();
        }
    }



    private void menuUsuario() throws ClassNotFoundException, IOException {
        while (true) {
            System.out.println("\nMenú Usuario:");
            System.out.println("1. Crear coche");
            System.out.println("2. Listar coches propios");
            System.out.println("3. Añadir propietario a coche");
            System.out.println("4. Gestionar gastos de coche");
            System.out.println("5. Cerrar sesión");
            System.out.print("Seleccione opción: ");
            String opcion = scanner.nextLine().trim();
            switch (opcion) {
                case "1":
                    crearCoche();
                    break;
                case "2":
                    listarCoches();
                    break;
                case "3":
                    añadirPropietario();
                    break;
                case "4":
                    gestionarGastos();
                    break;
                case "5":
                    usuarioLogueado = null;
                    System.out.println("Sesión cerrada.");
                    return;
                default:
                    System.out.println("Opción inválida, intente de nuevo.");
            }
        }
    }

    private void crearCoche() {
        if (usuarioLogueado == null) {
            cocheView.showMensaje("Error: No hay un usuario logueado.");
            return;
        }
        
        String marca = cocheView.promptMarca();
        String modelo = cocheView.promptModelo();
        String matricula = cocheView.promptMatricula();
        int anio = cocheView.promptAnio();
        
        // Validar datos de entrada
        if (marca == null || modelo == null || matricula == null || anio <= 0) {
            cocheView.showMensaje("Error: Datos de coche inválidos.");
            return;
        }
        
        try {
            // Asegurar de que el propietarioOriginal no sea null
            Coche nuevo = cocheController.crearCoche(0, usuarioLogueado, marca, modelo, matricula, anio);
            cocheView.showMensaje("Coche creado: " + nuevo);
        } catch (Exception e) {
            cocheView.showMensaje("Error al crear coche: " + e.getMessage());
        }
    }

    private void listarCoches() {
        List<Coche> coches = cocheController.obtenerCochesPorPropietario(usuarioLogueado);
        cocheView.showCoches(coches);
    }


    private void añadirPropietario() {
        List<Coche> coches = cocheController.obtenerCochesPorPropietario(usuarioLogueado);
        if (coches.isEmpty()) {
            cocheView.showMensaje("No tiene coches para añadir propietarios.");
            return;
        }
        int index = cocheView.promptSeleccionCoche(coches, "Seleccione el coche al que desea añadir propietario:");
        Coche cocheSeleccionado = coches.get(index);

        String uuidNuevoPropietario = usuarioView.promptUUIDParaAgregarPropietario();
        Usuario nuevoPropietario = authController.obtenerUsuarioPorUUID(uuidNuevoPropietario);
        if (nuevoPropietario == null) {
            cocheView.showMensaje("No se encontró ningún usuario con ese UUID.");
            return;
        }
        if (cocheSeleccionado.addPropietario(nuevoPropietario)) {
            cocheView.showMensaje("Usuario añadido como propietario correctamente.");
        } else {
            cocheView.showMensaje("El usuario ya es propietario de este coche.");
        }
    }

    private void gestionarGastos() throws ClassNotFoundException, IOException {
        List<Coche> coches = cocheController.obtenerCochesPorPropietario(usuarioLogueado);
        if (coches.isEmpty()) {
            cocheView.showMensaje("No tiene coches para gestionar gastos.");
            return;
        }
        int index = cocheView.promptSeleccionCoche(coches, "Seleccione el coche para gestionar gastos:");
        Coche cocheSeleccionado = coches.get(index);

        while (true) {
            System.out.println("\nGestión de gastos para coche: " + cocheSeleccionado.getMarca() + " " + cocheSeleccionado.getModelo());
            System.out.println("1. Añadir gasto");
            System.out.println("2. Ver gastos");
            System.out.println("3. Volver");
            System.out.print("Seleccione opción: ");
            String opcion = scanner.nextLine().trim();
            switch (opcion) {
                case "1":
                    añadirGasto(cocheSeleccionado);
                    break;
                case "2":
                    verGastos(cocheSeleccionado);
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Opción inválida, intente nuevamente.");
            }
        }
    }

    private void añadirGasto(Coche coche) {
        try {
            String tipo = gastoView.promptTipoGasto();
            int km = gastoView.promptKilometraje();
            Date fecha = gastoView.promptFecha();
            double importe = gastoView.promptImporte();
            String descripcion = gastoView.promptDescripcion();
            if (descripcion.isEmpty()) {
                descripcion = null;
            }
            Gasto gasto = new Gasto(tipo, km, fecha, importe, descripcion);
            gastoController.añadirGasto(coche, gasto);
            System.out.println("Gasto añadido correctamente.");
        } catch (Exception e) {
            System.out.println("Error al añadir gasto: " + e.getMessage());
        }
    }

    private void verGastos(Coche coche) throws ClassNotFoundException, IOException {
        Map<String, Object> filtros = gastoView.promptFiltros();

        Integer anio = (Integer) filtros.get("anio");
        Date fechaInicio = (Date) filtros.get("fechaInicio");
        Date fechaFin = (Date) filtros.get("fechaFin");
        Integer kmMin = (Integer) filtros.get("kmMin");
        Integer kmMax = (Integer) filtros.get("kmMax");
        
      
        String tipo = gastoView.promptTipoGasto(); 

        List<Gasto> gastosFiltrados = gastoController.filtrarGastos(coche, anio, fechaInicio, fechaFin, kmMin, kmMax, tipo);
        gastoView.showGastos(gastosFiltrados);
    }


    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        Main app = new Main();
        app.iniciar();
    }
}
