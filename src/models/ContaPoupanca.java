package models;

import models.enums.IdentificadorTipo;
import models.exceptions.SaldoInsuficienteException;
import models.interfaces.Remunerada;

public class ContaPoupanca extends Conta implements Remunerada {

    public ContaPoupanca(String correntistaNome, String correntistaCPF) {
        super(correntistaNome, correntistaCPF);
    }

    public ContaPoupanca(String correntistaNome, String correntistaCPF, double saldo) {
        super(correntistaNome, correntistaCPF, saldo);
        if (saldo < 0) {
            this.saldo = 0;
        }
    }

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

    @Override
    public void taxaCorrecao(double porcentagemTaxa) {
        if (porcentagemTaxa <= 0) {
            throw new IllegalArgumentException("O valor para taxa deve ser positivo.");
        }

        double valor = this.saldo + (this.saldo * (porcentagemTaxa / 100));
        this.saldo = valor;

        Operacao operacao = new Operacao(valor, IdentificadorTipo.CORRECAO_TAX);
        operacao.setSaldoAtual(this.saldo);

        transacoes.add(operacao);
    }
}
