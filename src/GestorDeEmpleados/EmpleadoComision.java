/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestorDeEmpleados;

/**
 *
 * @author calde
 */
public class EmpleadoComision extends Empleado {
    private double salarioBase;
    private double ventasTotales;
    private double porcentajeComision;
    public EmpleadoComision(String id, String nombre, double base, double ventas, double porcentaje) {
        super(id, nombre);
        setSalarioBase(base);
        setVentasTotales(ventas);
        setPorcentajeComision(porcentaje);
    }
    public double getSalarioBase() { return salarioBase; }
    public double getVentasTotales() { return ventasTotales; }
    public double getPorcentajeComision() { return porcentajeComision; }
    public void setSalarioBase(double valor) { salarioBase = noNegativo(valor, "Salario base"); }
    public void setVentasTotales(double valor) { ventasTotales = noNegativo(valor, "Ventas"); }
    public void setPorcentajeComision(double valor) {
        noNegativo(valor, "Comisión");
        if (valor > 100) throw new IllegalArgumentException("La comisión debe estar entre 0 y 100%.");
        porcentajeComision = valor;
    }
    @Override public double calcularSalario() { return centavos(salarioBase + ventasTotales * porcentajeComision / 100); }
    @Override public String getTipo() { return "Por comisión"; }
    @Override public void actualizarPago(double valor) { setSalarioBase(valor); }
    @Override public String obtenerDetalle() {
        return super.obtenerDetalle() + String.format(java.util.Locale.US, " | Base: $%.2f | Ventas: $%.2f | Comisión: %.2f%%", salarioBase, ventasTotales, porcentajeComision);
    }
}