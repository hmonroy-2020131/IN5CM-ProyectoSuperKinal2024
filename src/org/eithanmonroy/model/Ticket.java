/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eithanmonroy.model;

/**
 *
 * @author Eithan
 */
public class Ticket {
    
    private int ticketId;
    private String descripcion;
    private String Estatus;
    private int clienteId;
    private String cliente;
    private int facturaId;
    
    public Ticket(){
    }

    public Ticket(int ticketId, String descripcion, String Estatus, String cliente, int facturaId) {
        this.ticketId = ticketId;
        this.descripcion = descripcion;
        this.Estatus = Estatus;
        this.cliente = cliente;
        this.facturaId = facturaId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstatus() {
        return Estatus;
    }

    public void setEstatus(String Estatus) {
        this.Estatus = Estatus;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(int facturaId) {
        this.facturaId = facturaId;
    }

    @Override
    public String toString() {
        return "Ticket{" + "ticketId=" + ticketId + ", descripcion=" + descripcion + ", Estatus=" + Estatus + ", clienteId=" + clienteId + ", cliente=" + cliente + ", facturaId=" + facturaId + '}';
    }

    
}
