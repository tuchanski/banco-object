package models;

import models.enums.IdentificadorTipo;
import models.exceptions.PixNaoCadastradoException;
import models.exceptions.SaldoInsuficienteException;

import java.util.List;
import java.util.Random;

public class ContaEspecial extends ContaCorrente {

    private double limiteEspecial;
    private Random random = new Random();

    public ContaEspecial(String correntistaNome, String correntistaCPF, double saldo) {
        super(correntistaNome, correntistaCPF, saldo);
        this.limiteEspecial = random.nextInt(300, 1000);
    }

    public ContaEspecial(String correntistaNome, String correntistaCPF) {
        super(correntistaNome, correntistaCPF);
        this.limiteEspecial = random.nextInt(300, 1000);
    }

    public double getLimiteEspecial() {
        return limiteEspecial;
    }

    @Override
    public synchronized void sacar(double valor) throws SaldoInsuficienteException {

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

    @Override
    public synchronized void efetuarPix(List<String> usuariosPix, ContaCorrente destinatario, double valor) throws PixNaoCadastradoException, SaldoInsuficienteException {

        if (valor <= 0) {
            throw new IllegalArgumentException("O valor para transferência deve ser positivo.");
        }
        if (!usuariosPix.contains(this.getCorrentistaCPF())) {
            throw new PixNaoCadastradoException("O CPF do remetente não está cadastrado no PIX.");
        }
        if (!usuariosPix.contains(destinatario.getCorrentistaCPF())) {
            throw new PixNaoCadastradoException("O CPF do destinatário não está cadastrado no PIX.");
        }
        if ((this.saldo + this.limiteEspecial) < valor) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar a transferência PIX.");
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
