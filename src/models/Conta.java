package models;

import models.exceptions.SaldoInsuficienteException;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Conta implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static int numeroContaGerador = 0;

    private int numeroConta;
    private String correntistaNome;
    private String correntistaCPF;
    protected double saldo;
    protected List<Operacao> transacoes;

    public Conta(String correntistaNome, String correntistaCPF) {
        numeroContaGerador++;
        this.numeroConta = numeroContaGerador;
        this.correntistaNome = correntistaNome;
        this.correntistaCPF = correntistaCPF;
        this.saldo = 0;
        this.transacoes = new ArrayList<Operacao>();
    }

    public Conta(String correntistaNome, String correntistaCPF, double saldo) {
        numeroContaGerador++;
        this.numeroConta = numeroContaGerador;
        this.correntistaNome = correntistaNome;
        this.correntistaCPF = correntistaCPF;
        this.saldo = saldo;
        this.transacoes = new ArrayList<Operacao>();
    }

    public int getNumeroConta() {
        return numeroConta;
    }

    public String getCorrentistaNome() {
        return correntistaNome;
    }

    public void setCorrentistaNome(String correntistaNome) {
        this.correntistaNome = correntistaNome;
    }

    public String getCorrentistaCPF() {
        return correntistaCPF;
    }

    public double getSaldo() {
        return saldo;
    }

    public List<Operacao> getTransacoes() {
        return transacoes;
    }

    public abstract void sacar(double valor) throws SaldoInsuficienteException;
    public abstract void depositar(double valor);

    @Override
    public String toString() {
        return "Conta{" +
                "numeroConta=" + numeroConta +
                ", correntistaNome='" + correntistaNome + '\'' +
                ", correntistaCPF='" + correntistaCPF + '\'' +
                ", saldo=" + saldo +
                ", transacoes=" + transacoes +
                '}';
    }
}
