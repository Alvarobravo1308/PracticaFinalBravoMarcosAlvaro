package View;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import Controller.AuthController;

public class UsuarioView {
    private Scanner scanner;
    private AuthController authController;

    public UsuarioView() throws ClassNotFoundException, SQLException, IOException {
    	super();
    	this.authController = new AuthController();
        scanner = new Scanner(System.in);
    }

    public String promptNombreRegistro() {
        System.out.print("Introduzca su nombre para registrarse: ");
        return scanner.nextLine().trim();
    }
    public String promptContraseñaRegistro() {
        System.out.print("Introduzca su contraseña para registrarse: ");
        return scanner.nextLine().trim();
    }

    public String promptNombreLogin() {
        System.out.print("Introduzca su nombre para iniciar sesión: ");
        return scanner.nextLine().trim();
    }

    public String promptContraseñaLogin() {
        System.out.print("Introduzca su contraseña para iniciar sesión: ");
        return scanner.nextLine().trim();
    }
    public void showRegistroExitoso(String uuid) {
        System.out.println("Registro exitoso. Su código UUID es: " + uuid);
    }

    public void showError(String mensaje) {
        System.out.println("Error: " + mensaje);
    }
    public UsuarioView(AuthController authController) throws ClassNotFoundException, SQLException, IOException {
        super();
        this.authController = authController; 
        scanner = new Scanner(System.in);
    }

    public void showLoginExitoso(String nombre) {
        System.out.println("Bienvenido, " + nombre + "!");
    }

    public void showLoginFallido() {
        System.out.println("Login fallido. Usuario no encontrado.");
    }

    public String promptUUIDParaAgregarPropietario() {
        System.out.print("Ingrese el código UUID del usuario que desea añadir como propietario: ");
        return scanner.nextLine().trim();
    }

	public AuthController getAuthController() {
		return authController;
	}

	public void setAuthController(AuthController authController) {
		this.authController = authController;
	}

	
}
