package models;

import models.enums.IdentificadorTipo;
import models.exceptions.PixJaCadastradoException;
import models.exceptions.PixNaoCadastradoException;
import models.exceptions.SaldoInsuficienteException;
import models.interfaces.Pix;

import java.util.List;

public class ContaCorrente extends Conta implements Pix {

    public ContaCorrente(String correntistaNome, String correntistaCPF, double saldo) {
        super(correntistaNome, correntistaCPF, saldo);

        if (saldo < 0) {
            this.saldo = 0;
        }

    }

    public ContaCorrente(String correntistaNome, String correntistaCPF) {
        super(correntistaNome, correntistaCPF);
    }

    @Override
    public synchronized void sacar(double valor) throws SaldoInsuficienteException {

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
    public synchronized void depositar(double valor) {

        if (valor <= 0) {
            throw new IllegalArgumentException("O valor para depósito deve ser positivo.");
        }

        this.saldo += valor;

        Operacao operacao = new Operacao(valor, IdentificadorTipo.DEPOSITO);
        operacao.setSaldoAtual(this.saldo);

        transacoes.add(operacao);
    }

    @Override
    public void cadastrarPix(List<String> usuariosPix) throws PixJaCadastradoException {

        if (usuariosPix.contains(this.getCorrentistaCPF())) {
            throw new PixJaCadastradoException("CPF já cadastrado.");
        }

        usuariosPix.add(this.getCorrentistaCPF());
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
        if (this.saldo < valor) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar a transferência PIX.");
        }

        destinatario.receberPix(usuariosPix, valor);
        this.saldo -= valor;

        Operacao operacao = new Operacao(valor, IdentificadorTipo.PIX_OUT);
        operacao.setSaldoAtual(this.saldo);

        transacoes.add(operacao);
    }

    @Override
    public synchronized void receberPix(List<String> usuariosPix, double valor) throws PixNaoCadastradoException {

        if (!usuariosPix.contains(this.getCorrentistaCPF())) {
            throw new PixNaoCadastradoException("O CPF do destinatário não está cadastrado no PIX.");
        }

        this.saldo += valor;
        Operacao operacao = new Operacao(valor, IdentificadorTipo.PIX_IN);
        operacao.setSaldoAtual(this.saldo);

        transacoes.add(operacao);
    }

}
