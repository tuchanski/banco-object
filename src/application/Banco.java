package application;

import models.Conta;
import models.ContaCorrente;
import models.ContaPoupanca;
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

    private static List<Conta> contas = new ArrayList<>();
    private static List<String> cpfsPix = new ArrayList<>();

    private Scanner input = new Scanner(System.in);

    private void getMenu() {
        System.out.println("""
                \n- 🏦 MENU DE OPERAÇÕES 🏦 -
                [1] - Criar Conta Corrente
                [2] - Criar Conta Poupança
                [3] - Efetuar Depósito
                [4] - Efetuar Saque
                [5] - Aplicar Correção
                [6] - Cadastrar PIX
                [7] - Efetuar Pix
                [8] - Consultar Extrato
                [0] - Sair""");
        System.out.printf("\nDigite aqui: ");
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
                        efetuarDeposito();
                        break;
                    case 4:
                        efetuarSaque();
                        break;
                    case 5:
                        aplicarCorrecao();
                        break;
                    case 6:
                        cadastrarPix();
                        break;
                    case 7:
                        efetuarPix();
                        break;
                    case 8:
                        consultarExtrato();
                        break;
                    case 0:
                        input.close();
                        app = false;
                        break;
                    default:
                        System.out.println("- Operação inválida.");
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

    private void criarContaCorrente() throws ContaJaCadastradaException {

        System.out.print("\n- Digite o nome do correntista: ");
        input.nextLine();
        String correntistaNome = input.nextLine();

        boolean nomeDisponibilidade = verificarDisponibilidadePorNome(correntistaNome);

        if (!nomeDisponibilidade) {
            throw new ContaJaCadastradaException("Nome já cadastrado no sistema.");
        }

        System.out.print("\n- Digite o CPF do correntista: ");
        String correntistaCPF = input.next();

        boolean cpfDisponibilidade = verificarDisponibilidadePorCPF(correntistaCPF);

        if (!cpfDisponibilidade) {
            throw new ContaJaCadastradaException("CPF já cadastrado no sistema.");
        }

        Conta novaContaCorrente = new ContaCorrente(correntistaNome, correntistaCPF);
        contas.add(novaContaCorrente);

        System.out.println("- Conta criada com sucesso: n° " + novaContaCorrente.getNumeroConta());
    }

    private void criarContaPoupanca() throws ContaJaCadastradaException {
        System.out.print("\n- Digite o nome do correntista: ");
        input.nextLine();
        String correntistaNome = input.nextLine();

        boolean nomeDisponibilidade = verificarDisponibilidadePorNome(correntistaNome);

        if (!nomeDisponibilidade) {
            throw new ContaJaCadastradaException("Nome já cadastrado no sistema.");
        }

        System.out.print("\n- Digite o CPF do correntista: ");
        String correntistaCPF = input.next();

        boolean cpfDisponibilidade = verificarDisponibilidadePorCPF(correntistaCPF);

        if (!cpfDisponibilidade) {
            throw new ContaJaCadastradaException("CPF já cadastrado no sistema.");
        }

        Conta novaContaPoupanca = new ContaPoupanca(correntistaNome, correntistaCPF);

        contas.add(novaContaPoupanca);

        System.out.println("- Conta criada com sucesso: n° " + novaContaPoupanca.getNumeroConta());
    }

    private void efetuarDeposito() throws ContaNaoEncontradaException {

        System.out.print("\n- Informe o número da conta: ");
        int numeroConta = input.nextInt();

        Conta conta = getContaPorNumero(numeroConta);

        if (conta == null) {
            throw new ContaNaoEncontradaException("Conta com n° " + numeroConta + " não encontrada.");
        }

        System.out.print("\n- Informe a quantia desejada para depósito: ");
        double quantiaDeposito = input.nextDouble();

        conta.depositar(quantiaDeposito);

        System.out.println("- Depósito de R$" + quantiaDeposito + " realizado com sucesso.");
    }

    private void efetuarSaque() throws ContaNaoEncontradaException {
        System.out.print("\n- Informe o número da conta: ");
        int numeroConta = input.nextInt();

        Conta conta = getContaPorNumero(numeroConta);

        if (conta == null) {
            throw new ContaNaoEncontradaException("- Conta com n° " + numeroConta + " não encontrada.");
        }

        System.out.print("\n- Informe a quantia desejada para saque: ");
        double quantiaSaque = input.nextDouble();

        try {
            conta.sacar(quantiaSaque);
            System.out.println("- Saque de R$" + quantiaSaque + " realizado com sucesso.");
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

    private void cadastrarPix() throws ContaNaoEncontradaException {
        System.out.print("\n- Informe o CPF para registro: ");
        String cpf = input.next();

        Conta conta = getContaPorCPF(cpf);

        if (conta == null) {
            throw new ContaNaoEncontradaException("- Conta com CPF " + cpf + " não encontrada.");
        }

        if (conta instanceof ContaCorrente contaCorrente) {
            try {
                contaCorrente.cadastrarPix(cpfsPix);
                System.out.println("Chave pix cadastrada com sucesso.");
            } catch (PixJaCadastradoException e) {
                System.out.println("\nErro: " + e.getMessage());
            }
        } else {
            System.out.println("- O tipo da conta desejada não é Corrente."); // Criar Exceção nova
        }

    }

    private void efetuarPix() throws ContaNaoEncontradaException {

        System.out.print("\n- Insira o CPF de origem: ");
        String cpfOrigem = input.next();

        if (!cpfsPix.contains(cpfOrigem)) {
            throw new ContaNaoEncontradaException("CPF " + cpfOrigem + " não cadastrada para PIX.");
        }

        System.out.print("\n- Insira o CPF do destino: ");
        String cpfDestino = input.next();

        if (!cpfsPix.contains(cpfDestino)) {
            throw new ContaNaoEncontradaException("CPF " + cpfDestino + " não cadastrada para PIX.");
        }

        System.out.print("\n- Insira o valor em R$: ");
        double valor = input.nextDouble();

        ContaCorrente origem = (ContaCorrente) getContaPorCPF(cpfOrigem);
        ContaCorrente destino = (ContaCorrente) getContaPorCPF(cpfDestino);

        try {
            origem.efetuarPix(cpfsPix, destino, valor); // Já chama a função pro destino receber
            System.out.println("- Pix de R$" + valor + " realizado com sucesso de " + cpfOrigem + " para " + cpfDestino + ".");
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

        System.out.println(conta.getTransacoes());
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
