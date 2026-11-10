/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestorDeEmpleados;

/**
 *
 * @author calde
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Locale;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

/** Servicio en memoria. Los cálculos se delegan a los objetos Empleado. */
public class GestorEmpleados {
    private final List<Empleado> empleados = new ArrayList<>();
    public boolean registrar(Empleado empleado) {
        Objects.requireNonNull(empleado, "Empleado obligatorio");
        if (buscarPorId(empleado.getId()) != null) return false;
        empleados.add(empleado);
        return true;
    }
    public Empleado buscarPorId(String id) {
        if (id == null) return null;
        for (Empleado empleado : empleados)
            if (empleado.getId().equalsIgnoreCase(id.strip())) return empleado;
        return null;
    }
    public List<Empleado> listarTodos() { return List.copyOf(empleados); }
    public List<Empleado> listarPorTipo(Class<? extends Empleado> tipo) {
        Objects.requireNonNull(tipo, "Tipo obligatorio");
        List<Empleado> resultado = new ArrayList<>();
        for (Empleado empleado : empleados) if (tipo.isInstance(empleado)) resultado.add(empleado);
        return List.copyOf(resultado);
    }
    public boolean modificarNombre(String id, String nombre) {
        Empleado empleado = buscarPorId(id);
        if (empleado == null) return false;
        empleado.setNombre(nombre);
        return true;
    }
    public boolean actualizarPago(String id, double valor) {
        Empleado empleado = buscarPorId(id);
        if (empleado == null) return false;
        empleado.actualizarPago(valor); // Polimorfismo, sin if por subclase.
        return true;
    }
    public double calcularPlanilla() {
        double total = 0;
        for (Empleado empleado : empleados) total += empleado.calcularSalario();
        return Empleado.centavos(total);
    }
    public String generarReporte() {
        StringBuilder reporte = new StringBuilder("REPORTE ACADEMICO DE REMUNERACIONES\n");
        reporte.append("Importes de ejemplo: mensual (administrativo/comisión) y semanal (hora).\n");
        if (empleados.isEmpty()) return reporte.append("No hay empleados registrados.\n").toString();
        Empleado mayor = empleados.get(0);
        for (Empleado empleado : empleados) {
            reporte.append(empleado).append('\n');
            if (empleado.calcularSalario() > mayor.calcularSalario()) mayor = empleado;
        }
        double total = calcularPlanilla();
        return reporte.append(String.format(Locale.US,
            "Empleados: %d%nSuma de importes de ejemplo: $%.2f%nPromedio de importes: $%.2f%nMayor importe: %s%n",
            empleados.size(), total, total / empleados.size(), mayor.getNombre())).toString();
    }
    public void exportarCsv(Path archivo) throws IOException {
        StringBuilder csv = new StringBuilder("ID,Nombre,Tipo,Periodo,Remuneracion\n");
        for (Empleado empleado : empleados) {
            String periodo = empleado instanceof EmpleadoHora ? "Semana" : "Mes";
            csv.append(celda(empleado.getId())).append(',').append(celda(empleado.getNombre())).append(',')
                .append(celda(empleado.getTipo())).append(',').append(periodo).append(',')
                .append(String.format(Locale.US, "%.2f", empleado.calcularSalario())).append('\n');
        }
        Files.writeString(archivo, csv, StandardCharsets.UTF_8);
    }
    private static String celda(String valor) {
        // El archivo es un reporte; evita interpretar nombres como fórmulas al abrir en Excel.
        if (!valor.isEmpty() && "=+-@".indexOf(valor.charAt(0)) >= 0) valor = "'" + valor;
        return "\"" + valor.replace("\"", "\"\"") + "\"";
    }
}





