package models;

import models.enums.IdentificadorTipo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe responsável por guardar os dados de uma operação bancária,
 * incluindo informações como data, valor, tipo da operação, mensagem
 * associada e o saldo atual após a operação.
 */

public class Operacao implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final LocalDateTime data;
    private final double valor;
    private final IdentificadorTipo identificadorTipo;
    private String msg;

    private double saldoAtual;

    /**
     * Construtor da operação, inicializando com data, valor e tipo.
     *
     * @param data Data da operação.
     * @param valor Valor da operação.
     * @param identificadorTipo Tipo da operação (ex: depósito, saque).
     */
    public Operacao(LocalDateTime data, double valor, IdentificadorTipo identificadorTipo) {
        this.data = data;
        this.valor = valor;
        this.identificadorTipo = identificadorTipo;
    }

    /**
     * Construtor da operação, inicializando com valor e tipo. A data é preenchida com o momento atual.
     *
     * @param valor Valor da operação.
     * @param identificadorTipo Tipo da operação (ex: depósito, saque).
     */
    public Operacao(double valor, IdentificadorTipo identificadorTipo) {
        this.valor = valor;
        this.identificadorTipo = identificadorTipo;
        this.data = LocalDateTime.now();
    }

    /**
     * Construtor da operação, inicializando com valor, tipo e uma mensagem associada.
     * A data é preenchida com o momento atual.
     *
     * @param valor Valor da operação.
     * @param identificadorTipo Tipo da operação (ex: depósito, saque).
     * @param msg Mensagem associada à operação.
     */
    public Operacao(double valor, IdentificadorTipo identificadorTipo, String msg) {
        this.valor = valor;
        this.identificadorTipo = identificadorTipo;
        this.data = LocalDateTime.now();
        this.msg = msg;
    }

    /**
     * Retorna a data da operação.
     *
     * @return Data da operação.
     */
    public LocalDateTime getData() {
        return data;
    }

    /**
     * Retorna o valor da operação.
     *
     * @return Valor da operação.
     */
    public double getValor() {
        return valor;
    }

    /**
     * Retorna o tipo da operação.
     *
     * @return Tipo da operação.
     */
    public IdentificadorTipo getIdentificadorTipo() {
        return identificadorTipo;
    }

    /**
     * Retorna o saldo atual após a operação.
     *
     * @return Saldo atual.
     */
    public double getSaldoAtual() {
        return saldoAtual;
    }

    /**
     * Define o saldo atual após a operação.
     *
     * @param saldoAtual Saldo atual.
     */
    public void setSaldoAtual(double saldoAtual) {
        this.saldoAtual = saldoAtual;
    }

    /**
     * Retorna uma representação textual da operação, incluindo data, tipo, valor,
     * mensagem (se aplicável), e saldo atual.
     *
     * @return Representação textual da operação.
     */
    @Override
    public String toString() {
        return formatter.format(data) + " - " +
                identificadorTipo.getTipoNome() + " - " +
                String.format("%.2f", valor) + "\n" +
                (msg != null ? "Mensagem: " + msg + "\n" : "") +
                "Saldo: " + String.format("%.2f", saldoAtual);
    }

    /**
     * Método especial para restaurar o estado do objeto após a desserialização,
     * reconfigurando o formatter.
     *
     * @param ois Objeto de entrada de fluxo.
     * @throws java.io.IOException Em caso de erro de entrada/saída.
     * @throws ClassNotFoundException Caso a classe não seja encontrada durante a leitura.
     */
    @Serial
    private void readObject(java.io.ObjectInputStream ois) throws java.io.IOException, ClassNotFoundException {
        ois.defaultReadObject();
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }
}
