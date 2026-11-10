/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestorDeEmpleados;

/**
 *
 * @author calde
 */
public class EmpleadoAdmin extends Empleado {
    private double salarioMensual;
    public EmpleadoAdmin(String id, String nombre, double salarioMensual) {
        super(id, nombre);
        setSalarioMensual(salarioMensual);
    }
    public double getSalarioMensual() { return salarioMensual; }
    public void setSalarioMensual(double valor) { salarioMensual = noNegativo(valor, "Salario mensual"); }
    @Override public double calcularSalario() { return centavos(salarioMensual); }
    @Override public String getTipo() { return "Administrativo"; }
    @Override public void actualizarPago(double valor) { setSalarioMensual(valor); }
    @Override public String obtenerDetalle() {
        return super.obtenerDetalle() + String.format(java.util.Locale.US, " | Mensual: $%.2f", salarioMensual);
    }
}

