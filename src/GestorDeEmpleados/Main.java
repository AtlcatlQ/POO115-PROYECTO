/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestorDeEmpleados;

/**
 *
 * @author calde
 */
public class Main {
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("--demo")) demostrar();
        else new ConsolaEmpleados().ejecutar();
    }
    public static void cargarEjemplos(GestorEmpleados gestor) {
        gestor.registrar(new EmpleadoAdmin("A-001", "Ana Martinez", 850));
        gestor.registrar(new EmpleadoHora("H-001", "Carlos Perez", 45, 6.50));
        gestor.registrar(new EmpleadoComision("C-001", "Lucia Gomez", 300, 5000, 8));
    }
    private static void demostrar() {
        GestorEmpleados gestor = new GestorEmpleados();
        cargarEjemplos(gestor);
        System.out.println("Duplicado aceptado: " + gestor.registrar(new EmpleadoHora("h-001", "Otro", 10, 5)));
        System.out.print(gestor.generarReporte());
        Empleado empleado = gestor.buscarPorId("H-001"); // Referencia a la superclase.
        if (empleado instanceof EmpleadoHora) {
            EmpleadoHora porHora = (EmpleadoHora) empleado; // Conversión comprobada.
            porHora.setHorasTrabajadas(50);
        }
        gestor.actualizarPago("A-001", 900);
        gestor.modificarNombre("C-001", "Lucia Gomez de Rivas");
        System.out.println("\nDESPUES DE MODIFICAR");
        System.out.print(gestor.generarReporte());
    }
}


