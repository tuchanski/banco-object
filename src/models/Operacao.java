package models;

import models.enums.IdentificadorTipo;

import java.time.LocalDateTime;

public class Operacao {

    private final LocalDateTime data;
    private final double valor;
    private final IdentificadorTipo identificadorTipo;

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
        return "Operacao[data=" + data + ", valor=" + valor + ", tipo=" + identificadorTipo + "]";
    }

}
