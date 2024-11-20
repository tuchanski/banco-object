package service;

import models.*;
import models.exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class BancoService {

    private final static List<Conta> contas = new ArrayList<>();
    private final static List<String> cpfsPix = new ArrayList<>();

    public void criarContaCorrente(String correntistaNome, String correntistaCPF) throws ContaJaCadastradaException, DocumentoInvalidoException {
        checaDisponibilidadeNomeCPF(correntistaNome, correntistaCPF);

        Conta novaContaCorrente = new ContaCorrente(correntistaNome, correntistaCPF);
        contas.add(novaContaCorrente);

        System.out.println("\n- Conta Corrente criada com sucesso: n° " + novaContaCorrente.getNumeroConta());
    }

    public void criarContaPoupanca(String correntistaNome, String correntistaCPF) throws ContaJaCadastradaException, DocumentoInvalidoException {
        checaDisponibilidadeNomeCPF(correntistaNome, correntistaCPF);

        Conta novaContaPoupanca = new ContaPoupanca(correntistaNome, correntistaCPF);
        contas.add(novaContaPoupanca);

        System.out.println("\n- Conta Poupança criada com sucesso: n° " + novaContaPoupanca.getNumeroConta());
    }

    public void criarContaEspecial(String correntistaNome, String correntistaCPF) throws ContaJaCadastradaException, DocumentoInvalidoException {
        checaDisponibilidadeNomeCPF(correntistaNome, correntistaCPF);

        Conta novaContaEspecial = new ContaEspecial(correntistaNome, correntistaCPF);
        contas.add(novaContaEspecial);

        double limiteEspecial = ((ContaEspecial) novaContaEspecial).getLimiteEspecial();
        System.out.println("\n- Conta especial criada com sucesso: n° " + novaContaEspecial.getNumeroConta() + " | Limite Especial: " + String.format("%.2f", limiteEspecial));
    }

    public void efetuarDeposito(int numeroConta, double quantiaDeposito) throws ContaNaoEncontradaException {
        Conta conta = getContaPorNumero(numeroConta);

        if (conta == null) {
            throw new ContaNaoEncontradaException("Conta com n° " + numeroConta + " não encontrada.");
        }

        conta.depositar(quantiaDeposito);
        System.out.println("- Depósito de R$" + String.format("%.2f", quantiaDeposito) + " realizado com sucesso.");
    }

    public void efetuarSaque(int numeroConta, double quantiaSaque) throws ContaNaoEncontradaException {
        Conta conta = getContaPorNumero(numeroConta);

        if (conta == null) {
            throw new ContaNaoEncontradaException("Conta com n° " + numeroConta + " não encontrada.");
        }

        try {
            conta.sacar(quantiaSaque);
            System.out.println("- Saque de R$" + String.format("%.2f", quantiaSaque) + " realizado com sucesso.");
        } catch (SaldoInsuficienteException e) {
            System.out.println("\nErro: " + e.getMessage());
        }
    }

    public void aplicarCorrecao(double taxa) {
        aplicaTaxaEmPorcentagem(taxa);
        System.out.println("- Correção de " + taxa + "% realizada com sucesso.");
    }

    public void cadastrarPix(String cpf) throws ContaNaoEncontradaException, TipoContaException {
        Conta conta = getContaPorCPF(cpf);

        if (conta == null) {
            throw new ContaNaoEncontradaException("Conta com CPF " + cpf + " não encontrada.");
        }

        if (conta instanceof ContaCorrente contaCorrente) {
            try {
                contaCorrente.cadastrarPix(cpfsPix);
                System.out.println("- Chave pix cadastrada com sucesso.");
            } catch (PixJaCadastradoException e) {
                System.out.println("\nErro: " + e.getMessage());
            }
        } else {
            throw new TipoContaException("Conta selecionada não é Conta Corrente.");
        }
    }

    public void efetuarPix(String cpfOrigem, String cpfDestino, double valor) throws ContaNaoEncontradaException {
        if (!cpfsPix.contains(cpfOrigem)) {
            throw new ContaNaoEncontradaException("CPF " + cpfOrigem + " não cadastrado para PIX.");
        }

        if (!cpfsPix.contains(cpfDestino)) {
            throw new ContaNaoEncontradaException("CPF " + cpfDestino + " não cadastrado para PIX.");
        }

        ContaCorrente origem = (ContaCorrente) getContaPorCPF(cpfOrigem);
        ContaCorrente destino = (ContaCorrente) getContaPorCPF(cpfDestino);

        try {
            origem.efetuarPix(cpfsPix, destino, valor); // Destino recebe automaticamente
            System.out.println("- Pix de R$" + String.format("%.2f", valor) + " realizado com sucesso de " + cpfOrigem + " para " + cpfDestino + ".");
        } catch (PixNaoCadastradoException | SaldoInsuficienteException e) {
            System.out.println("\nErro: " + e.getMessage());
        }
    }

    public void consultarExtrato(int numeroConta) throws ContaNaoEncontradaException {
        Conta conta = getContaPorNumero(numeroConta);

        if (conta == null) {
            throw new ContaNaoEncontradaException("Conta com número " + numeroConta + " não encontrada.");
        }

        System.out.println();
        List<Operacao> operacoesConta = conta.getTransacoes();

        if (operacoesConta.isEmpty()) {
            System.out.println("- Sem transações disponíveis no histórico da conta.");
            return;
        }

        System.out.println("---- Histórico de Transações ----");
        operacoesConta.forEach(System.out::println);
    }


    private void checaDisponibilidadeNomeCPF(String correntistaNome, String correntistaCPF) throws ContaJaCadastradaException, DocumentoInvalidoException {
        boolean nomeDisponibilidade = verificarDisponibilidadePorNome(correntistaNome);

        if (!nomeDisponibilidade) {
            throw new ContaJaCadastradaException("Nome já cadastrado no sistema.");
        }

        boolean cpfDisponibilidade = verificarDisponibilidadePorCPF(correntistaCPF);

        if (!cpfDisponibilidade) {
            throw new ContaJaCadastradaException("CPF já cadastrado no sistema.");
        }

        boolean cpfEhValido = ValidarCPF.cpfEhValido(correntistaCPF);

        if (!cpfEhValido) {
            throw new DocumentoInvalidoException("CPF inválido.");
        }
    }

    private Conta getContaPorCPF(String cpf) {
        return contas.stream().filter(conta -> conta.getCorrentistaCPF().equals(cpf)).findFirst().orElse(null);
    }

    private Conta getContaPorNumero(int numero) {
        return contas.stream().filter(conta -> conta.getNumeroConta() == numero).findFirst().orElse(null);
    }

    private void aplicaTaxaEmPorcentagem(double taxa) {
        contas.stream()
                .filter(conta -> conta instanceof ContaPoupanca)
                .map(conta -> (ContaPoupanca) conta)
                .forEach(contaPoupanca -> {contaPoupanca.taxaCorrecao(taxa);}
                );
    }

    private boolean verificarDisponibilidadePorNome(String nome) {
        return contas.stream().noneMatch(conta -> conta.getCorrentistaNome().equals(nome));
    }

    private boolean verificarDisponibilidadePorCPF(String cpf) {
        return contas.stream().noneMatch(conta -> conta.getCorrentistaCPF().equals(cpf));
    }
}
