package models;

import models.enums.IdentificadorTipo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Operacao implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final LocalDateTime data;
    private final double valor;
    private final IdentificadorTipo identificadorTipo;
    private String msg;

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

    public Operacao(double valor, IdentificadorTipo identificadorTipo, String msg) {
        this.valor = valor;
        this.identificadorTipo = identificadorTipo;
        this.data = LocalDateTime.now();
        this.msg = msg;
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

    public double getSaldoAtual() {
        return saldoAtual;
    }

    public void setSaldoAtual(double saldoAtual) {
        this.saldoAtual = saldoAtual;
    }

    @Override
    public String toString() {
        return formatter.format(data) + " - " +
                identificadorTipo.getTipoNome() + " - " +
                String.format("%.2f", valor) + "\n" +
                (msg != null ? "Mensagem: " + msg + "\n" : "") +
                "Saldo: " + String.format("%.2f", saldoAtual);
    }

    @Serial
    private void readObject(java.io.ObjectInputStream ois) throws java.io.IOException, ClassNotFoundException {
        ois.defaultReadObject();
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }
}
