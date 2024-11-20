package application;

import models.*;
import models.exceptions.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Banco {

    public static void main(String[] args) {
        Banco banco = new Banco();
        banco.iniciar();
    }

    private final static List<Conta> contas = new ArrayList<>();
    private final static List<String> cpfsPix = new ArrayList<>();

    private final Scanner input = new Scanner(System.in);

    private void getMenu() {
        System.out.println("""
            \n- 🏦 MENU DE OPERAÇÕES 🏦 -
            [1] - Criar Conta Corrente
            [2] - Criar Conta Poupança
            [3] - Criar Conta Especial
            [4] - Efetuar Depósito
            [5] - Efetuar Saque
            [6] - Aplicar Correção
            [7] - Cadastrar PIX
            [8] - Efetuar Pix
            [9] - Consultar Extrato
            [0] - Sair""");
        System.out.print("\nDigite aqui: ");
    }

    private void iniciar() {
        boolean app = true;

        while(app) {
            getMenu();
            try {
                int modoOperacao = input.nextInt();

                switch (modoOperacao) {
                    case 1:
                        criarContaCorrente();
                        break;
                    case 2:
                        criarContaPoupanca();
                        break;
                    case 3:
                        criarContaEspecial();
                        break;
                    case 4:
                        efetuarDeposito();
                        break;
                    case 5:
                        efetuarSaque();
                        break;
                    case 6:
                        aplicarCorrecao();
                        break;
                    case 7:
                        cadastrarPix();
                        break;
                    case 8:
                        efetuarPix();
                        break;
                    case 9:
                        consultarExtrato();
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        break;
                }

            } catch (InputMismatchException e) {
                System.out.println("\nErro: Entrada inválida. Por favor, insira um número.");
                input.nextLine(); // Limpando buffer
            } catch (Exception e) {
                System.out.println("\nErro: " + e.getMessage());
            }
        }
    }

    private void criarContaCorrente() throws ContaJaCadastradaException, DocumentoInvalidoException {

        System.out.print("\n- Digite o nome do correntista: ");
        input.nextLine();
        String correntistaNome = input.nextLine();

        boolean nomeDisponibilidade = verificarDisponibilidadePorNome(correntistaNome);

        if (!nomeDisponibilidade) {
            throw new ContaJaCadastradaException("Nome já cadastrado no sistema.");
        }

        System.out.print("- Digite o CPF do correntista: ");
        String correntistaCPF = input.next();

        boolean cpfDisponibilidade = verificarDisponibilidadePorCPF(correntistaCPF);

        if (!cpfDisponibilidade) {
            throw new ContaJaCadastradaException("CPF já cadastrado no sistema.");
        }

        boolean cpfEhValido = ValidarCPF.cpfEhValido(correntistaCPF);

        if (!cpfEhValido) {
            throw new DocumentoInvalidoException("CPF inválido.");
        }

        Conta novaContaCorrente = new ContaCorrente(correntistaNome, correntistaCPF);
        contas.add(novaContaCorrente);

        System.out.println("\n- Conta criada com sucesso: n° " + novaContaCorrente.getNumeroConta());
    }

    private void criarContaPoupanca() throws ContaJaCadastradaException, DocumentoInvalidoException {

        System.out.print("\n- Digite o nome do correntista: ");
        input.nextLine();
        String correntistaNome = input.nextLine();

        boolean nomeDisponibilidade = verificarDisponibilidadePorNome(correntistaNome);

        if (!nomeDisponibilidade) {
            throw new ContaJaCadastradaException("Nome já cadastrado no sistema.");
        }

        System.out.print("- Digite o CPF do correntista: ");
        String correntistaCPF = input.next();

        boolean cpfDisponibilidade = verificarDisponibilidadePorCPF(correntistaCPF);

        if (!cpfDisponibilidade) {
            throw new ContaJaCadastradaException("CPF já cadastrado no sistema.");
        }

        boolean cpfEhValido = ValidarCPF.cpfEhValido(correntistaCPF);

        if (!cpfEhValido) {
            throw new DocumentoInvalidoException("CPF inválido.");
        }

        Conta novaContaPoupanca = new ContaPoupanca(correntistaNome, correntistaCPF);

        contas.add(novaContaPoupanca);

        System.out.println("\n- Conta criada com sucesso: n° " + novaContaPoupanca.getNumeroConta());
    }

    private void criarContaEspecial() throws ContaJaCadastradaException, DocumentoInvalidoException {
        System.out.print("\n- Digite o nome do correntista: ");
        input.nextLine();
        String correntistaNome = input.nextLine();

        boolean nomeDisponibilidade = verificarDisponibilidadePorNome(correntistaNome);

        if (!nomeDisponibilidade) {
            throw new ContaJaCadastradaException("Nome já cadastrado no sistema.");
        }

        System.out.print("- Digite o CPF do correntista: ");
        String correntistaCPF = input.next();

        boolean cpfDisponibilidade = verificarDisponibilidadePorCPF(correntistaCPF);

        if (!cpfDisponibilidade) {
            throw new ContaJaCadastradaException("CPF já cadastrado no sistema.");
        }

        boolean cpfEhValido = ValidarCPF.cpfEhValido(correntistaCPF);

        if (!cpfEhValido) {
            throw new DocumentoInvalidoException("CPF inválido.");
        }

        Conta novaContaEspecial = new ContaEspecial(correntistaNome, correntistaCPF);
        contas.add(novaContaEspecial);

        double limiteEspecial = ((ContaEspecial) novaContaEspecial).getLimiteEspecial();

        System.out.println("\n- Conta especial criada com sucesso: n° " + novaContaEspecial.getNumeroConta() + " | Limite Especial: " + String.format("%.2f", limiteEspecial));
    }

    private void efetuarDeposito() throws ContaNaoEncontradaException {

        System.out.print("\n- Informe o número da conta: ");
        int numeroConta = input.nextInt();

        Conta conta = getContaPorNumero(numeroConta);

        if (conta == null) {
            throw new ContaNaoEncontradaException("Conta com n° " + numeroConta + " não encontrada.");
        }

        System.out.print("- Informe a quantia desejada para depósito: ");
        double quantiaDeposito = input.nextDouble();

        conta.depositar(quantiaDeposito);

        System.out.println("- Depósito de R$" + String.format("%.2f", quantiaDeposito) + " realizado com sucesso.");
    }

    private void efetuarSaque() throws ContaNaoEncontradaException {

        System.out.print("\n- Informe o número da conta: ");
        int numeroConta = input.nextInt();

        Conta conta = getContaPorNumero(numeroConta);

        if (conta == null) {
            throw new ContaNaoEncontradaException("Conta com n° " + numeroConta + " não encontrada.");
        }

        System.out.print("- Informe a quantia desejada para saque: ");
        double quantiaSaque = input.nextDouble();

        try {
            conta.sacar(quantiaSaque);
            System.out.println("- Saque de R$" + String.format("%.2f", quantiaSaque) + " realizado com sucesso.");
        } catch (SaldoInsuficienteException e) {
            System.out.println("\nErro: " + e.getMessage());
        }
    }

    private void aplicarCorrecao() {
        System.out.print("\n- Informe a taxa de correção: ");
        double taxa = input.nextDouble();
        aplicaTaxaEmPorcentagem(taxa);
        System.out.println("- Correção de " + taxa + "% realizada com sucesso.");
    }

    private void cadastrarPix() throws ContaNaoEncontradaException, TipoContaException {
        System.out.print("\n- Informe o CPF para registro: ");
        String cpf = input.next();

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

    private void efetuarPix() throws ContaNaoEncontradaException {

        System.out.print("\n- Insira o CPF de origem: ");
        String cpfOrigem = input.next();

        if (!cpfsPix.contains(cpfOrigem)) {
            throw new ContaNaoEncontradaException("CPF " + cpfOrigem + " não cadastrado para PIX.");
        }

        System.out.print("- Insira o CPF do destino: ");
        String cpfDestino = input.next();

        if (!cpfsPix.contains(cpfDestino)) {
            throw new ContaNaoEncontradaException("CPF " + cpfDestino + " não cadastrado para PIX.");
        }

        System.out.print("- Insira o valor em R$: ");
        double valor = input.nextDouble();

        ContaCorrente origem = (ContaCorrente) getContaPorCPF(cpfOrigem);
        ContaCorrente destino = (ContaCorrente) getContaPorCPF(cpfDestino);

        try {
            origem.efetuarPix(cpfsPix, destino, valor); // Destino recebe automaticamente
            System.out.println("- Pix de R$" + String.format("%.2f", valor) + " realizado com sucesso de " + cpfOrigem + " para " + cpfDestino + ".");
        } catch (PixNaoCadastradoException | SaldoInsuficienteException e) {
            System.out.println("\nErro: " + e.getMessage());
        }

    }

    private void consultarExtrato() throws ContaNaoEncontradaException {
        System.out.print("\n- Insira o número da conta: ");
        int numeroConta = input.nextInt();

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

    // Aux

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
