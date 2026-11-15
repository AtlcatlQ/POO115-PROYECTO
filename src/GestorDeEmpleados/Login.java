package GestorDeEmpleados;

import java.util.Scanner;

/** Control sencillo de acceso para el prototipo de consola. */
public class Login {

    private static final String USUARIO = "admin";
    private static final String CONTRASENA = "Admin123";

    private Login() {
        // Evita crear objetos de esta clase de utilidad.
    }

    public static boolean autenticar(Scanner entrada) {
        int intentos = 3;

        System.out.println("==================================");
        System.out.println("       NEXO - INICIO DE SESION");
        System.out.println("==================================");

        while (intentos > 0) {
            System.out.print("Usuario: ");
            String usuario = entrada.nextLine().strip();

            System.out.print("Contrasena: ");
            String contrasena = entrada.nextLine();

            if (usuario.equals(USUARIO) && contrasena.equals(CONTRASENA)) {
                System.out.println("\nAcceso autorizado.");
                System.out.println("Bienvenido al sistema.\n");
                return true;
            }

            intentos--;
            System.out.println("\nUsuario o contrasena incorrectos.");
            System.out.println("Intentos restantes: " + intentos);
        }

        System.out.println("\nAcceso bloqueado.");
        System.out.println("Cerrando el sistema...");
        return false;
    }
}
