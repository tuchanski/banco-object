package models;

import java.util.ArrayList;
import java.util.List;

public abstract class Conta {

    private static int numeroContaGerador = 0;

    private int numeroConta;
    private String correntistaNome;
    private String correntistaCPF;
    private double saldo;
    private List<Operacao> transacoes;

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

    public abstract void sacar(double valor);
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
