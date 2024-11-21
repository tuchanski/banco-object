package models;

import models.enums.IdentificadorTipo;
import models.exceptions.PixJaCadastradoException;
import models.exceptions.PixNaoCadastradoException;
import models.exceptions.SaldoInsuficienteException;
import models.interfaces.Pix;

import java.util.List;

/**
 * Classe que representa uma conta corrente.
 * Implementa as operações básicas de saque, depósito, e funcionalidades relacionadas ao sistema Pix.
 */

public class ContaCorrente extends Conta implements Pix {

    /**
     * Construtor da classe ContaCorrente.
     * Inicializa uma conta corrente com saldo inicial.
     * Se o saldo fornecido for negativo, ele será ajustado para 0.
     *
     * @param correntistaNome Nome do correntista.
     * @param correntistaCPF  CPF do correntista.
     * @param saldo           Saldo inicial da conta.
     */
    public ContaCorrente(String correntistaNome, String correntistaCPF, double saldo) {
        super(correntistaNome, correntistaCPF, saldo);

        if (saldo < 0) {
            this.saldo = 0;
        }

    }

    /**
     * Construtor da classe ContaCorrente.
     * Inicializa uma conta corrente sem saldo inicial.
     *
     * @param correntistaNome Nome do correntista.
     * @param correntistaCPF  CPF do correntista.
     */
    public ContaCorrente(String correntistaNome, String correntistaCPF) {
        super(correntistaNome, correntistaCPF);
    }

    /**
     * Realiza um saque na conta.
     * O valor do saque deve ser positivo e não pode exceder o saldo disponível.
     *
     * @param valor Valor a ser sacado.
     * @throws IllegalArgumentException   Se o valor do saque for menor ou igual a 0.
     * @throws SaldoInsuficienteException Se o saldo for insuficiente para o saque.
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
     * O valor do depósito deve ser positivo.
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
     * Cadastra o CPF do correntista no sistema Pix, caso não esteja.
     *
     * @param usuariosPix Lista de CPFs cadastrados no sistema Pix.
     * @throws PixJaCadastradoException Se o CPF do correntista já estiver cadastrado.
     */
    @Override
    public void cadastrarPix(List<String> usuariosPix) throws PixJaCadastradoException {

        if (usuariosPix.contains(this.getCorrentistaCPF())) {
            throw new PixJaCadastradoException("CPF já cadastrado.");
        }

        usuariosPix.add(this.getCorrentistaCPF());
    }

    /**
     * Realiza uma transferência Pix para outra conta.
     * O CPF do remetente e do destinatário devem estar cadastrados no sistema Pix,
     * e o saldo deve ser suficiente para a transferência.
     *
     * @param usuariosPix Lista de CPFs cadastrados no sistema Pix.
     * @param destinatario Conta destinatária do Pix.
     * @param valor Valor a ser transferido.
     * @throws PixNaoCadastradoException Se o CPF do remetente ou destinatário não estiver cadastrado no sistema Pix.
     * @throws SaldoInsuficienteException Se o saldo for insuficiente para a transferência.
     * @throws IllegalArgumentException Se o valor for menor ou igual a 0.
     */
    @Override
    public void efetuarPix(List<String> usuariosPix, ContaCorrente destinatario, double valor) throws PixNaoCadastradoException, SaldoInsuficienteException {

        if (valor <= 0) {
            throw new IllegalArgumentException("O valor para transferência deve ser positivo.");
        }
        if (!usuariosPix.contains(this.getCorrentistaCPF())) {
            throw new PixNaoCadastradoException("O CPF do remetente não está cadastrado no Pix.");
        }
        if (!usuariosPix.contains(destinatario.getCorrentistaCPF())) {
            throw new PixNaoCadastradoException("O CPF do destinatário não está cadastrado no Pix.");
        }
        if (this.saldo < valor) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar a transferência Pix.");
        }

        destinatario.receberPix(usuariosPix, valor);
        this.saldo -= valor;

        Operacao operacao = new Operacao(valor, IdentificadorTipo.PIX_OUT);
        operacao.setSaldoAtual(this.saldo);

        transacoes.add(operacao);
    }

    /**
     * Recebe uma transferência Pix.
     * O CPF do destinatário deve estar cadastrado no sistema Pix.
     *
     * @param usuariosPix Lista de CPFs cadastrados no sistema Pix.
     * @param valor Valor a ser recebido.
     * @throws PixNaoCadastradoException Se o CPF do destinatário não estiver cadastrado no sistema Pix.
     */
    @Override
    public void receberPix(List<String> usuariosPix, double valor) throws PixNaoCadastradoException {

        if (!usuariosPix.contains(this.getCorrentistaCPF())) {
            throw new PixNaoCadastradoException("O CPF do destinatário não está cadastrado no Pix.");
        }

        this.saldo += valor;
        Operacao operacao = new Operacao(valor, IdentificadorTipo.PIX_IN);
        operacao.setSaldoAtual(this.saldo);

        transacoes.add(operacao);
    }

}
