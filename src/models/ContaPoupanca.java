package models;

import models.enums.IdentificadorTipo;
import models.exceptions.SaldoInsuficienteException;
import models.interfaces.Remunerada;

/**
 * Classe que representa uma conta poupança.
 * Implementa a interface Remunerada para aplicar taxas de correção ao saldo.
 */

public class ContaPoupanca extends Conta implements Remunerada {

    /**
     * Construtor da classe ContaPoupanca.
     * Inicializa uma conta poupança sem saldo inicial.
     *
     * @param correntistaNome Nome do correntista.
     * @param correntistaCPF CPF do correntista.
     */
    public ContaPoupanca(String correntistaNome, String correntistaCPF) {
        super(correntistaNome, correntistaCPF);
    }

    /**
     * Construtor da classe ContaPoupanca.
     * Inicializa uma conta poupança com saldo inicial.
     * Caso o saldo fornecido seja negativo, ele será ajustado para zero.
     *
     * @param correntistaNome Nome do correntista.
     * @param correntistaCPF CPF do correntista.
     * @param saldo Saldo inicial da conta.
     */
    public ContaPoupanca(String correntistaNome, String correntistaCPF, double saldo) {
        super(correntistaNome, correntistaCPF, saldo);
        if (saldo < 0) {
            this.saldo = 0;
        }
    }

    /**
     * Realiza um saque na conta.
     *
     * @param valor Valor a ser sacado.
     * @throws IllegalArgumentException Se o valor do saque for menor ou igual a 0.
     * @throws SaldoInsuficienteException Se o saldo disponível for insuficiente.
     */
    @Override
    public void sacar(double valor) throws SaldoInsuficienteException {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor para saque deve ser positivo.");
        }

        if (this.saldo < valor) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar o saque.");
        }

        this.saldo -= valor;

        Operacao operacao = new Operacao(valor, IdentificadorTipo.SAQUE);
        operacao.setSaldoAtual(this.saldo);
        transacoes.add(operacao);
    }

    /**
     * Realiza um depósito na conta.
     *
     * @param valor Valor a ser depositado.
     * @throws IllegalArgumentException Se o valor do depósito for menor ou igual a 0.
     */
    @Override
    public void depositar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor para depósito deve ser positivo.");
        }

        this.saldo += valor;
        Operacao operacao = new Operacao(valor, IdentificadorTipo.DEPOSITO);
        operacao.setSaldoAtual(this.saldo);

        transacoes.add(operacao);
    }

    /**
     * Aplica uma taxa de correção ao saldo da conta, gerando um rendimento proporcional à taxa informada.
     *
     * @param porcentagemTaxa Porcentagem da taxa de correção.
     * @throws IllegalArgumentException Se a taxa de correção for negativa.
     */
    @Override
    public void taxaCorrecao(double porcentagemTaxa) {
        if (porcentagemTaxa < 0) {
            throw new IllegalArgumentException("A taxa de correção deve ser positiva.");
        }

        double rendimento = this.saldo * (porcentagemTaxa / 100);
        this.saldo += rendimento;

        Operacao operacao = new Operacao(rendimento, IdentificadorTipo.CORRECAO_TAX, "Taxa " + String.format("%.2f", porcentagemTaxa) + "%");
        operacao.setSaldoAtual(this.saldo);

        transacoes.add(operacao);
    }

}
