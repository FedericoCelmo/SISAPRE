/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Federico
 */
public class Prestamo {
    private int folio;
    private String matricula;
    private int idequipo;
    private String fechaPrestamo;
    private String fechaDevolucion;
    private String horaPrestamo;
    private String fechaUso;
    private String horaUso;
    private String horaFinUso;
    private String status;

    public int getFolio() {
        return folio;
    }

    public void setFolio(int folio) {
        this.folio = folio;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getIdequipo() {
        return idequipo;
    }

    public void setIdequipo(int idequipo) {
        this.idequipo = idequipo;
    }

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(String fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getHoraPrestamo() {
        return horaPrestamo;
    }

    public void setHoraPrestamo(String horaPrestamo) {
        this.horaPrestamo = horaPrestamo;
    }

    public String getFechaUso() {
        return fechaUso;
    }

    public void setFechaUso(String fechaUso) {
        this.fechaUso = fechaUso;
    }

    public String getHoraUso() {
        return horaUso;
    }

    public void setHoraUso(String horaUso) {
        this.horaUso = horaUso;
    }

    public String getHoraFinUso() {
        return horaFinUso;
    }

    public void setHoraFinUso(String horaFinUso) {
        this.horaFinUso = horaFinUso;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }   
}
