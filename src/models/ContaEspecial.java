package models;

import models.enums.IdentificadorTipo;
import models.exceptions.PixNaoCadastradoException;
import models.exceptions.SaldoInsuficienteException;

import java.util.List;
import java.util.Random;

/**
 * Classe que representa uma conta especial.
 * Herda da classe ContaCorrente e adiciona a funcionalidade de um limite especial para saque e Pix.
 */

public class ContaEspecial extends ContaCorrente {

    private double limiteEspecial;
    private Random random = new Random();

    /**
     * Construtor da classe ContaEspecial.
     * Inicializa uma conta especial com saldo inicial e atribui um limite especial aleatório.
     *
     * @param correntistaNome Nome do correntista.
     * @param correntistaCPF CPF do correntista.
     * @param saldo Saldo inicial da conta.
     */
    public ContaEspecial(String correntistaNome, String correntistaCPF, double saldo) {
        super(correntistaNome, correntistaCPF, saldo);
        this.limiteEspecial = random.nextInt(300, 1000);
    }

    /**
     * Construtor da classe ContaEspecial.
     * Inicializa uma conta especial sem saldo inicial e atribui um limite especial aleatório.
     *
     * @param correntistaNome Nome do correntista.
     * @param correntistaCPF CPF do correntista.
     */
    public ContaEspecial(String correntistaNome, String correntistaCPF) {
        super(correntistaNome, correntistaCPF);
        this.limiteEspecial = random.nextInt(300, 1000);
    }

    /**
     * Retorna o valor do limite especial disponível para a conta.
     *
     * @return Valor do limite especial.
     */
    public double getLimiteEspecial() {
        return limiteEspecial;
    }

    /**
     * Realiza um saque na conta.
     * O saque pode usar o saldo e o limite especial, caso o saldo não seja suficiente.
     *
     * @param valor Valor a ser sacado.
     * @throws IllegalArgumentException   Se o valor do saque for menor ou igual a 0.
     * @throws SaldoInsuficienteException Se o saldo mais o limite especial não forem suficientes para o saque.
     */
    @Override
    public void sacar(double valor) throws SaldoInsuficienteException {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor para saque deve ser positivo.");
        }

        if ((this.saldo + limiteEspecial) < valor) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar o saque.");
        }

        if (saldo >= valor) {
            this.saldo -= valor;
        } else {
            this.saldo += limiteEspecial;
            this.saldo -= valor;
            this.limiteEspecial = 0;
        }

        Operacao operacao = new Operacao(valor, IdentificadorTipo.SAQUE);
        operacao.setSaldoAtual(this.saldo);

        transacoes.add(operacao);
    }

    /**
     * Realiza uma transferência Pix para outra conta.
     * O Pix pode utilizar o saldo e o limite especial, caso o saldo não seja suficiente.
     *
     * @param usuariosPix Lista de CPFs cadastrados no sistema Pix.
     * @param destinatario Conta destinatária do Pix.
     * @param valor Valor a ser transferido.
     * @throws IllegalArgumentException Se o valor do Pix for menor ou igual a 0.
     * @throws PixNaoCadastradoException Se o CPF do remetente ou do destinatário não estiver cadastrado no sistema Pix.
     * @throws SaldoInsuficienteException Se o saldo mais o limite especial não forem suficientes para a transferência Pix.
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
        if ((this.saldo + this.limiteEspecial) < valor) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar a transferência Pix.");
        }

        destinatario.receberPix(usuariosPix, valor);

        if (saldo >= valor) {
            this.saldo -= valor;
        } else {
            this.saldo += this.limiteEspecial;
            this.saldo -= valor;
            this.limiteEspecial = 0;
        }

        Operacao operacao = new Operacao(valor, IdentificadorTipo.PIX_OUT);
        operacao.setSaldoAtual(this.saldo);

        transacoes.add(operacao);
    }

}
