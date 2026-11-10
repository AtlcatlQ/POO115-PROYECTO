/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestorDeEmpleados;

/**
 *
 * @author calde
 */
/** Regla académica: hasta 40 horas y excedente al factor 1.5 por semana. */
public class EmpleadoHora extends Empleado {
    private static final double HORAS_NORMALES = 40;
    private static final double FACTOR_HORA_EXTRA = 1.5;
    private double horasTrabajadas;
    private double tarifaPorHora;
    public EmpleadoHora(String id, String nombre, double horas, double tarifa) {
        super(id, nombre);
        setHorasTrabajadas(horas);
        setTarifaPorHora(tarifa);
    }
    public double getHorasTrabajadas() { return horasTrabajadas; }
    public double getTarifaPorHora() { return tarifaPorHora; }
    public void setHorasTrabajadas(double valor) {
        noNegativo(valor, "Horas");
        if (valor > 168) throw new IllegalArgumentException("Las horas semanales no pueden superar 168.");
        horasTrabajadas = valor;
    }
    public void setTarifaPorHora(double valor) { tarifaPorHora = noNegativo(valor, "Tarifa por hora"); }
    @Override public double calcularSalario() {
        double normales = Math.min(horasTrabajadas, HORAS_NORMALES);
        double extras = Math.max(0, horasTrabajadas - HORAS_NORMALES);
        return centavos(normales * tarifaPorHora + extras * tarifaPorHora * FACTOR_HORA_EXTRA);
    }
    @Override public String getTipo() { return "Por hora"; }
    @Override public void actualizarPago(double valor) { setTarifaPorHora(valor); }
    @Override public String obtenerDetalle() {
        return super.obtenerDetalle() + String.format(java.util.Locale.US, " | Horas semanales: %.2f | Tarifa: $%.2f", horasTrabajadas, tarifaPorHora);
    }
}


