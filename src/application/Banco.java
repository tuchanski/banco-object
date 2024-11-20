package application;

import models.exceptions.*;
import service.BancoService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Banco {

    public static void main(String[] args) {
        Banco banco = new Banco();
        banco.iniciar();
    }

    private final BancoService bancoService = new BancoService();
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
            [7] - Cadastrar Pix
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
                    case 1 -> criarContaCorrente();
                    case 2 -> criarContaPoupanca();
                    case 3 -> criarContaEspecial();
                    case 4 -> efetuarDeposito();
                    case 5 -> efetuarSaque();
                    case 6 -> aplicarCorrecao();
                    case 7 -> cadastrarPix();
                    case 8 -> efetuarPix();
                    case 9 -> consultarExtrato();
                    case 0 -> {
                        input.close();
                        System.out.println("\n- Obrigado por utilizar o Banco Object. 🏦");
                        app = false;
                    }
                    default -> System.out.println("Opção inválida. Tente novamente.");
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

        System.out.print("- Digite o CPF do correntista: ");
        String correntistaCPF = input.next();

        bancoService.criarContaCorrente(correntistaNome, correntistaCPF);
    }

    private void criarContaPoupanca() throws ContaJaCadastradaException, DocumentoInvalidoException {
        System.out.print("\n- Digite o nome do correntista: ");
        input.nextLine();
        String correntistaNome = input.nextLine();

        System.out.print("- Digite o CPF do correntista: ");
        String correntistaCPF = input.next();

        bancoService.criarContaPoupanca(correntistaNome, correntistaCPF);
    }

    private void criarContaEspecial() throws ContaJaCadastradaException, DocumentoInvalidoException {
        System.out.print("\n- Digite o nome do correntista: ");
        input.nextLine();
        String correntistaNome = input.nextLine();

        System.out.print("- Digite o CPF do correntista: ");
        String correntistaCPF = input.next();

        bancoService.criarContaEspecial(correntistaNome, correntistaCPF);
    }

    private void efetuarDeposito() throws ContaNaoEncontradaException {
        System.out.print("\n- Informe o número da conta: ");
        int numeroConta = input.nextInt();

        System.out.print("- Informe a quantia desejada para depósito: ");
        double quantiaDeposito = input.nextDouble();

        bancoService.efetuarDeposito(numeroConta, quantiaDeposito);
    }

    private void efetuarSaque() throws ContaNaoEncontradaException {
        System.out.print("\n- Informe o número da conta: ");
        int numeroConta = input.nextInt();

        System.out.print("- Informe a quantia desejada para saque: ");
        double quantiaSaque = input.nextDouble();

        bancoService.efetuarSaque(numeroConta, quantiaSaque);
    }

    private void aplicarCorrecao() {
        System.out.print("\n- Informe a taxa de correção: ");
        double taxa = input.nextDouble();
        bancoService.aplicarCorrecao(taxa);
    }

    private void cadastrarPix() throws ContaNaoEncontradaException, TipoContaException {
        System.out.print("\n- Informe o CPF para registro: ");
        String cpf = input.next();
        bancoService.cadastrarPix(cpf);
    }

    private void efetuarPix() throws ContaNaoEncontradaException {
        System.out.print("\n- Insira o CPF de origem: ");
        String cpfOrigem = input.next();

        System.out.print("- Insira o CPF do destino: ");
        String cpfDestino = input.next();

        System.out.print("- Insira o valor em R$: ");
        double valor = input.nextDouble();

        bancoService.efetuarPix(cpfOrigem, cpfDestino, valor);
    }

    private void consultarExtrato() throws ContaNaoEncontradaException {
        System.out.print("\n- Insira o número da conta: ");
        int numeroConta = input.nextInt();
        bancoService.consultarExtrato(numeroConta);
    }
}
