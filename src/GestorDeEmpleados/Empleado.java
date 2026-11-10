/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestorDeEmpleados;

/**
 *
 * @author calde
 */
/** Identidad y contrato común de todos los empleados. */
public abstract class Empleado {
    private final String id;
    private String nombre;

    protected Empleado(String id, String nombre) {
        this.id = texto(id, "ID").toUpperCase(java.util.Locale.ROOT);
        setNombre(nombre);
    }
    public final String getId() { return id; }
    public final String getNombre() { return nombre; }
    public final void setNombre(String nombre) { this.nombre = texto(nombre, "Nombre"); }
    public abstract double calcularSalario();
    public abstract String getTipo();
    /** Actualiza salario mensual, tarifa por hora o salario base según la subclase. */
    public abstract void actualizarPago(double valor);
    public String obtenerDetalle() { return getId() + " | " + getNombre() + " | " + getTipo(); }
    @Override public String toString() {
        return obtenerDetalle() + String.format(java.util.Locale.US, " | Pago: $%.2f", calcularSalario());
    }
    protected static String texto(String valor, String campo) {
        if (valor == null || valor.isBlank()) throw new IllegalArgumentException(campo + " es obligatorio.");
        return valor.strip();
    }
    protected static double noNegativo(double valor, String campo) {
        if (!Double.isFinite(valor) || valor < 0 || valor > 1_000_000_000)
            throw new IllegalArgumentException(campo + " debe estar entre 0 y 1000000000 y ser finito.");
        return valor;
    }
    protected static double centavos(double valor) { return Math.round(valor * 100.0) / 100.0; }
}

