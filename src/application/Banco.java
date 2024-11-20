/**
 * Classe principal do Banco Object.
 * Responsável por inicializar e gerenciar as operações do sistema bancário.
 * O estado do serviço bancário é persistido e recuperado automaticamente.
 */

package application;

import models.exceptions.*;
import service.BancoService;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Banco {

    /**
     * Ponto de entrada da aplicação.
     * @param args argumentos de linha de comendo (não utilizados).
     */
    public static void main(String[] args) {
        Banco banco = new Banco();
        banco.iniciar();
    }

    private BancoService bancoService;
    private final Scanner input = new Scanner(System.in);
    private static final String FILE_NAME = "banco_service.ser";

    /**
     * Construtor da classe Banco.
     * Inicializa o serviço bancário, recupera o seu estado a partir de um arquivo serializado.
     * Caso o arquivo não exista, cria uma nova instância de {@link BancoService}
     */
    public Banco() {
        bancoService = desserializarBancoService();
        if (bancoService == null) {
            bancoService = new BancoService();
        }
    }

    /**
     * Exibe o menu principal de operações bancárias.
     */
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
            [10] - Exibir Contas Registradas
            [0] - Sair""");
        System.out.print("\nDigite aqui: ");
    }

    /**
     * Inicia o sistema bancário, exibe o menu e processa as operações escolhidas pelo usuário.
     */
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
                    case 10 -> exibirContasRegistradas();
                    case 0 -> {
                        serializarBancoService();
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

    /**
     * Salva o estado atual do {@link BancoService} em um arquivo serializado.
     */
    private void serializarBancoService() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(bancoService);
            //System.out.println("\n- Estado do BancoService salvo com sucesso.");
        } catch (IOException e) {
            System.out.println("\nErro ao salvar o estado do BancoService: " + e.getMessage());
        }
    }

    /**
     * Recupera o estado do {@link BancoService} a partir de um arquivo serializado.
     * @return Uma instância de {@link BancoService} carregada, ou {@code null} se não houver dados salvos.
     */
    private BancoService desserializarBancoService() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            //System.out.println("\n- Estado do BancoService carregado com sucesso.");
            return (BancoService) ois.readObject();
        } catch (FileNotFoundException e) {
            //System.out.println("\n- Nenhum estado anterior encontrado. Iniciando novo BancoService.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("\nErro ao carregar o estado do BancoService: " + e.getMessage());
        }
        return null;
    }

    // Métodos abaixo são específicos para as operações do banco.

    /**
     * Cria uma conta corrente no banco.
     * @throws ContaJaCadastradaException se a conta já existir.
     * @throws DocumentoInvalidoException se o CPF informado for inválido.
     */
    private void criarContaCorrente() throws ContaJaCadastradaException, DocumentoInvalidoException {
        System.out.print("\n- Digite o nome do correntista: ");
        input.nextLine();
        String correntistaNome = input.nextLine();

        System.out.print("- Digite o CPF do correntista: ");
        String correntistaCPF = input.next();

        bancoService.criarContaCorrente(correntistaNome, correntistaCPF);
    }

    /**
     * Cria uma conta poupança no banco.
     * @throws ContaJaCadastradaException se a conta já existir.
     * @throws DocumentoInvalidoException se o CPF informado for inválido.
     */
    private void criarContaPoupanca() throws ContaJaCadastradaException, DocumentoInvalidoException {
        System.out.print("\n- Digite o nome do correntista: ");
        input.nextLine();
        String correntistaNome = input.nextLine();

        System.out.print("- Digite o CPF do correntista: ");
        String correntistaCPF = input.next();

        bancoService.criarContaPoupanca(correntistaNome, correntistaCPF);
    }

    /**
     * Cria uma conta especial no banco.
     * @throws ContaJaCadastradaException se a conta já existir.
     * @throws DocumentoInvalidoException se o CPF informado for inválido.
     */
    private void criarContaEspecial() throws ContaJaCadastradaException, DocumentoInvalidoException {
        System.out.print("\n- Digite o nome do correntista: ");
        input.nextLine();
        String correntistaNome = input.nextLine();

        System.out.print("- Digite o CPF do correntista: ");
        String correntistaCPF = input.next();

        bancoService.criarContaEspecial(correntistaNome, correntistaCPF);
    }

    /**
     * Efetua um depósito para uma conta registrada no banco.
     * @throws ContaNaoEncontradaException se a conta não existir.
     */
    private void efetuarDeposito() throws ContaNaoEncontradaException {
        System.out.print("\n- Informe o número da conta: ");
        int numeroConta = input.nextInt();

        System.out.print("- Informe a quantia desejada para depósito: ");
        double quantiaDeposito = input.nextDouble();

        bancoService.efetuarDeposito(numeroConta, quantiaDeposito);
    }

    /**
     * Efetua um saque de uma conta registrada no banco.
     * @throws ContaNaoEncontradaException se a conta não existir.
     */
    private void efetuarSaque() throws ContaNaoEncontradaException {
        System.out.print("\n- Informe o número da conta: ");
        int numeroConta = input.nextInt();

        System.out.print("- Informe a quantia desejada para saque: ");
        double quantiaSaque = input.nextDouble();

        bancoService.efetuarSaque(numeroConta, quantiaSaque);
    }

    /**
     * Aplica correção monetária, em porcentagem, à todas as contas poupança registradas.
     */
    private void aplicarCorrecao() {
        System.out.print("\n- Informe a taxa de correção: ");
        double taxa = input.nextDouble();
        bancoService.aplicarCorrecao(taxa);
    }

    /**
     * Cadastra CPF como chave Pix caso a conta seja corrente/especial.
     * @throws ContaNaoEncontradaException se a conta não existir.
     * @throws TipoContaException se a conta não for corrente/especial.
     */
    private void cadastrarPix() throws ContaNaoEncontradaException, TipoContaException {
        System.out.print("\n- Informe o CPF para registro: ");
        String cpf = input.next();
        bancoService.cadastrarPix(cpf);
    }

    /**
     * Efetua pix de uma conta origem para conta destino. Ambas as contas devem ser corrente/especial e registradas
     * através do método {@link Banco#cadastrarPix()}.
     * @throws ContaNaoEncontradaException se a conta não existir.
     */
    private void efetuarPix() throws ContaNaoEncontradaException {
        System.out.print("\n- Insira o CPF de origem: ");
        String cpfOrigem = input.next();

        System.out.print("- Insira o CPF do destino: ");
        String cpfDestino = input.next();

        System.out.print("- Insira o valor em R$: ");
        double valor = input.nextDouble();

        bancoService.efetuarPix(cpfOrigem, cpfDestino, valor);
    }

    /**
     * Mostra extrato de uma conta registrada.
     * @throws ContaNaoEncontradaException se a conta não existir.
     */
    private void consultarExtrato() throws ContaNaoEncontradaException {
        System.out.print("\n- Insira o número da conta: ");
        int numeroConta = input.nextInt();
        bancoService.consultarExtrato(numeroConta);
    }

    private void exibirContasRegistradas() {
        bancoService.exibirContasRegistradas();
    }
}
