/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestorDeEmpleados;

/**
 *
 * @author calde
 */
import java.util.Scanner;
import java.util.List;
import java.nio.file.Path;
import java.io.IOException;

/**
 * Interfaz de consola; separada de las reglas de remuneración.
 */
public class ConsolaEmpleados {

    private final GestorEmpleados gestor = new GestorEmpleados();
    private final Scanner entrada = new Scanner(System.in, "UTF-8");

    public void ejecutar() {
        System.out.println("NEXO | Administracion academica de empleados");
        System.out.println("Datos en memoria: al salir se pierden. CSV es un reporte, no un respaldo.");
        while (true) {
            System.out.println("\n1 Registrar |  2 Consultar todos |  3 Buscar ID |  4 Consultar tipo");
            System.out.println("5 Calcular remuneracion | 6 Modificar | 7 Reporte  | 8 Exportar CSV");
            System.out.println("9 Cargar ejemplos  0 Salir");
            String opcion = leer("Opcion: ");
            if (opcion == null || opcion.equals("0")) {
                break;
            }
            try {
                switch (opcion) {
                    case "1":
                        registrar();
                        break;
                    case "2":
                        mostrar(gestor.listarTodos());
                        break;
                    case "3":
                        System.out.println(buscar());
                        break;
                    case "4":
                        mostrar(gestor.listarPorTipo(tipo()));
                        break;
                    case "5":
                        System.out.printf(java.util.Locale.US, "Remuneracion: $%.2f%n", buscar().calcularSalario());
                        break;
                    case "6":
                        modificar();
                        break;
                    case "7":
                        System.out.print(gestor.generarReporte());
                        break;
                    case "8":
                        Path archivo = Path.of(requerido("Ruta CSV (ejemplo reporte.csv): ")).toAbsolutePath();
                        gestor.exportarCsv(archivo);
                        System.out.println("Reporte guardado en " + archivo);
                        break;
                    case "9":
                        Main.cargarEjemplos(gestor);
                        System.out.println("Ejemplos cargados; se conservaron los ID existentes.");
                        break;
                    default:
                        System.out.println("Elegi una opcion de 0 a 9.");
                }
            } catch (IllegalArgumentException | IOException ex) {
                System.out.println("No se completo la operación: " + ex.getMessage());
            } catch (IllegalStateException ex) {
                break;
            }
        }
        System.out.println("Sesion finalizada.");
    }

    private void registrar() {
        Class<? extends Empleado> tipo = tipo();
        String id = requerido("ID: ");
        if (gestor.buscarPorId(id) != null) {
            throw new IllegalArgumentException("El ID ya existe.");
        }
        String nombre = requerido("Nombre: ");
        Empleado empleado;
        if (tipo == EmpleadoAdmin.class) {
            empleado = new EmpleadoAdmin(id, nombre, numero("Salario mensual: "));
        } else if (tipo == EmpleadoHora.class) {
            empleado = new EmpleadoHora(id, nombre, numero("Horas semanales: "), numero("Tarifa por hora: "));
        } else {
            empleado = new EmpleadoComision(id, nombre, numero("Base mensual: "), numero("Ventas mensuales: "), numero("Comisión (%): "));
        }
        if (!gestor.registrar(empleado)) {
            throw new IllegalArgumentException("El ID ya existe.");
        }
        System.out.println("Empleado registrado: " + empleado);
    }

    private Class<? extends Empleado> tipo() {
        String valor = requerido("Tipo (1 Administrativo, 2 Hora, 3 Comision): ");
        switch (valor) {
            case "1":
                return EmpleadoAdmin.class;
            case "2":
                return EmpleadoHora.class;
            case "3":
                return EmpleadoComision.class;
            default:
                throw new IllegalArgumentException("Tipo invalido.");
        }
    }

    private Empleado buscar() {
        Empleado empleado = gestor.buscarPorId(requerido("ID: "));
        if (empleado == null) {
            throw new IllegalArgumentException("No existe ese empleado.");
        }
        return empleado;
    }

    private void modificar() {
        Empleado empleado = buscar();
        String opcion = requerido("1 Nombre, 2 Pago principal, 3 Horas, 4 Ventas, 5 Comision (%): ");
        switch (opcion) {
            case "1":
                gestor.modificarNombre(empleado.getId(), requerido("Nuevo nombre: "));
                break;
            case "2":
                gestor.actualizarPago(empleado.getId(), numero("Nuevo pago principal (mensual / tarifa / base): "));
                break;
            case "3":
                if (!(empleado instanceof EmpleadoHora)) {
                    throw new IllegalArgumentException("Solo aplica a empleados por hora.");
                }
                ((EmpleadoHora) empleado).setHorasTrabajadas(numero("Nuevas horas semanales: "));
                break;
            case "4":
                if (!(empleado instanceof EmpleadoComision)) {
                    throw new IllegalArgumentException("Solo aplica a empleados por comisión.");
                }
                ((EmpleadoComision) empleado).setVentasTotales(numero("Nuevas ventas mensuales: "));
                break;
            case "5":
                if (!(empleado instanceof EmpleadoComision)) {
                    throw new IllegalArgumentException("Solo aplica a empleados por comision.");
                }
                ((EmpleadoComision) empleado).setPorcentajeComision(numero("Nueva comision (%): "));
                break;
            default:
                throw new IllegalArgumentException("Opcion de modificacion inválida.");
        }
        System.out.println("Actualizado: " + empleado);
    }

    private void mostrar(List<Empleado> empleados) {
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados para esta consulta.");
        }
        for (Empleado empleado : empleados) {
            System.out.println(empleado);
        }
    }

    private String leer(String mensaje) {
        System.out.print(mensaje);
        return entrada.hasNextLine() ? entrada.nextLine().strip() : null;
    }

    private String requerido(String mensaje) {
        String valor = leer(mensaje);
        if (valor == null) {
            throw new IllegalStateException("Fin de entrada.");
        }
        if (valor.isBlank()) {
            throw new IllegalArgumentException("El campo no puede quedar vacio.");
        }
        return valor;
    }

    private double numero(String mensaje) {
        String texto = requerido(mensaje);
        if (!texto.matches("[+-]?[0-9]+([.,][0-9]+)?")) {
            throw new IllegalArgumentException("Ingresá un número sin separadores de miles (ejemplo 6.50 o 6,50).");
        }
        double valor = Double.parseDouble(texto.replace(',', '.'));
        if (!Double.isFinite(valor) || valor < 0 || valor > 1_000_000_000) {
            throw new IllegalArgumentException("El número debe estar entre 0 y 1000000000.");
        }
        return valor;
    }
}
