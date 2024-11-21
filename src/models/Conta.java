package models;

import models.exceptions.SaldoInsuficienteException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstrata que representa uma conta bancária genérica.
 * Armazena informações do correntista, saldo, número da conta, e histórico de transações.
 */

public abstract class Conta implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String FILE_NAME = "numero_conta_gerador.ser";
    private static int numeroContaGerador = restaurarNumeroContaGerador();

    private int numeroConta;
    private String correntistaNome;
    private String correntistaCPF;
    protected double saldo;
    protected List<Operacao> transacoes;

    /**
     * Construtor da classe Conta.
     * Inicializa a conta com saldo zero e gera automaticamente o número da conta.
     *
     * @param correntistaNome Nome do correntista.
     * @param correntistaCPF CPF do correntista.
     */
    public Conta(String correntistaNome, String correntistaCPF) {
        this.numeroConta = numeroContaGerador;

        this.correntistaNome = correntistaNome;
        this.correntistaCPF = correntistaCPF;
        this.saldo = 0;
        this.transacoes = new ArrayList<>();

        numeroContaGerador++;
        salvarNumeroContaGerador();
    }

    /**
     * Construtor da classe Conta.
     * Inicializa a conta com um saldo inicial e gera automaticamente o número da conta.
     *
     * @param correntistaNome Nome do correntista.
     * @param correntistaCPF CPF do correntista.
     * @param saldo Saldo inicial da conta.
     */
    public Conta(String correntistaNome, String correntistaCPF, double saldo) {
        this.numeroConta = numeroContaGerador;

        this.correntistaNome = correntistaNome;
        this.correntistaCPF = correntistaCPF;
        this.saldo = saldo;
        this.transacoes = new ArrayList<>();

        numeroContaGerador++;
        salvarNumeroContaGerador();
    }

    /**
     * Retorna o número da conta.
     *
     * @return Número da conta.
     */
    public int getNumeroConta() {
        return numeroConta;
    }

    /**
     * Retorna o nome do correntista.
     *
     * @return Nome do correntista.
     */
    public String getCorrentistaNome() {
        return correntistaNome;
    }

    /**
     * Define o nome do correntista.
     *
     * @param correntistaNome Novo nome do correntista.
     */
    public void setCorrentistaNome(String correntistaNome) {
        this.correntistaNome = correntistaNome;
    }

    /**
     * Retorna o CPF do correntista.
     *
     * @return CPF do correntista.
     */
    public String getCorrentistaCPF() {
        return correntistaCPF;
    }

    /**
     * Retorna o saldo atual da conta.
     *
     * @return Saldo da conta.
     */
    public double getSaldo() {
        return saldo;
    }

    /**
     * Retorna a lista de transações associadas à conta.
     *
     * @return Lista de transações.
     */
    public List<Operacao> getTransacoes() {
        return transacoes;
    }

    /**
     * Método abstrato para realizar saques.
     * Deve ser implementado nas subclasses.
     *
     * @param valor Valor a ser sacado.
     * @throws SaldoInsuficienteException Se o saldo for insuficiente.
     */
    public abstract void sacar(double valor) throws SaldoInsuficienteException;

    /**
     * Método abstrato para realizar depósitos.
     * Deve ser implementado nas subclasses.
     *
     * @param valor Valor a ser depositado.
     */
    public abstract void depositar(double valor);

    /**
     * Salva o valor atual do gerador de número de conta em um arquivo.
     * Este método é usado para persistir o número da próxima conta a ser criada.
     */
    private static void salvarNumeroContaGerador() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeInt(numeroContaGerador);
        } catch (IOException e) {
            System.err.println("Erro ao salvar numeroContaGerador: " + e.getMessage());
        }
    }

    /**
     * Restaura o valor do gerador de número de conta a partir de um arquivo.
     * Se o arquivo não existir, o gerador começa em 0.
     *
     * @return Valor restaurado ou 0 se o arquivo não existir.
     */
    private static int restaurarNumeroContaGerador() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return ois.readInt();
        } catch (IOException e) {
            return 0;
        }
    }

    /**
     * Retorna uma representação textual da conta, incluindo o número, nome do correntista,
     * CPF e saldo.
     *
     * @return Representação textual da conta.
     */
    @Override
    public String toString() {
        return "Conta: " + numeroConta + " - " +
                "Correntista: " + correntistaNome + " (CPF: " + correntistaCPF + ") - " +
                "Saldo: " + String.format("%.2f", saldo);
    }

}
