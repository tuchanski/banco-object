package service;

import models.*;
import models.exceptions.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Serviço responsável pela gestão de contas bancárias, incluindo operações
 * de criação, depósito, saque, cadastro de PIX, transferências via PIX,
 * aplicação de correção em contas poupança, e consulta de extratos.
 * A classe principal Banco utiliza BancoService para lidar com as lógicas de negócios.
 * O detalhamento também é similar à classe Banco.
 */

public class BancoService implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final List<Conta> contas = new ArrayList<>();
    private final List<String> cpfsPix = new ArrayList<>();

    /**
     * Cria uma conta corrente no banco.
     *
     * @param correntistaNome Nome do correntista.
     * @param correntistaCPF  CPF do correntista.
     * @throws ContaJaCadastradaException Se o nome ou CPF já estiver cadastrado.
     * @throws DocumentoInvalidoException Se o CPF for inválido.
     */
    public void criarContaCorrente(String correntistaNome, String correntistaCPF) throws ContaJaCadastradaException, DocumentoInvalidoException {
        checaDisponibilidadeNomeCPF(correntistaNome, correntistaCPF);

        Conta novaContaCorrente = new ContaCorrente(correntistaNome, correntistaCPF);
        contas.add(novaContaCorrente);

        System.out.println("\n- Conta Corrente criada com sucesso: n° " + novaContaCorrente.getNumeroConta());
    }

    /**
     * Cria uma conta poupança no sistema.
     *
     * @param correntistaNome Nome do correntista.
     * @param correntistaCPF  CPF do correntista.
     * @throws ContaJaCadastradaException Se o nome ou CPF já estiver cadastrado.
     * @throws DocumentoInvalidoException Se o CPF for inválido.
     */
    public void criarContaPoupanca(String correntistaNome, String correntistaCPF) throws ContaJaCadastradaException, DocumentoInvalidoException {
        checaDisponibilidadeNomeCPF(correntistaNome, correntistaCPF);

        Conta novaContaPoupanca = new ContaPoupanca(correntistaNome, correntistaCPF);
        contas.add(novaContaPoupanca);

        System.out.println("\n- Conta Poupança criada com sucesso: n° " + novaContaPoupanca.getNumeroConta());
    }

    /**
     * Cria uma conta especial no sistema.
     *
     * @param correntistaNome Nome do correntista.
     * @param correntistaCPF  CPF do correntista.
     * @throws ContaJaCadastradaException Se o nome ou CPF já estiver cadastrado.
     * @throws DocumentoInvalidoException Se o CPF for inválido.
     */
    public void criarContaEspecial(String correntistaNome, String correntistaCPF) throws ContaJaCadastradaException, DocumentoInvalidoException {
        checaDisponibilidadeNomeCPF(correntistaNome, correntistaCPF);

        Conta novaContaEspecial = new ContaEspecial(correntistaNome, correntistaCPF);
        contas.add(novaContaEspecial);

        double limiteEspecial = ((ContaEspecial) novaContaEspecial).getLimiteEspecial();
        System.out.println("\n- Conta especial criada com sucesso: n° " + novaContaEspecial.getNumeroConta() + " | Limite Especial: " + String.format("%.2f", limiteEspecial));
    }


    /**
     * Realiza um depósito em uma conta específica.
     *
     * @param numeroConta Número da conta.
     * @param quantiaDeposito Valor a ser depositado.
     * @throws ContaNaoEncontradaException Se a conta não for encontrada.
     */
    public void efetuarDeposito(int numeroConta, double quantiaDeposito) throws ContaNaoEncontradaException {
        Conta conta = getContaPorNumero(numeroConta);

        if (conta == null) {
            throw new ContaNaoEncontradaException("Conta com n° " + numeroConta + " não encontrada.");
        }

        conta.depositar(quantiaDeposito);
        System.out.println("- Depósito de R$" + String.format("%.2f", quantiaDeposito) + " realizado com sucesso.");
    }


    /**
     * Realiza um saque em uma conta específica.
     *
     * @param numeroConta Número da conta.
     * @param quantiaSaque Valor a ser sacado.
     * @throws ContaNaoEncontradaException Se a conta não for encontrada.
     */
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

    /**
     * Aplica uma taxa de correção em todas as contas poupança registradas.
     *
     * @param taxa Taxa de correção em porcentagem.
     */
    public void aplicarCorrecao(double taxa) {
        aplicaTaxaEmPorcentagem(taxa);
        System.out.println("- Correção de " + taxa + "% realizada com sucesso.");
    }


    /**
     * Cadastra uma chave PIX associada a um CPF.
     *
     * @param cpf CPF a ser cadastrado.
     * @throws ContaNaoEncontradaException Se o CPF não estiver associado a uma conta.
     * @throws TipoContaException Se a conta associada ao CPF não for uma conta corrente/especial.
     */
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
            throw new TipoContaException("Conta selecionada não é Conta Corrente/Especial.");
        }
    }


    /**
     * Realiza uma transferência via PIX entre duas contas.
     *
     * @param cpfOrigem  CPF da conta de origem.
     * @param cpfDestino CPF da conta de destino.
     * @param valor      Valor a ser transferido.
     * @throws ContaNaoEncontradaException Se um dos CPFs não estiver cadastrado para PIX.
     */
    public void efetuarPix(String cpfOrigem, String cpfDestino, double valor) throws ContaNaoEncontradaException {
        if (!cpfsPix.contains(cpfOrigem)) {
            throw new ContaNaoEncontradaException("CPF " + cpfOrigem + " não cadastrado para Pix.");
        }

        if (!cpfsPix.contains(cpfDestino)) {
            throw new ContaNaoEncontradaException("CPF " + cpfDestino + " não cadastrado para Pix.");
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


    /**
     * Exibe o histórico de transações de uma conta.
     *
     * @param numeroConta Número da conta a ser consultada.
     * @throws ContaNaoEncontradaException Se a conta não for encontrada.
     */
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


    /**
     * Exibe todas as contas registradas no sistema.
     */
    public void exibirContasRegistradas() {
        System.out.println();
        contas.forEach(System.out::println);
    }

    // Métodos Privados


    /**
     * Verifica a disponibilidade de um nome e CPF no sistema.
     *
     * @param correntistaNome Nome a ser verificado.
     * @param correntistaCPF  CPF a ser verificado.
     * @throws ContaJaCadastradaException Se o nome ou CPF já estiver em uso.
     * @throws DocumentoInvalidoException Se o CPF for inválido.
     */
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


    /**
     * Busca uma conta pelo CPF.
     *
     * @param cpf Número do CPF.
     * @return Conta encontrada ou null se não existir.
     */
    private Conta getContaPorCPF(String cpf) {
        return contas.stream().filter(conta -> conta.getCorrentistaCPF().equals(cpf)).findFirst().orElse(null);
    }


    /**
     * Busca uma conta pelo número.
     *
     * @param numeroConta Número da conta.
     * @return Conta encontrada ou null se não existir.
     */
    private Conta getContaPorNumero(int numeroConta) {
        return contas.stream().filter(conta -> conta.getNumeroConta() == numeroConta).findFirst().orElse(null);
    }


    /**
     * Aplica uma taxa de correção a todas as contas poupança.
     *
     * @param taxa Taxa a ser aplicada.
     */
    private void aplicaTaxaEmPorcentagem(double taxa) {
        contas.stream()
                .filter(conta -> conta instanceof ContaPoupanca)
                .map(conta -> (ContaPoupanca) conta)
                .forEach(contaPoupanca -> {contaPoupanca.taxaCorrecao(taxa);}
                );
    }


    /**
     * Verifica se nome está disponível no sistema.
     * @param nome Nome a ser procurado.
     * @return Verdadeiro se o nome está disponível, caso contrário falso.
     */
    private boolean verificarDisponibilidadePorNome(String nome) {
        return contas.stream().noneMatch(conta -> conta.getCorrentistaNome().equals(nome));
    }


    /**
     * Verifica se o CPF está disponível no sistema.
     * @param cpf CPF a ser procurado.
     * @return Verdadeiro se o CPF está disponível, caso contrário falso.
     */
    private boolean verificarDisponibilidadePorCPF(String cpf) {
        return contas.stream().noneMatch(conta -> conta.getCorrentistaCPF().equals(cpf));
    }
}
