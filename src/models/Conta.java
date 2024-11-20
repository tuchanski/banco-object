package models;

import models.exceptions.SaldoInsuficienteException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Conta implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String FILE_NAME = "numeroContaGerador.ser";
    private static int numeroContaGerador = restaurarNumeroContaGerador();

    private int numeroConta;
    private String correntistaNome;
    private String correntistaCPF;
    protected double saldo;
    protected List<Operacao> transacoes;

    public Conta(String correntistaNome, String correntistaCPF) {
        numeroContaGerador++;
        salvarNumeroContaGerador();
        this.numeroConta = numeroContaGerador;
        this.correntistaNome = correntistaNome;
        this.correntistaCPF = correntistaCPF;
        this.saldo = 0;
        this.transacoes = new ArrayList<>();
    }

    public Conta(String correntistaNome, String correntistaCPF, double saldo) {
        numeroContaGerador++;
        salvarNumeroContaGerador();
        this.numeroConta = numeroContaGerador;
        this.correntistaNome = correntistaNome;
        this.correntistaCPF = correntistaCPF;
        this.saldo = saldo;
        this.transacoes = new ArrayList<>();
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

    /**
     * Salva o valor atual de numeroContaGerador em um arquivo.
     */
    private static void salvarNumeroContaGerador() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeInt(numeroContaGerador);
        } catch (IOException e) {
            System.err.println("Erro ao salvar numeroContaGerador: " + e.getMessage());
        }
    }

    /**
     * Restaura o valor de numeroContaGerador do arquivo, se existir.
     *
     * @return o último valor gerado, ou 0 se o arquivo não existir.
     */
    private static int restaurarNumeroContaGerador() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return ois.readInt();
        } catch (IOException e) {
            return 0;
        }
    }
}
