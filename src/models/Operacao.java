package models;

import models.enums.IdentificadorTipo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Operacao {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final LocalDateTime data;
    private final double valor;
    private final IdentificadorTipo identificadorTipo;

    private double saldoAtual;

    public Operacao(LocalDateTime data, double valor, IdentificadorTipo identificadorTipo) {
        this.data = data;
        this.valor = valor;
        this.identificadorTipo = identificadorTipo;
    }

    public Operacao(double valor, IdentificadorTipo identificadorTipo) {
        this.valor = valor;
        this.identificadorTipo = identificadorTipo;
        this.data = LocalDateTime.now();
    }

    public LocalDateTime getData() {
        return data;
    }

    public double getValor() {
        return valor;
    }

    public IdentificadorTipo getIdentificadorTipo() {
        return identificadorTipo;
    }

    @Override
    public String toString() {
        return formatter.format(data) + " - " + identificadorTipo.getTipoNome() + " - " + String.format("%.2f", valor) + "\nSaldo " + String.format("%.2f", saldoAtual);
    }

    public double getSaldoAtual() {
        return saldoAtual;
    }

    public void setSaldoAtual(double saldoAtual) {
        this.saldoAtual = saldoAtual;
    }
}
